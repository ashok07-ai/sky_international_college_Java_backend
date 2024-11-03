package com.projects.ashok.sky_international_college.dtos;
import com.projects.ashok.sky_international_college.enums.Gender;
import com.projects.ashok.sky_international_college.annotations.MinimumAge;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public class UserDTO {

    @NotBlank(message = "Full name is required!")
    @Size(max = 100, message = "Full name cannot exceed 100 characters!")
    private String fullName;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Password must contain at least one uppercase letter, one number, and one special character!")
    private String password;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String username;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String contactNumber;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be a past date")
    @MinimumAge(value = 18, message = "User must be at least 18 years old")
    private LocalDate dateOfBirth;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
