package com.webapp.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.webapp.boot.models.service.FileServiceInterface;

@SpringBootApplication
public class SpringBootFacturasApplication implements CommandLineRunner {

	@Autowired
	FileServiceInterface fileService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootFacturasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileService.deleteAll();
		fileService.init();
		
		String password = "12345";
		
		for(int i = 0; i<2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}
		
	}

}
