package com.MicroservicioStock.demo.domain.exception;

public class NameTooLongException extends RuntimeException{
    public NameTooLongException(String message) {
        super(message);
    }
}
