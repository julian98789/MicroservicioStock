package com.microservicio.stock.domain.exception.custom;

public class ValidationExceptions extends RuntimeException{
    public ValidationExceptions(String message) {
        super(message);
    }
}
