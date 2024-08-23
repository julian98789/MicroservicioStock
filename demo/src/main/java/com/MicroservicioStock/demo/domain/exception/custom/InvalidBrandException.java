package com.MicroservicioStock.demo.domain.exception.custom;

public class InvalidBrandException extends RuntimeException{
    public InvalidBrandException(String message) {
        super(message);
    }
}
