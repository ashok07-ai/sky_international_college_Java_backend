package com.projects.ashok.sky_international_college.services;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.dtos.responseDTOs.UserResponseDTO;
import com.projects.ashok.sky_international_college.dtos.swaggerDTOs.LoginRequestDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Registers a new user in the system.
     * @param user The user Data Transfer Object (DTO) containing the user's details.
     * @return The registered user information after successful creation.
     */
    UserDTO registerUser(UserDTO user);

    /**
     * Updates an existing user's information.
     * @param userResponseDTO The updated user DTO with new information.
     * @return The updated user data after successful update.
     */
    UserDTO updateUser(UserResponseDTO userResponseDTO, UUID userId);

    /**
     * Retrieves the details of a user based on the provided user ID.
     * @param userId The unique identifier of the user to fetch.
     * @return The user DTO containing the user's information.
     */
    UserDTO getUserById(UUID userId);

    /**
     * Retrieves a list of all users present in the system.
     * @return A list of UserDTOs representing all registered users.
     */
    List<UserDTO> getAllUsers();

    /**
     * Deletes a user from the system based on their ID.
     * @param userId The unique identifier of the user to be deleted.
     */
    void deleteUser(UUID userId);

    /**
     * Verifies the given user's credentials and generates a JWT upon successful authentication.
     *
     * @param loginRequestDTO the User object containing authentication details for verification.
     * @return a JWT as a String if the user is successfully authenticated; otherwise, returns an error message.
     */
    String verify(LoginRequestDTO loginRequestDTO);
}
