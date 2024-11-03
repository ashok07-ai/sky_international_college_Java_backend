package com.projects.ashok.sky_international_college.configs;

import com.projects.ashok.sky_international_college.services.CustomUserDetailService;
import com.projects.ashok.sky_international_college.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private final JWTService jwtService;
    private final CustomUserDetailService userDetailService;

    public JWTFilter(JWTService jwtService, CustomUserDetailService userDetailService) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request.getHeader("Authorization"));
        if (token != null) {
            processToken(token, request);
        }
        filterChain.doFilter(request, response);
    }

    private void processToken(String token, HttpServletRequest request) {
        try {
            String username = jwtService.extractUsernameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    setAuthenticationContext(request, userDetails);
                } else {
                    logger.warn("Invalid JWT token for user: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the JWT token", e);
        }
    }

    private String extractToken(String authHeader) {
        if (authHeader == null) {
            logger.warn("No Authorization header provided");
            return null;
        }
        if (!authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header does not start with 'Bearer ': {}", authHeader);
            return null;
        }
        return authHeader.substring(7);
    }

    private void setAuthenticationContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}