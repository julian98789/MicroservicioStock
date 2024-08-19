package com.MicroservicioStock.demo.infrastructure.exception.custom;

public class CategoriAlreadyExistsException extends RuntimeException{
    public CategoriAlreadyExistsException(String message) {
        super(message);
    }
}
