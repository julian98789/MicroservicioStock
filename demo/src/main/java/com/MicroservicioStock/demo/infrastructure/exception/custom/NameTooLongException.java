package com.MicroservicioStock.demo.infrastructure.exception.custom;

public class NameTooLongException extends RuntimeException{
    public NameTooLongException(String message) {
        super(message);
    }
}
