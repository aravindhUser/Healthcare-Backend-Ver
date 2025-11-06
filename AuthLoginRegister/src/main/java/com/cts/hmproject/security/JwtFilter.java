package com.cts.hmproject.security;




import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import java.io.IOException;
import java.util.List;
 
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	
	private JwtUtil jwtUtil;
 
    @Autowired
    ApplicationContext context;

 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
    	String autHeader =request.getHeader("Authorization");
    	
        if(autHeader != null && autHeader.startsWith("Bearer ")){
        	
            String token=autHeader.substring(7);
            String userEmail=jwtUtil.extractUserEmail(token);
            String role=jwtUtil.extractUserRole(token);
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            	
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
//                System.out.println(authToken);
//              SecurityContextHolder is static holder provided by the spring security to hold the current user's authentication information
                SecurityContextHolder.getContext().setAuthentication(authToken);
 
            }
        }
        
        filterChain.doFilter(request,response);
    }
}
 
