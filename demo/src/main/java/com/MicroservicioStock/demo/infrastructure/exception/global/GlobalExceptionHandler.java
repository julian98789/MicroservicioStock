package com.MicroservicioStock.demo.infrastructure.exception.global;


import com.MicroservicioStock.demo.infrastructure.exception.custom.CategoriAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriAlreadyExistsException.class)
    public ResponseEntity<String> handleCategoriAlreadyExistsException(CategoriAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

}
