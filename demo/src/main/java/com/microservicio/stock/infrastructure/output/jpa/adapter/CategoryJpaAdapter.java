package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.infrastructure.output.jpa.entity.CategoryEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.ICategoryRepository;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository iCategoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;


    @Override
    public Category saveCategory(Category category) {
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        CategoryEntity savedEntity = iCategoryRepository.save(categoryEntity);
        return categoryEntityMapper.toCategory(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return iCategoryRepository.findByName(name).isPresent();
    }

    @Override
    public List<Category> getCategories(int page, int size, String sort, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<CategoryEntity> categoryEntities = iCategoryRepository.findAll(pageRequest);

        return categoryEntities.stream()
                .map(categoryEntityMapper::toCategory).toList();

    }
}
