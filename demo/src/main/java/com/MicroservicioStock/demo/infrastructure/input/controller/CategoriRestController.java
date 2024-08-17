package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.handler.ICategoriHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categori")
@RequiredArgsConstructor
public class CategoriRestController {

    private final ICategoriHandler iCategoriHandler;

    @PostMapping()
    public ResponseEntity<Void> saveCategori (@RequestBody CategoriRequest categoriRequest){
        iCategoriHandler.saveCategori(categoriRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
