package com.inn.cafe.jwt;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private String strBearer = "Bearer ";

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomerUsersDetailsService customerUsersDetailsService;

    Claims claims = null;
    private String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().matches("/user/login|/user/forgot_password|/user/signup")){
            filterChain.doFilter(request, response);
        }else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            if(authorizationHeader != null && authorizationHeader.contains(strBearer)){
                token = authorizationHeader.substring(strBearer.length());
                username = jwtUtil.getUsername(token);
                claims = jwtUtil.getClaims(token);
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = customerUsersDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUsername(){
        return username;
    }

}
