package com.MicroservicioStock.demo.domain.exception;

public class NameCannotBeEmptyException extends RuntimeException {
    public NameCannotBeEmptyException(String message) {
        super(message);
    }
}