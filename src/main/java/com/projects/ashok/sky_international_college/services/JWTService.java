package com.projects.ashok.sky_international_college.services;


import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    /**
     * Generates a new JWT (JSON Web Token) for the specified user.
     *
     * @param username the username of the user for whom the JWT is generated.
     * @return a JWT as a String that can be used to verify the identity
     *         of the authenticated user in subsequent requests.
     */
    String generateToken(String username);

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token from which the username is to be extracted
     * @return the extracted username as a String
     */
    String extractUsernameFromToken(String token);

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token the JWT token to be validated
     * @param userDetails the user details to verify the token against
     * @return true if the token is valid for the specified user; false otherwise
     */
    boolean validateToken(String token, UserDetails userDetails);
}
