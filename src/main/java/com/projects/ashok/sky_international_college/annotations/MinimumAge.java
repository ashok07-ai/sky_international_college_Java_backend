package com.projects.ashok.sky_international_college.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom annotation to validate the minimum age requirement.
 * Ensures that a user's age meets a specified minimum value.
 */
@Documented // Indicates that this annotation should be included in Javadoc
@Constraint(validatedBy = MinimumAgeValidator.class) // Specifies the validator class for this constraint
@Target({ ElementType.FIELD }) // Indicates that this annotation can only be applied to fields
@Retention(RetentionPolicy.RUNTIME) // Makes this annotation available at runtime for reflection
public @interface MinimumAge {

    /**
     * The default error message if the minimum age requirement is not met.
     * Can include {value} placeholder for the minimum age specified.
     *
     * @return The default error message
     */
    String message() default "User must be at least {value} years old";

    /**
     * The minimum age required for the annotated field.
     *
     * @return The minimum age value
     */
    int value();

    /**
     * Allows specification of validation groups, used to apply different validation groups.
     *
     * @return The array of classes representing the groups
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients to provide custom payload objects for this constraint.
     * Commonly used to carry metadata information about the constraint violation.
     *
     * @return The array of classes representing the payload
     */
    Class<? extends Payload>[] payload() default {};
}
