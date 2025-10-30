package com.cts.hmproject.config;
 
import com.cts.hmproject.service.JWTService;
import com.cts.hmproject.service.DoctorUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import java.io.IOException;
import java.util.List;
 
@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
    private JWTService jwtService;
 
    @Autowired
    ApplicationContext context;
    

    private static final List<String> PUBLIC_URLS = List.of(
        "/doctor/login", "/doctor/register",
        "/patient/login", "/patient/register"
    );

 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
        

    	String path = request.getRequestURI();
    	System.out.println("Request URI: " + path);
    	

        if (PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }


    	
    	String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        System.out.println("AuthHeader :" + authHeader);
        System.out.println("Checking again");
 
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }
 
//        System.out.println("username="+username+" ---- getAuthentication()="+SecurityContextHolder.getContext().getAuthentication());
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(DoctorUserDetailsService.class).loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                
                System.out.println("setAuthentication() token="+authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
 
        filterChain.doFilter(request, response);
    }
}
 