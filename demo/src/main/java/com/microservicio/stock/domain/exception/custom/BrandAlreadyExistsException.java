package com.microservicio.stock.domain.exception.custom;

public class BrandAlreadyExistsException extends RuntimeException{
    public BrandAlreadyExistsException(String message) {
        super(message);
    }
}
