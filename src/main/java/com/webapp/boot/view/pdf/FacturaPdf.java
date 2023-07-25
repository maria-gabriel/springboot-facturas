package com.webapp.boot.view.pdf;

import java.awt.Color;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.webapp.boot.models.entity.Factura;
import com.webapp.boot.models.entity.ItemFactura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("facturas/factura")
public class FacturaPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura) model.get("factura");
		PdfPCell cell = null;
		
		 
		Font titulo = new Font(Font.HELVETICA, 16, Font.BOLD, new Color(0, 0, 0));
		 
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(10);		
		cell = new PdfPCell(new Phrase("Factura #" + factura.getId(), titulo));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPadding(10f);
		tabla.addCell(cell);
		
		PdfPTable tabla1 = new PdfPTable(1);
		
		cell = new PdfPCell(new Phrase("Datos del cliente"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla1.addCell(cell);
		
		tabla1.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla1.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase("Datos de la factura"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla2.addCell(cell);
		
		tabla2.addCell("Folio: " + factura.getId());
		tabla2.addCell("Descripción: " + factura.getDescripcion());
		tabla2.addCell("Fecha: " + factura.getCreatedAt());
		
		document.add(tabla);
		document.add(tabla2);
		
		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float [] {3.5f, 1, 1, 1});
		
		cell = new PdfPCell(new Phrase("Productos"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		cell.setColspan(4);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Nombre"));
		cell.setBackgroundColor(new Color(204, 255, 204));
		cell.setPadding(6f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Precio"));
		cell.setBackgroundColor(new Color(204, 255, 204));
		cell.setPadding(6f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Cantidad"));
		cell.setBackgroundColor(new Color(204, 255, 204));
		cell.setPadding(6f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Total"));
		cell.setBackgroundColor(new Color(204, 255, 204));
		cell.setPadding(6f);
		tabla3.addCell(cell);
		
		for(ItemFactura item: factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			tabla3.addCell(item.calcularImporte().toString());
		}
		
		cell = new PdfPCell(new Phrase("Total"));
	    cell.setColspan(3);
		cell.setBackgroundColor(new Color(255, 204, 230));
		cell.setPadding(6f);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	    tabla3.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(factura.getTotal().toString()));
		cell.setBackgroundColor(new Color(255, 204, 230));
		cell.setPadding(6f);
	    tabla3.addCell(cell);
	    
	    document.add(tabla3);
		
	}

}
