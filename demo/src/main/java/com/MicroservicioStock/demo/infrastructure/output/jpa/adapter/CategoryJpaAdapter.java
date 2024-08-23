package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Category;
import com.MicroservicioStock.demo.domain.spi.ICategoryPersistencePort;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoryEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository iCategoryRepository;
    private final CategoryEntityMapper CategoryEntityMapper;


    @Override
    public Category saveCategory(Category category) {
        CategoryEntity categoryEntity = CategoryEntityMapper.toEntity(category);
        CategoryEntity savedEntity = iCategoryRepository.save(categoryEntity);
        return CategoryEntityMapper.toCategory(savedEntity);
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
                .map(CategoryEntityMapper::toCategory).toList();

    }
}
