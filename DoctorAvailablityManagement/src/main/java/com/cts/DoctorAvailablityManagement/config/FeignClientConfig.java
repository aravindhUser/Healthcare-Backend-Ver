package com.cts.DoctorAvailablityManagement.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
    	System.out.println("requestInterceptor()------------");
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            System.out.println("attributes="+attributes);
            if (attributes != null && attributes.getRequest() != null) {
                String token = attributes.getRequest().getHeader("Authorization");
                
                if (token != null && !token.isEmpty()) {
                    // Ensure the token has the Bearer prefix
                    if (!token.startsWith("Bearer ")) {
                        token = "Bearer " + token;
                    }
                    requestTemplate.header("Authorization", token);

                    // Optional: Log the token for debugging
                    System.out.println("Forwarding Authorization token: " + token);
                } else {
                    System.out.println("No Authorization header found in the incoming request.");
                }
            } else {
                System.out.println("Request attributes are null. Cannot forward Authorization header.");
            }
        };
    }
}
