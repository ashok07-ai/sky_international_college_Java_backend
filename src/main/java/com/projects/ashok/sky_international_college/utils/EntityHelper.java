package com.projects.ashok.sky_international_college.utils;

import com.projects.ashok.sky_international_college.exceptions.ResourceNotFoundException;
import com.projects.ashok.sky_international_college.exceptions.UserAlreadyExistsException;
import com.projects.ashok.sky_international_college.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EntityHelper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public EntityHelper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    /**
     * Converts a DTO object to its entity representation.
     *
     * @param dto the DTO object to convert
     * @param <D> the DTO type
     * @param <E> the entity type
     * @return the entity representation of the DTO
     */
    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    /**
     * Converts an entity object to its DTO representation.
     *
     * @param entity the entity object to convert
     * @param <D> the DTO type
     * @param <E> the entity type
     * @return the DTO representation of the entity
     */
    public <D, E> D convertToDTO(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     *
     * Finds an entity by its ID or throws a ResourceNotFoundException if the entity is not found.
     * @param <T> The type of the entity.
     * @param <ID> The type of the entity's ID.
     * @param repository The JpaRepository for the entity type.
     * @param id The ID of the entity to retrieve.
     * @param resourceName The name of the resource (e.g., "User", "Order") used in the exception message.
     * @return The found entity of type T.
     * @throws ResourceNotFoundException if the entity with the specified ID is not found.
     */
    public <T, ID> T findByIdOrThrow(JpaRepository<T, ID> repository, ID id, String resourceName) {
        // Attempt to find the entity by its ID using the provided repository.
        // If the entity is not found, throw a ResourceNotFoundException with a descriptive message.
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, " not found with ID: " ,id));
    }

    /**
     * Constructs a ResponseEntity containing an ApiResponse with the specified HTTP status,
     * message, and data.
     *
     * @param <T>     The type of the data being returned in the ApiResponse.
     * @param status  The HTTP status to be set for the response (e.g., OK, NOT_FOUND).
     * @param message A descriptive message providing context about the response.
     * @param data    The data to be included in the response body, can be of any type T.
     * @return A ResponseEntity wrapping the ApiResponse containing the provided message and data,
     *         along with the specified HTTP status.
     */
    public  <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(message, data);
        return new ResponseEntity<>(response, status);
    }


    /**
     * Checks if a field (username, email, contact number) already exists in the database.
     * If it does, throws a UserAlreadyExistsException with the appropriate error details.
     *
     * @param fieldValue the value of the field to check
     * @param fieldName the name of the field (used for error messages)
     * @param errorMessage the error message to be included in the exception
     */
    public void checkForDuplicateField(String fieldValue, String fieldName, String errorMessage) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            return; // Skip check if the field value is null or empty (optional validation check)
        }

        boolean fieldExists = false;

        // Check the database for duplicates based on the field name
        switch (fieldName) {
            case "username":
                fieldExists = userRepository.findByUsername(fieldValue).isPresent();
                break;
            case "email":
                fieldExists = userRepository.findByEmail(fieldValue).isPresent();
                break;
            case "contactNumber":
                fieldExists = userRepository.findByContactNumber(fieldValue).isPresent();
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }

        // If the field already exists, throw the exception
        if (fieldExists) {
            throw new UserAlreadyExistsException(
                    "",
                    Map.of(fieldName, errorMessage)
            );
        }
    }

}
