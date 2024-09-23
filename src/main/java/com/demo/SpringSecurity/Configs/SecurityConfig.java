package com.demo.SpringSecurity.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF.
            .authorizeHttpRequests(authz -> authz // Inicia la configuración de autorizaciones para las solicitudes HTTP.
                .requestMatchers("/v1/index2").permitAll() // Permite acceso sin autenticación al endpoint /v1/index2.
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud.
            )
            .formLogin(form -> form.permitAll()) // Permite el acceso al formulario de inicio de sesión.
            .build(); // Retorna el objeto SecurityFilterChain configurado.
    } 


}
