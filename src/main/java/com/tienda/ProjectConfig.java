package com.tienda;

import java.util.Locale; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.MessageSource; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.context.support.ResourceBundleMessageSource; 
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.web.servlet.LocaleResolver; 
import org.springframework.web.servlet.config.annotation.InterceptorRegistry; 
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor; 
import org.springframework.web.servlet.i18n.SessionLocaleResolver; 

@Configuration
public class ProjectConfig implements WebMvcConfigurer {
    /* Los siguientes métodos son para incorporar el tema de internacionalización en el proyecto */
    
    /* localeResolver se utiliza para crear una sesión de cambio de idioma*/
    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }

    /* localeChangeInterceptor se utiliza para crear un interceptor de cambio de idioma*/
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChangeInterceptor());
    }

    //Bean para poder acceder a los Messages.properties en código...
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource= new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    /* Los siguiente métodos son para implementar el tema de seguridad dentro del proyecto */ 
    @Override 
    public void addViewControllers(ViewControllerRegistry registry) {  //lo de addviewcontrollers es para que todos tienen acceso a esto
        registry.addViewController("/").setViewName("index"); 
        registry.addViewController("/index").setViewName("index"); 
        registry.addViewController("/login").setViewName("login"); 
        registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo"); 
    } 
 
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        http 
                .authorizeHttpRequests((request) -> request 
                .requestMatchers("/","/index","/errores/**", 
                        "/carrito/**","/pruebas/**","/reportes/**", 
                        "/registro/**","/js/**","/webjars/**") 
                        .permitAll() 
                .requestMatchers( 
                        "/producto/nuevo","/producto/guardar", 
                        "/producto/modificar/**","/producto/eliminar/**", 
                        "/categoria/nuevo","/categoria/guardar", 
                        "/categoria/modificar/**","/categoria/eliminar/**", 
                        "/usuario/nuevo","/usuario/guardar", 
                        "/usuario/modificar/**","/usuario/eliminar/**", 
                        "/reportes/**" 
                ).hasRole("ADMIN") //solo el admin lo puede ver
                .requestMatchers( 
                        "/producto/listado", 
                        "/categoria/listado", 
                        "/usuario/listado" 
                ).hasAnyRole("ADMIN", "VENDEDOR") //el admin o vendedor lo pueden ver
                .requestMatchers("/facturar/carrito") 
                .hasRole("USER") 
                ) 
                .formLogin((form) -> form 
                .loginPage("/login").permitAll()) 
                .logout((logout) -> logout.permitAll()); 
        return http.build(); 
    } 
    
    @Autowired 
    private UserDetailsService userDetailsService; 
 
    @Autowired 
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {  //permite que el usuario se autentique
        build.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()); 
    } 
}

