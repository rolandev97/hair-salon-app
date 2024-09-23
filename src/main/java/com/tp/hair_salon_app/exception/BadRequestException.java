package com.tp.hair_salon_app.exception;

public class BadRequestException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    public BadRequestException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s The length of  %s : '%s' not correct", resourceName, fieldName, fieldValue)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
