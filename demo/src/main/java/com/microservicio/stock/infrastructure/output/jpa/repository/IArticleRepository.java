package com.microservicio.stock.infrastructure.output.jpa.repository;

import com.microservicio.stock.infrastructure.output.jpa.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity,Long> {
    Optional<ArticleEntity> findByName(String aString);
    Page<ArticleEntity> findAll(Pageable pageable);

}
