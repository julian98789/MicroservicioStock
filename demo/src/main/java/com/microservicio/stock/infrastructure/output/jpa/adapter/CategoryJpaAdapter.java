package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.domain.util.pagination.PaginatedResult;
import com.microservicio.stock.infrastructure.output.jpa.entity.CategoryEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
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
    private final ICategoryEntityMapper iCategoryEntityMapper;


    @Override
    public Category saveCategory(Category category) {
        CategoryEntity categoryEntity = iCategoryEntityMapper.toEntity(category);
        CategoryEntity savedEntity = iCategoryRepository.save(categoryEntity);
        return iCategoryEntityMapper.toCategory(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return iCategoryRepository.findByName(name).isPresent();
    }

    @Override
    public PaginatedResult<Category> getCategories(int page, int size, String sort, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<CategoryEntity> categoryEntities = iCategoryRepository.findAll(pageRequest);

        List<Category> categories = categoryEntities.stream()
                .map(iCategoryEntityMapper::toCategory)
                .toList();

        return new PaginatedResult<>(
                categories,
                categoryEntities.getNumber(),
                categoryEntities.getSize(),
                categoryEntities.getTotalElements()
        );
    }

    @Override
    public List<Category> getCategoriesByIds(List<Long> ids) {
        List<CategoryEntity> categoryEntities = iCategoryRepository.findAllById(ids);

        return categoryEntities.stream()
                .map(iCategoryEntityMapper::toCategory).toList();


    }
}
