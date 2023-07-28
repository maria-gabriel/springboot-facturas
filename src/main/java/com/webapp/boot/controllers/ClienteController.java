package com.webapp.boot.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webapp.boot.components.paginator.PageRender;
import com.webapp.boot.models.entity.Cliente;
import com.webapp.boot.models.service.ClienteServiceInterface;
import com.webapp.boot.models.service.FileServiceInterface;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private ClienteServiceInterface clienteService;
	
	@Autowired
	private FileServiceInterface fileService;

	@GetMapping("/clientes-api")
	public @ResponseBody List<Cliente> listar_api(){
		return clienteService.findAll();
	}
	
	@GetMapping({"/clientes", "/"})
	public String clientes(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			if(hasRole("ROLE_ADMIN")) {
				System.out.println("Hola "+ auth.getName()+ " tienes acceso");
			}else {
				System.out.println("Hola "+ auth.getName()+ " NO tienes acceso");
			}
		}
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		List<Cliente> clientesJson = clienteService.findAll();
		Long totalRegistros = clienteService.total();
		PageRender<Cliente> pageRender = new PageRender<>("/clientes", clientes);
		model.addAttribute("titulo", "Clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("clientesJson", clientesJson);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", totalRegistros);
		
		return "clientes";
	}
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		Resource recurso = null;
		
		try {
			recurso = fileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@GetMapping("/cliente/{id}")
	public String cliente(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		if(cliente == null)
		{
			flash.addFlashAttribute("error", "No se encontró el registro");
			return "redirect:/clientes";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", cliente.getNombre() + " " + cliente.getApellido());
		
		return "cliente";
	}
	
	@GetMapping("/formulario")
	public String formulario (Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Nuevo Cliente");
		model.put("cliente", cliente);
		
		return "formulario";
	}
	
	@GetMapping("/formulario/{id}")
	public String modificar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if(id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "No se encontró el registro");
				return "redirect:/clientes";
			}
		} else {
			flash.addFlashAttribute("error", "Identificador no válido");
			return "redirect:/clientes";
		}
		
		model.put("titulo", "Editar Cliente");
		model.put("cliente", cliente);
		
		return "formulario";
	}
	
	@PostMapping("/formulario")
	public String insertar(@Valid Cliente cliente, BindingResult result, @RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes flash) {
		
		if(result.hasErrors()) {
			return "formulario";
		}
		
		if(!foto.isEmpty()) {
			
			if(cliente.getId() != null && cliente.getFoto().length() > 0) {
				fileService.delete(cliente.getFoto());
			}
			
			String uniqueFilename = null;
			try {
				uniqueFilename = fileService.copy(foto);
			} catch (IOException e) {
				flash.addFlashAttribute("error", "No se ha subido "+foto.getOriginalFilename());
				e.printStackTrace();
			}
			
			if(uniqueFilename != null) {
				flash.addFlashAttribute("info", "Se ha subido correctamente " + uniqueFilename);
				cliente.setFoto(uniqueFilename);
			}
			
		}
		
		String mnsFlash = (cliente.getId() == null ? "Registro guardado con éxito" : "Registro modificado con éxito");
		Boolean toRed = (cliente.getId() == null ? false : true);

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mnsFlash);
		if (toRed) {
			return "redirect:cliente/"+cliente.getId();
		} else {
			return "redirect:clientes";
		}
		
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			Cliente cliente = clienteService.findOne(id);
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Registro eliminado con éxito");
			
			if(fileService.delete(cliente.getFoto()) && cliente.tieneFoto()) {
				flash.addFlashAttribute("info", "Se eliminó el archivo " + cliente.getFoto());
			}
			
		}
		return "redirect:/clientes";
	}
	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				return true;
			}
		}
		
		return false;
	}
}
