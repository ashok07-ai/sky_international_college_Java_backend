package com.projects.ashok.sky_international_college.controllers;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.entities.User;
import com.projects.ashok.sky_international_college.services.UserService;
import com.projects.ashok.sky_international_college.utils.ApiResponse;
import com.projects.ashok.sky_international_college.utils.EntityHelper;
import com.projects.ashok.sky_international_college.utils.ErrorApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth/")
@Tag(name="Login Controller", description = "User authentication")

public class AuthController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager manager;
    private final EntityHelper entityHelper;

    AuthController(UserDetailsService userDetailsService, UserService userService, AuthenticationManager manager, EntityHelper entityHelper){
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.manager = manager;
        this.entityHelper = entityHelper;
    }


    @PostMapping("login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User user) {

        this.doAuthenticate(user.getUsername(), user.getPassword());
        String token = userService.verify(user);
        return entityHelper.buildResponse(HttpStatus.OK, "Logged in successfully!", token);
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }
}
