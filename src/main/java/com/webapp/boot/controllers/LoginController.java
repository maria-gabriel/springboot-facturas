package com.webapp.boot.controllers;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Sesión de usuario activa");
			return "redirect:/";
		}
		
		model.addAttribute("titulo", "Iniciar sesión");
		
		return "login";
	}
	
	@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Lógica adicional antes de cerrar sesión si es necesario

        // Invalidar la sesión actual
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        // Redirigir a la página de inicio de sesión o a otra página deseada después de cerrar sesión
        return "redirect:/login?logout";
    }
}
