package com.webapp.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.webapp.boot.auth.handler.LoginHandler;
import com.webapp.boot.models.service.UsuarioServiceJPA;

@Configuration
public class SpringSecurityConfig {

	@Autowired
	private LoginHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*@Autowired
    private DataSource dataSource;*/
	
	 @Autowired
     private UsuarioServiceJPA userDetailService;

     @Autowired
     public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder);
     }
        
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	http.authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/api/**", "/clientes", "/inicio").permitAll()
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
    
    /*
     * CONFIGURACION A TRAVES DE JDBC Y MYSQL CON DATASOURCE --------------------------
     * @Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		build.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");

	}*/
    
    /*
     * CONFIGURACION CON USUARIO INTERNO A TRAVES DE INMEMORY --------------------------
     * @Bean
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
         
}
