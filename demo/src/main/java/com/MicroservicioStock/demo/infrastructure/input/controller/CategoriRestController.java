package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.application.handler.ICategoriHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categori")
@RequiredArgsConstructor
public class CategoriRestController {

    private final ICategoriHandler iCategoriHandler;

    @PostMapping()
    @Operation(summary = "Save a category", description = "Save a new category to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Category already exists"),
    })
    public ResponseEntity<CategoriResponse> saveCategori(@Valid @RequestBody CategoriRequest categoriRequest) {
        CategoriResponse savedCategori = iCategoriHandler.saveCategori(categoriRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategori);
    }

    @GetMapping
    public List<CategoriResponse> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return iCategoriHandler.getCategories(page, size, sort, ascending);
    }


}
