package com.projects.ashok.sky_international_college.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;

    /**
     * Constructs a ResourceNotFoundException with the specified resource details.
     *
     * @param resourceName the name of the resource that was not found
     */
    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found with %s: %d", resourceName));
        this.resourceName = resourceName;
    }


    public String getResourceName() {
        return resourceName;
    }
}
