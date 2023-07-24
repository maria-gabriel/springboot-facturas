package com.webapp.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.webapp.boot.auth.handler.LoginHandler;

@Configuration
public class SpringSecurityConfig {

	@Autowired
	private LoginHandler successHandler;
	
	@Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
        
    @Bean
    public UserDetailsService userDetailsService()throws Exception{
                    
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
               .withUsername("jhon")
               .password(passwordEncoder().encode("12345"))
               .roles("USER")
               .build());
 
        manager.createUser(User
               .withUsername("admin")
               .password(passwordEncoder().encode("admin"))
               .roles("ADMIN","USER")
               .build());
            
        return manager;
    }
         
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	http.authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/clientes", "/inicio").permitAll()
                .requestMatchers("/cliente/**").hasAnyRole("USER")
                .requestMatchers("/uploads/**").hasAnyRole("USER")
                .requestMatchers("/formulario/**").hasAnyRole("ADMIN")
                .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                .requestMatchers("/facturas/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin(login -> login
                		.loginPage("/login")
                		.defaultSuccessUrl("/home", true)
                        .permitAll()
                        .successHandler(successHandler))
                
                .logout(logout -> logout.permitAll())
                .exceptionHandling(handling -> handling.accessDeniedPage("/error_403"));
 
        return http.build();
    }
}
