package com.MicroservicioStock.demo.infrastructure.exception.global;


import com.MicroservicioStock.demo.domain.exception.DescriptionCannotBeEmptyException;
import com.MicroservicioStock.demo.domain.exception.DescriptionTooLongException;
import com.MicroservicioStock.demo.domain.exception.NameCannotBeEmptyException;
import com.MicroservicioStock.demo.domain.exception.NameTooLongException;
import com.MicroservicioStock.demo.infrastructure.exception.custom.CategoriAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(DescriptionTooLongException.class)
    public ResponseEntity<String> handleDescriptionTooLongException(DescriptionTooLongException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NameTooLongException.class)
    public ResponseEntity<String> handleNameTooLongException(NameTooLongException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NameCannotBeEmptyException.class)
    public ResponseEntity<String> handleNameCannotBeEmptyException(NameCannotBeEmptyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DescriptionCannotBeEmptyException.class)
    public ResponseEntity<String> handleDescriptionCannotBeEmptyException(DescriptionCannotBeEmptyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
