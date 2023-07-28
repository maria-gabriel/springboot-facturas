package com.webapp.boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({"/inicio", "/home"})
	public String inicio(Model model) {
		model.addAttribute("titulo", "Inicio");
		return "home";
	}
	
	@GetMapping("/error_403")
	public String error() {
		return "error_403";
	}
}
