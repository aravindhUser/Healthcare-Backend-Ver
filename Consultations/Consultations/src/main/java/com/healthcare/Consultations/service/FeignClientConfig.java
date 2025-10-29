package com.healthcare.Consultations.service;

import feign.RequestInterceptor;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.context.request.RequestContextHolder;  
import org.springframework.web.context.request.ServletRequestAttributes;  
  
@Configuration  
public class FeignClientConfig {  
  
    @Bean  
    public RequestInterceptor requestInterceptor() {  
        return requestTemplate -> {  
            ServletRequestAttributes attributes = (ServletRequestAttributes) 
RequestContextHolder.getRequestAttributes();  
            if (attributes != null && attributes.getRequest() != null) {  
                String token = attributes.getRequest().getHeader("Authorization");  
                if (token != null && !token.isEmpty()) {  
                    if (!token.startsWith("Bearer ")) {  
                        token = "Bearer " + token;  
                    }  
                    requestTemplate.header("Authorization", token);  
                }  
            }  
        };  
    }  
}  
