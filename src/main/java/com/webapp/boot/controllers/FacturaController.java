package com.webapp.boot.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webapp.boot.models.entity.Cliente;
import com.webapp.boot.models.entity.Factura;
import com.webapp.boot.models.entity.ItemFactura;
import com.webapp.boot.models.entity.Producto;
import com.webapp.boot.models.service.ClienteServiceInterface;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("factura")
@RequestMapping("/facturas")
public class FacturaController {
	
	@Autowired
	private ClienteServiceInterface clienteService;

	//private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/formulario/{clienteId}")
	public String formulario (@PathVariable(value="clienteId") Long clienteId ,Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		
		if(clienteId > 0) {
			cliente = clienteService.findOne(clienteId);
			if(cliente == null) {
				flash.addFlashAttribute("error", "No se encontró el registro");
				return "redirect:/clientes";
			}
		}else {
			flash.addFlashAttribute("error", "Identificador no válido");
			return "redirect:/clientes";
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.put("titulo", "Nueva Factura");
		model.put("factura", factura);
		return "facturas/formulario";
	}
	
	@GetMapping(value="/cargar-productos/{term}", produces="application/json")
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		return clienteService.findByNombre(term);
	}
	
	@PostMapping(value="/formulario")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Nueva factura");
			return "facturas/formulario";
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Nueva factura");
			model.addAttribute("error", "La factura no se puede registrar sin productos");
			return "facturas/formulario";
		}
		
		for(int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
		}
		
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Registro guardado con éxito");
		
		return "redirect:/cliente/" + factura.getCliente().getId();
	}
	
	@GetMapping("/factura/{id}")
	public String factura (@PathVariable(value="id") Long id ,Map<String, Object> model, RedirectAttributes flash) {
		Factura factura = clienteService.findFacturaById(id);
		if(factura == null) {
			flash.addFlashAttribute("error", "Registro no encontrado");
			return "redirect:/clientes";
		}
		model.put("titulo", factura.getDescripcion());
		model.put("factura", factura);
		
		return "facturas/factura";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Factura factura = clienteService.findFacturaById(id);
		
		if(factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Registro eliminado con exito");
			return "redirect:/cliente/"+factura.getCliente().getId();
		}
		flash.addFlashAttribute("error","No se encontró el registro");
		return "redirect:/clientes";
	}
	
}
