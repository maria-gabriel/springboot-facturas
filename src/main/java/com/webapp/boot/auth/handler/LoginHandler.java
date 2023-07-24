package com.webapp.boot.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginHandler implements AuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
         
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		flashMap.put("success", "Hola " +authentication.getName()+ ", sesión iniciada correctamente");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
         
		if(authentication != null) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String username = userDetails.getUsername();
	        System.out.println("The user " + username + " has logged in.");
		}
         
        response.sendRedirect(request.getContextPath());
    }
	
}
