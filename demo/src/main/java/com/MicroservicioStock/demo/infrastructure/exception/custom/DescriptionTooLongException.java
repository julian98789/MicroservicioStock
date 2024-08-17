package com.MicroservicioStock.demo.infrastructure.exception.custom;

public class DescriptionTooLongException extends RuntimeException {
    public DescriptionTooLongException(String message) {
        super(message);
    }
}
