package com.projects.ashok.sky_international_college.services.impl;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.dtos.responseDTOs.UserResponseDTO;
import com.projects.ashok.sky_international_college.dtos.swaggerDTOs.LoginRequestDTO;
import com.projects.ashok.sky_international_college.entities.User;
import com.projects.ashok.sky_international_college.repositories.UserRepository;
import com.projects.ashok.sky_international_college.services.JWTService;
import com.projects.ashok.sky_international_college.services.UserService;
import com.projects.ashok.sky_international_college.utils.EntityHelper;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
   private final EntityHelper entityHelper;
   private final AuthenticationManager authenticationManager;
   private final JWTService jwtService;

   public UserServiceImpl(UserRepository userRepository, EntityHelper entityHelper, AuthenticationManager authenticationManager, JWTService jwtService){
       this.userRepository = userRepository;
       this.entityHelper = entityHelper;
       this.authenticationManager = authenticationManager;
       this.jwtService = jwtService;
   }


    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        // Check for duplicates and throw exceptions if necessary
        entityHelper.checkForDuplicateField(userDTO.getUsername(), "username", "Username already exists");
        entityHelper.checkForDuplicateField(userDTO.getEmail(), "email", "Email already exists");
        entityHelper.checkForDuplicateField(userDTO.getContactNumber(), "contactNumber", "Contact number already exists");

        // Save the new user and return the UserDTO
        User userDetails = userRepository.save(entityHelper.convertToEntity(userDTO, User.class));
       return entityHelper.convertToDTO(userDetails, userDTO.getClass());
    }

    @Override
    public UserDTO updateUser(UserResponseDTO userResponseDTO, UUID userId) {
        return null;
    }

    @Override
    public UserDTO getUserById(UUID userId) {
        User userDetails = entityHelper.findByIdOrThrow(userRepository, userId, "User");
        return entityHelper.convertToDTO(userDetails, UserDTO.class);

    }

    @Override
    public List<UserDTO> getAllUsers() {
       return userRepository.findAll().stream().map(user->entityHelper.convertToDTO(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(UUID userId) {
        User userDetails = entityHelper.findByIdOrThrow(userRepository, userId, "User");
        userRepository.delete(userDetails);
    }

    @Override
    public String verify(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        return authentication.isAuthenticated() ? jwtService.generateToken(loginRequestDTO.getUsername()) : "Failed";
    }

}

