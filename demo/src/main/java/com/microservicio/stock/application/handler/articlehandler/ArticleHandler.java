package com.microservicio.stock.application.handler.articlehandler;

import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.application.dto.articledto.ArticleResponse;
import com.microservicio.stock.application.dto.categorydto.CategoryRelationArticleResponse;
import com.microservicio.stock.application.mapper.articlemapper.IArticleRequestMapper;
import com.microservicio.stock.application.mapper.articlemapper.IArticleResponseMapper;
import com.microservicio.stock.domain.api.IArticleServicePort;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.pagination.PaginatedResult;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements IArticleHandler {

    private final IArticleServicePort iArticleServicePort;
    private final IArticlePersistencePort articlePersistencePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    @Override
    public ArticleResponse saveArticle(ArticleRequest articleRequest) {

        Article article = articleRequestMapper.articleRequestToArticle(articleRequest);

        Brand brand = brandPersistencePort.getBrandById(articleRequest.getBrandId());
        article.setBrand(brand);

        List<Category> categories = categoryPersistencePort.getCategoriesByIds(articleRequest.getCategoryIds());
        article.setCategories(categories);

        Article savedArticle = iArticleServicePort.saveArticle(article);

        return articleResponseMapper.toArticleResponseDto(savedArticle);
    }

    @Override
    public PaginatedResult<ArticleResponse> listArticles(int page, int size, String sortField, boolean ascending) {
        PaginatedResult<Article> paginatedArticles = articlePersistencePort.listArticles(page, size, sortField, ascending);

        List<ArticleResponse> articleResponses = paginatedArticles.getContent().stream()
                .map(article -> {
                    ArticleResponse articleResponse = articleResponseMapper.toArticleResponseDto(article);
                    List<CategoryRelationArticleResponse> sortedCategories = articleResponse.getCategories().stream()
                            .sorted(Comparator.comparing(CategoryRelationArticleResponse::getName))
                            .toList();
                    articleResponse.setCategories(sortedCategories);
                    return articleResponse;
                }).toList();

        articleResponses = articleResponses.stream()
                .sorted(Comparator.comparing((ArticleResponse articleResponse) -> articleResponse.getCategories().get(0).getName())
                        .thenComparing(articleResponse -> articleResponse.getCategories().size()))
                .toList();

        return new PaginatedResult<>(
                articleResponses,
                paginatedArticles.getPageNumber(),
                paginatedArticles.getPageSize(),
                paginatedArticles.getTotalElements()
        );

    }


}
