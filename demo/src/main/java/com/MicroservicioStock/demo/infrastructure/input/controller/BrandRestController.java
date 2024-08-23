package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.brandDto.BrandRequest;
import com.MicroservicioStock.demo.application.dto.brandDto.BrandResponse;
import com.MicroservicioStock.demo.application.handler.brandHandler.IBrandHandler;
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
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler iBrandHandler;

    @PostMapping
    @Operation(
            summary = "Save a brand",
            description = "Create a new brand and save it to the database. If a brand with the same name already exists, an error will be returned."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Brand created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string")
                    )
            ),
    })
    public ResponseEntity<BrandResponse> saveCategori(@Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse savedBrand = iBrandHandler.savedBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    @GetMapping
    @Operation(
            summary = "List all brands",
            description = "Fetch a paginated list of brands. Results can be sorted by name in either ascending or descending order."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of brand",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = BrandResponse.class)
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
    public List<BrandResponse> getBrands(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1)int size,
            @RequestParam(defaultValue = "name") @NotBlank @Size(min = 1)String sort,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return iBrandHandler.getBrands(page, size, sort, ascending);
    }
}
