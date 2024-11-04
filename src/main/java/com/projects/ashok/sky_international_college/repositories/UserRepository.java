package com.projects.ashok.sky_international_college.repositories;

import com.projects.ashok.sky_international_college.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Retrieves a User entity based on the provided username.
     *
     * @param username the username of the user to find
     * @return the User entity associated with the given username,
     *         or null if no user is found
     */
    User findByUsername(String username);
}
