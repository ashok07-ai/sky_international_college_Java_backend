package com.projects.ashok.sky_international_college.controllers;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.dtos.responseDTOs.UserResponseDTO;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
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

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO registeredUser = userService.registerUser(userDTO);

        // Convert to response DTO excluding password
        UserResponseDTO registeredUserResponse = new UserResponseDTO(registeredUser);
        return entityHelper.buildResponse(HttpStatus.CREATED, "User registered successfully!", registeredUserResponse);
    }

    @GetMapping("")
    @Operation(summary = "Get all user details")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUser(){
        List<UserDTO> allUserDetails = userService.getAllUsers();
        return entityHelper.buildResponse(HttpStatus.OK, "User details fetched successfully!", allUserDetails);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get all user detail by ID")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable UUID userId){
        UserDTO getUserDetail = userService.getUserById(userId);
        UserResponseDTO getUserDetailResponse = new UserResponseDTO(getUserDetail);
        return entityHelper.buildResponse(HttpStatus.OK, "User detail fetched successfully!", getUserDetailResponse);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update the existing user with ID")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@Valid @RequestBody UserResponseDTO userResponseDTO, @PathVariable UUID userId){
        UserDTO updatedUser = userService.updateUser(userResponseDTO, userId);

        // Convert to response DTO excluding password
        UserResponseDTO updateUserResponse = new UserResponseDTO(updatedUser);
        return entityHelper.buildResponse(HttpStatus.OK, "User updated successfully!", updateUserResponse);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);
        return entityHelper.buildResponse(HttpStatus.OK, "User deleted successfully!", null);
    }



}
