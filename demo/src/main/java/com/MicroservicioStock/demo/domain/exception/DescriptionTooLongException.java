package com.MicroservicioStock.demo.domain.exception;

public class DescriptionTooLongException extends RuntimeException {
    public DescriptionTooLongException(String message) {
        super(message);
    }
}
