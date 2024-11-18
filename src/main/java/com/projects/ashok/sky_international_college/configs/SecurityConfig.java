package com.projects.ashok.sky_international_college.configs;

import com.projects.ashok.sky_international_college.services.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());
    private final CustomUserDetailService customeUserDetailService;
    private final JWTFilter jwtFilter;

    public SecurityConfig(CustomUserDetailService customeUserDetailService, JWTFilter jwtFilter) {
        this.customeUserDetailService = customeUserDetailService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try{
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers(
                                    "/api/v1/public/auth/login",
                                    "/v3/api-docs/**",       // For OpenAPI docs
                                    "/swagger-ui/**",        // For Swagger UI resources
                                    "/swagger-ui.html"       // For the Swagger UI HTML page
                            ).permitAll()
                            .anyRequest().authenticated())
//                    .oauth2Login(Customizer.withDefaults())
//                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }catch (Exception e) {
            logger.log(Level.SEVERE, "Error configuring SecurityFilterChain", e);
            throw new SecurityException("Failed to configure security filter chain", e);
        }
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customeUserDetailService);

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error retrieving AuthenticationManager", e);
            throw new SecurityException("Failed to retrieve authentication manager", e);
        }
    }
}
