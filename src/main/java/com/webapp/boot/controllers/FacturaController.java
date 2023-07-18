package com.webapp.boot.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webapp.boot.models.entity.Cliente;
import com.webapp.boot.models.entity.Factura;
import com.webapp.boot.models.entity.Producto;
import com.webapp.boot.models.service.ClienteServiceInterface;

@Controller
@SessionAttributes("factura")
@RequestMapping("/facturas")
public class FacturaController {
	
	@Autowired
	private ClienteServiceInterface clienteService;

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
	
}
