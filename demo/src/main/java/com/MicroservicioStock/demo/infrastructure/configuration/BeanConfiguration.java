package com.MicroservicioStock.demo.infrastructure.configuration;

import com.MicroservicioStock.demo.domain.api.IBrandServicePort;
import com.MicroservicioStock.demo.domain.api.ICategoryServicePort;
import com.MicroservicioStock.demo.domain.spi.IBrandPersistencePort;
import com.MicroservicioStock.demo.domain.spi.ICategoryPersistencePort;
import com.MicroservicioStock.demo.domain.useCase.BrandUseCase;
import com.MicroservicioStock.demo.domain.useCase.CategoryUseCase;
import com.MicroservicioStock.demo.infrastructure.output.jpa.adapter.BrandJpaAdapter;
import com.MicroservicioStock.demo.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.IBrandEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.IBrandRepository;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper iBrandEntityMapper;


    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
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
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}
