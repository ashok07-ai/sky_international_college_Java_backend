package com.projects.ashok.sky_international_college.repositories;

import com.projects.ashok.sky_international_college.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
