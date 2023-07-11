package com.webapp.boot;

import java.nio.file.Paths;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Config implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		//registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}

	
}
