package com.microservicio.stock.domain.exception.custom;

public class InvalidBrandException extends RuntimeException{
    public InvalidBrandException(String message) {
        super(message);
    }
}
