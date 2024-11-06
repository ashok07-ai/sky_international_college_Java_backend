package com.projects.ashok.sky_international_college.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

/**
 * Custom validator class for the @MinimumAge annotation.
 * Ensures that a user meets the specified minimum age requirement.
 */
public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    // Minimum age value, set during initialization
    private int minAge;

    /**
     * Initializes the validator with the minimum age specified
     * in the @MinimumAge annotation.
     *
     * @param constraintAnnotation The @MinimumAge annotation instance.
     */
    @Override
    public void initialize(MinimumAge constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }

    /**
     * Checks if the provided date of birth satisfies the minimum age requirement.
     *
     * @param dateOfBirth The date of birth to validate.
     * @param context The validation context.
     * @return true if dateOfBirth is at least the minimum age; false otherwise.
     * If dateOfBirth is null, it returns true and allows @NotNull to handle null checks.
     */
    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true; // Let @NotNull handle nulls
        }

        // Calculate the age by comparing dateOfBirth to the current date
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= minAge;
    }
}
