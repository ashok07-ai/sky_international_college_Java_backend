package com.projects.ashok.sky_international_college.services.impl;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.entities.User;
import com.projects.ashok.sky_international_college.repositories.UserRepository;
import com.projects.ashok.sky_international_college.services.JWTService;
import com.projects.ashok.sky_international_college.services.UserService;
import com.projects.ashok.sky_international_college.utils.EntityHelper;
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
    public UserDTO registerUser(UserDTO userDTO) {
       User userDetails = userRepository.save(entityHelper.convertToEntity(userDTO, User.class));
       return entityHelper.convertToDTO(userDetails, userDTO.getClass());
    }

    @Override
    public UserDTO updateUser(UserDTO user, Integer userId) {
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
    public String verify(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "Failed";
    }

}

