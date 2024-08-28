package com.microservicio.stock.infrastructure.input.controller;

import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.application.dto.articledto.ArticleResponse;
import com.microservicio.stock.application.handler.articlehandler.IArticleHandler;
import com.microservicio.stock.domain.pagination.PaginatedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestController {

    private final IArticleHandler articleHandler;


    @PostMapping
    @Operation(
            summary = "Save a new article",
            description = "Create a new article and save it to the database. If an article with the same name already exists, an error will be returned."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
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
    public ResponseEntity<ArticleResponse> saveArticle(@Valid  @RequestBody ArticleRequest articleRequest) {
        ArticleResponse saveArticle = articleHandler.saveArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }

    @GetMapping
    @Operation(
            summary = "List articles",
            description = "Retrieve a paginated list of articles. The articles can be sorted by different fields such as name, brand name, or category name, and can be ordered in ascending or descending order."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of article",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResult.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data. This may occur if the sorting field is invalid or the page size is not positive.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string")
                    )
            ),
    })
    public ResponseEntity<PaginatedResult<ArticleResponse>> listArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "true") boolean ascending) {

        PaginatedResult<ArticleResponse> paginatedResult = articleHandler.listArticles(page, size, sort, ascending);

        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
