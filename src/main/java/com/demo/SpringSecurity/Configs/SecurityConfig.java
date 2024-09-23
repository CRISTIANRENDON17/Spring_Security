package com.demo.SpringSecurity.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // configuration one -> 
   
    /**
     * Configura la cadena de filtros de seguridad.
     * 
     * @param http Objeto HttpSecurity para configurar la seguridad de las solicitudes HTTP.
     * @return SecurityFilterChain configurada.
     * @throws Exception si ocurre algún error durante la configuración.
     */
    /*  
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (Cross-Site Request Forgery).
                // Las aplicaciones que exponen solo servicios REST suelen ser stateless (sin estado) y usan tokens como JWT (JSON Web Tokens) para la autenticación.
                // Dado que no tienen un estado de sesión del lado del servidor, la protección CSRF no es relevante o necesaria para proteger las solicitudes.
                //Es común desactivar CSRF en aplicaciones que ofrecen APIs RESTful para simplificar la seguridad y evitar errores innecesarios.
            
            .authorizeHttpRequests(authz -> authz // Inicia la configuración de autorizaciones para las solicitudes HTTP.
                .requestMatchers("/v1/index2").permitAll() // Permite acceso sin autenticación al endpoint /v1/index2.
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud.
            )
            .formLogin(form -> form.permitAll()) // Permite el acceso al formulario de inicio de sesión.
            .build(); // Retorna el objeto SecurityFilterChain configurado.
    } 
 */

// --------------------------------------------------------------------------------------------

    // configuration two ->     
     /**
     * Configura la cadena de filtros de seguridad.
     * 
     * @param http Objeto HttpSecurity para configurar la seguridad de las solicitudes HTTP.
     * @return SecurityFilterChain configurada.
     * @throws Exception si ocurre algún error durante la configuración.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (Cross-Site Request Forgery).
                /*
                 * Las aplicaciones que exponen solo servicios REST suelen ser stateless (sin estado) y usan tokens como JWT (JSON Web Tokens) para la autenticación.
                 * Dado que no tienen un estado de sesión del lado del servidor, la protección CSRF no es relevante o necesaria para proteger las solicitudes.
                 * Es común desactivar CSRF en aplicaciones que ofrecen APIs RESTful para simplificar la seguridad y evitar errores innecesarios.
                 */
            .authorizeHttpRequests(authz -> authz // Inicia la configuración de autorizaciones para las solicitudes HTTP.
                .requestMatchers("/v1/index2").permitAll() // Permite acceso sin autenticación al endpoint /v1/index2.
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud.
            )
            .formLogin(form -> form // Habilita la autenticación basada en formulario.
                .successHandler(successHandler()) // Especifica el manejador de éxito al autenticarse correctamente.
                .permitAll() // Permite el acceso al formulario de inicio de sesión a cualquier usuario.
            )
            .sessionManagement(session -> session // Configura la administración de sesiones.
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) 
                /* 
                 * Configura cómo Spring Security gestionará la creación de sesiones.
                 * - ALWAYS: Siempre creará una nueva sesión si no existe y reutilizará si ya existe.
                 * - IF_REQUIRED: Crea una nueva sesión solo si es necesario y reutiliza si ya existe.
                 * - NEVER: No crea sesiones, pero reutiliza si ya hay una sesión existente.
                 * - STATELESS: No crea ni reutiliza sesiones; útil para servicios sin estado como RESTful APIs.
                 */
                .invalidSessionUrl("/login") // URL de redirección cuando la sesión es inválida.
                .maximumSessions(1) // Limita a 1 la cantidad de sesiones activas por usuario.
                /* 
                 * Útil para aplicaciones que desean evitar que un usuario tenga múltiples sesiones activas.
                 * Para aplicaciones multiplataforma, se podría aumentar este número.
                 */
                .expiredUrl("/login") // URL de redirección cuando la sesión expira por inactividad.
                .sessionRegistry(sessionRegistry()) // podemos definir o inyectar un objeto que administre la sesion
            )
            .sessionManagement(session -> session
                .sessionFixation().migrateSession()) 
            /* 
             * Configura la migración de la sesión para protegerse contra ataques de fijación de sesión.
             * migrateSession(): Crea una nueva sesión al autenticarse y transfiere los atributos de la sesión original.
             * newSession():  Crea una nueva sesión al autenticarse y NO transfiere los atributos de la sesión original, NI SUS DATOS.
             * none(): Inhabilitar la seguridad, permite al atacante apropiarse de la session
             */
            .build(); // Retorna el objeto SecurityFilterChain configurado.
    }

    /**
     * Define un manejador de éxito personalizado para redirigir a un endpoint específico después de la autenticación exitosa.
     * 
     * @return AuthenticationSuccessHandler configurado.
     */
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/v1/index"); // Redirige al endpoint /v1/index tras autenticación exitosa.
        };
    }

    /**
     * Define un bean de tipo SessionRegistry que gestiona las sesiones de los usuarios autenticados.
     * 
     * @return una instancia de SessionRegistryImpl que se utilizará para registrar, manejar y consultar 
     *         las sesiones activas de los usuarios en la aplicación.
     * 
     * La clase SessionRegistryImpl es la implementación por defecto de la interfaz SessionRegistry
     * que se encarga de llevar un seguimiento de todas las sesiones de los usuarios en la aplicación.
     * Esto es útil, por ejemplo, para controlar el número de sesiones activas de un usuario o para
     * obtener información de los usuarios que han iniciado sesión.
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
