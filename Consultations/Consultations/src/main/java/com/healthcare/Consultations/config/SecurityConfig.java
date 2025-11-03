package com.healthcare.Consultations.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {


   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration config = new CorsConfiguration();

       // Allow frontend origin (adjust if hosted elsewhere)
       config.setAllowedOriginPatterns(List.of("http://localhost:4200"));

       // Allowed HTTP methods
       config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

       // Allowed headers
       config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

       // Allow credentials if using cookies/JWT tokens
       config.setAllowCredentials(true);

       // Register configuration for all endpoints
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", config);

       return source;
   }
   
   
// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     http
//         // Enable global CORS configuration (check corsConfigurationSource)
//         .cors().and()
//         // Disable CSRF (since this is an API backend)
//         .csrf().disable()
//         // Allow all endpoints 
//         .authorizeHttpRequests(auth -> auth
//             .anyRequest().permitAll()
//         );
//
//     return http.build();
// }
}

