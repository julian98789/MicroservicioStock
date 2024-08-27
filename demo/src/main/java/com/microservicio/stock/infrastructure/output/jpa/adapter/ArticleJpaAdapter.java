package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.domain.model.Article;
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
    public List<Article> getArticles(int page, int size, String sort, boolean ascending) {
        Page<ArticleEntity> articleEntities = articleRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));

        return articleEntities.stream()
                .map(articleEntityMapper::toModel).toList();


    }

    @Override
    public boolean existsByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }
}
