package com.webapp.boot.view.xlsx;

import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.webapp.boot.models.entity.Factura;
import com.webapp.boot.models.entity.ItemFactura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("facturas/factura.xlsx")
public class FacturaXlsx extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "factura_detalle.xls"));

		Factura factura = (Factura) model.get("factura");
		Sheet sheet = workbook.createSheet("Factura #" + factura.getId());
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Datos del cliente");
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
		sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreatedAt());
		
		CellStyle head = workbook.createCellStyle();
		head.setBorderBottom(BorderStyle.MEDIUM);
		head.setBorderTop(BorderStyle.MEDIUM);
		head.setBorderRight(BorderStyle.MEDIUM);
		head.setBorderLeft(BorderStyle.MEDIUM);
		head.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		head.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle body = workbook.createCellStyle();
		body.setBorderBottom(BorderStyle.THIN);
		body.setBorderTop(BorderStyle.THIN);
		body.setBorderRight(BorderStyle.THIN);
		body.setBorderLeft(BorderStyle.THIN);
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue("Producto");
		header.createCell(1).setCellValue("Precio");
		header.createCell(2).setCellValue("Cantidad");
		header.createCell(3).setCellValue("Total");
		
		header.getCell(0).setCellStyle(head);
		header.getCell(1).setCellStyle(head);
		header.getCell(2).setCellStyle(head);
		header.getCell(3).setCellStyle(head);

		int rownum = 10;
		
		for(ItemFactura item: factura.getItems()) {
			Row fila = sheet.createRow(rownum ++);
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(body);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(body);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(body);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(body);
		}
		
		Row filatotal = sheet.createRow(rownum);
		filatotal.createCell(2).setCellValue("Total: ");
		filatotal.createCell(3).setCellValue(factura.getTotal());
		
		workbook.write(response.getOutputStream());
	}

	

}
