package com.webapp.boot.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Sesión de usuario activa");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error: Nombre de usuario o contraseña incorrectos");
		}
		
		if(logout != null) {
			model.addAttribute("info", "Sesión de usuario cerrada correctamente");
		}
		
		model.addAttribute("titulo", "Iniciar sesión");
		
		return "/login";
	}

}
