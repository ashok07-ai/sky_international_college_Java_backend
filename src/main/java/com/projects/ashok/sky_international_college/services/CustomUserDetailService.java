package com.projects.ashok.sky_international_college.services;

import com.projects.ashok.sky_international_college.configs.AuthDetails;
import com.projects.ashok.sky_international_college.entities.User;
import com.projects.ashok.sky_international_college.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("User with username '{}' not found in the database.", username);
            throw new UsernameNotFoundException("User with username '" + username + "' not found.");
        }

        logger.info("Successfully loaded user with username '{}'.", username);
        return new AuthDetails(user);
    }
}
