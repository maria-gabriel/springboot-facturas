package com.webapp.boot;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.webapp.boot.auth.filter.JWTAuthenticationFilter;
import com.webapp.boot.auth.filter.JWTAuthorizationFilter;
import com.webapp.boot.auth.service.JWTServiceInterface;
import com.webapp.boot.models.service.UsuarioServiceJPA;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	 @Autowired
     private UsuarioServiceJPA userDetailService;
	 
	 @Autowired
	 private JWTServiceInterface jwtService;
	 
	 @Autowired
     private AuthenticationConfiguration authenticationConfiguration;
	 
	 @Bean
     public AuthenticationManager authenticationManager() throws Exception {
         return authenticationConfiguration.getAuthenticationManager();
     }

     @Bean
     public AuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userDetailService);
         authProvider.setPasswordEncoder(passwordEncoder);
         return authProvider;
     }
        
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                		request -> request.requestMatchers("/", "/css/**", "/js/**", "/images/**", "/clientes", "/inicio")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(
                		manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService));
        return http.build();
    }
    
         
}
