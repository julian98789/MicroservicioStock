package com.microservicio.stock.domain.exception.custom;

public class NameAlreadyExistsException extends RuntimeException{
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
