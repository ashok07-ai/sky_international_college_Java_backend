package com.projects.ashok.sky_international_college.configs;

import com.projects.ashok.sky_international_college.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom implementation of UserDetails for Spring Security authentication.
 */
public class AuthDetails implements UserDetails {
    private final User user;

    /**
     * Constructs an AuthDetails object with the specified User.
     *
     * @param user the User object containing user details
     */
    public AuthDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Returns a collection of granted authorities assigned to the user
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        // Returns the user's password
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Returns the user's username
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indicates whether the user's account is expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indicates whether the user's account is locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indicates whether the user's credentials (password) are expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Indicates whether the user's account is enabled
        return true;
    }
}
