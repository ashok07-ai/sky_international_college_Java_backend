package com.projects.ashok.sky_international_college.controllers;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.dtos.UserResponseDTO;
import com.projects.ashok.sky_international_college.exceptions.UserAlreadyExistsException;
import com.projects.ashok.sky_international_college.services.UserService;
import com.projects.ashok.sky_international_college.utils.ApiResponse;
import com.projects.ashok.sky_international_college.utils.EntityHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@Tag(name="User Controller", description = "User Related Information")

public class UserController {
    private final UserService userService;
    private final EntityHelper entityHelper;
    private final BCryptPasswordEncoder passwordEncoder;

    UserController(UserService userService, EntityHelper entityHelper, BCryptPasswordEncoder passwordEncoder){
        this.userService = userService;
        this.entityHelper = entityHelper;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    @PostMapping("register")
    @Operation(summary = "Register new user")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO registeredUser = userService.registerUser(userDTO);
        // Convert to response DTO excluding password
        UserResponseDTO registeredUserResponse = new UserResponseDTO(registeredUser);
        return entityHelper.buildResponse(HttpStatus.CREATED, "User registered successfully!", registeredUserResponse);
    }



}
