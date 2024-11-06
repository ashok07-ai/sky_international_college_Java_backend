package com.projects.ashok.sky_international_college.repositories;

import com.projects.ashok.sky_international_college.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Retrieves a User entity based on the provided username.
     *
     * This method checks if a user exists in the database with the given username.
     * It returns an Optional<User> to handle cases where no user is found.
     *
     * @param username the username of the user to find
     * @return an Optional<User> containing the user entity if found,
     *         or an empty Optional if no user is found with the specified username
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves a User entity based on the provided email address.
     *
     * This method checks if a user exists in the database with the given email address.
     * It returns an Optional<User> to handle cases where no user is found.
     *
     * @param email the email address of the user to find
     * @return an Optional<User> containing the user entity if found,
     *         or an empty Optional if no user is found with the specified email
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves a User entity based on the provided contact number.
     *
     * This method checks if a user exists in the database with the given contact number.
     * It returns an Optional<User> to handle cases where no user is found.
     *
     * @param contactNumber the contact number of the user to find
     * @return an Optional<User> containing the user entity if found,
     *         or an empty Optional if no user is found with the specified contact number
     */
    Optional<User> findByContactNumber(String contactNumber);


}
