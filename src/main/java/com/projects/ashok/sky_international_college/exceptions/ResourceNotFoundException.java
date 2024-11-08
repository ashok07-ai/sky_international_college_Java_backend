package com.projects.ashok.sky_international_college.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    /**
     * Constructs a ResourceNotFoundException with the specified resource details.
     *
     * @param resourceName the name of the resource that was not found
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: %d", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName(){
        return fieldName;
    }

    public Object getFieldValue(){
        return fieldValue;
    }
}
