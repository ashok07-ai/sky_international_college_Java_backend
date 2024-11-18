package com.projects.ashok.sky_international_college.dtos.responseDTOs;

import com.projects.ashok.sky_international_college.dtos.UserDTO;
import com.projects.ashok.sky_international_college.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String username;
    private Gender gender;
    private String contactNumber;
    private String address;
    private LocalDate dateOfBirth;

    public UserResponseDTO(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.fullName = userDTO.getFullName();
        this.email = userDTO.getEmail();
        this.username = userDTO.getUsername();
        this.gender = userDTO.getGender();
        this.contactNumber = userDTO.getContactNumber();
        this.address = userDTO.getAddress();
        this.dateOfBirth = userDTO.getDateOfBirth();
    }

    public UUID getId() {
        return id;
    }

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
