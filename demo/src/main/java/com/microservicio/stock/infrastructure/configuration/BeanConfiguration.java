package com.microservicio.stock.infrastructure.configuration;

import com.microservicio.stock.domain.api.IArticleServicePort;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;
import com.microservicio.stock.domain.usecase.ArticleUseCase;
import com.microservicio.stock.infrastructure.output.jpa.adapter.ArticleJpaAdapter;
import com.microservicio.stock.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.microservicio.stock.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.mapper.IArticleEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.mapper.IBrandEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.IArticleRepository;
import com.microservicio.stock.infrastructure.output.jpa.repository.IBrandRepository;
import com.microservicio.stock.infrastructure.output.jpa.repository.ICategoryRepository;
import com.microservicio.stock.domain.api.IBrandServicePort;
import com.microservicio.stock.domain.api.ICategoryServicePort;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;
import com.microservicio.stock.domain.usecase.BrandUseCase;
import com.microservicio.stock.domain.usecase.CategoryUseCase;
import com.microservicio.stock.infrastructure.output.jpa.adapter.BrandJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper iCategoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper iBrandEntityMapper;
    private final IArticleRepository iArticleRepository;
    private final IArticleEntityMapper articleEntityMapper;


    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, iCategoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
            return new BrandJpaAdapter(brandRepository,iBrandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort(IBrandPersistencePort brandPersistencePort) {
            return new BrandUseCase(brandPersistencePort);
    }

    @Bean
    public IArticlePersistencePort iArticlePersistencePort() {
        return new ArticleJpaAdapter (articleEntityMapper,iArticleRepository);
    }

    @Bean
    public IArticleServicePort iArticleServicePort(IArticlePersistencePort iArticlePersistencePort) {
        return new ArticleUseCase(iArticlePersistencePort);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}
