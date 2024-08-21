package com.MicroservicioStock.demo.domain.exception.custom;

public class CategoriAlreadyExistsException extends RuntimeException{
    public CategoriAlreadyExistsException(String message) {
        super(message);
    }
}
