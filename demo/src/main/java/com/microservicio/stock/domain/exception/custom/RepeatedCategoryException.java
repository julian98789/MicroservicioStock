package com.microservicio.stock.domain.exception.custom;

public class RepeatedCategoryException extends RuntimeException{
    public RepeatedCategoryException(String message) {
        super(message);
    }
}
