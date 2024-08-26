package com.microservicio.stock.infrastructure.input.controller;

import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.application.dto.articledto.ArticleResponse;
import com.microservicio.stock.application.handler.articlehandler.IArticleHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestController {

    private final IArticleHandler articleHandler;


    @PostMapping
    public ResponseEntity<ArticleResponse> saveArticle(@Valid  @RequestBody ArticleRequest articleRequest) {
        // Convierte el DTO de solicitud a un modelo de dominio
        ArticleResponse saveArticle = articleHandler.saveArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }
}
