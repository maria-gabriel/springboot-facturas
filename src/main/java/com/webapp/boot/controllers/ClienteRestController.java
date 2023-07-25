package com.webapp.boot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.boot.models.entity.Cliente;
import com.webapp.boot.models.service.ClienteServiceInterface;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	
	@Autowired
	private ClienteServiceInterface clienteService;

	@GetMapping("/clientes")
	public List<Cliente> clientes(){
		return clienteService.findAll();
	}
}
