package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.pagination.PaginatedResult;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;
import com.microservicio.stock.infrastructure.output.jpa.entity.ArticleEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.IArticleEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.IArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final IArticleEntityMapper articleEntityMapper;
    private final IArticleRepository articleRepository;

    @Override
    public Article saveArticle(Article article) {
        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);

        articleEntity = articleRepository.save(articleEntity);

        return articleEntityMapper.toModel(articleEntity);
    }

    @Override
    public PaginatedResult<Article> listArticles(int page, int size, String sortBy, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ArticleEntity> articleEntities = articleRepository.findAll(pageRequest);

        List<Article> articles = articleEntities.stream()
                .map(articleEntityMapper::toModel).toList();


        return new PaginatedResult<>(
                articles,
                articleEntities.getNumber(),
                articleEntities.getSize(),
                articleEntities.getTotalElements()
        );
    }


    @Override
    public boolean existsByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }
}
