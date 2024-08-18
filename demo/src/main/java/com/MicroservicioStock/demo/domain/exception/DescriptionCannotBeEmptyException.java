package com.MicroservicioStock.demo.domain.exception;

public class DescriptionCannotBeEmptyException extends RuntimeException {
    public DescriptionCannotBeEmptyException(String message) {
        super(message);
    }
}
