package com.webapp.boot;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.webapp.boot.auth.handler.LoginHandler;

@Configuration
public class SpringSecurityConfig {

	@Autowired
	private LoginHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private DataSource dataSource;
        
    /*@Bean
    public UserDetailsService userDetailsService()throws Exception{
                    
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
               .withUsername("jhon")
               .password(passwordEncoder.encode("12345"))
               .roles("USER")
               .build());
 
        manager.createUser(User
               .withUsername("admin")
               .password(passwordEncoder.encode("admin"))
               .roles("ADMIN","USER")
               .build());
            
        return manager;
    }*/
         
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
                		.loginProcessingUrl("/login")
                		.defaultSuccessUrl("/home", true)
                		.failureUrl("/login?error")
                        .permitAll()
                        .successHandler(successHandler))
                
                .logout(logout -> logout.permitAll())
                .exceptionHandling(handling -> handling.accessDeniedPage("/error_403"));
 
        return http.build();
    }
    
    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?")
                .and().build();
    }
}
