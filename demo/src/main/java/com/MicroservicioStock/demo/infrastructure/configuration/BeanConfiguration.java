package com.MicroservicioStock.demo.infrastructure.configuration;

import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;
import com.MicroservicioStock.demo.domain.useCase.CategoriUseCase;
import com.MicroservicioStock.demo.infrastructure.output.jpa.adapter.CategoriaJpaAdapter;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoriEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoriRepository categoriRepository;
    private final CategoriEntityMapper categoriEntityMapper;

    @Bean
    public ICategoriPersistencePort categoriPersistencePort(){
        return new CategoriaJpaAdapter(categoriRepository, categoriEntityMapper);
    }

    @Bean
    public ICategoriServicePort categoriServicePort(){
        return new CategoriUseCase(categoriPersistencePort());
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}
