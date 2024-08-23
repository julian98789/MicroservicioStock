package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriResponse;
import com.MicroservicioStock.demo.application.handler.categoriHandler.ICategoriHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @PostMapping
    @Operation(
            summary = "Save a category",
            description = "Create a new category and save it to the database. If a category with the same name already exists, an error will be returned."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string")
                    )
            )
    })
    public ResponseEntity<CategoriResponse> saveCategori(@Valid @RequestBody CategoriRequest categoriRequest) {
        CategoriResponse savedCategori = iCategoriHandler.saveCategori(categoriRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategori);
    }

    @GetMapping
    @Operation(
            summary = "List all categories",
            description = "Fetch a paginated list of categories. Results can be sorted by name in either ascending or descending order."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of categories",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoriResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string")
                    )
            )
    })
    public List<CategoriResponse> getCategories(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1)int size,
            @RequestParam(defaultValue = "name") @NotBlank @Size(min = 1)String sort,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return iCategoriHandler.getCategories(page, size, sort, ascending);
    }

}
