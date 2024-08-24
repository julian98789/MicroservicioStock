package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.api.ICategoryServicePort;
import com.microservicio.stock.domain.exception.custom.CategoryAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.InvalidBrandException;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 90;

    private final ICategoryPersistencePort iCategoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort iCategoryPersistencePort) {
        this.iCategoryPersistencePort = iCategoryPersistencePort;
    }

    @Override
    public Category saveCategory(Category category) {
        validateCategory( category);

        if (iCategoryPersistencePort.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException("La categoría ya existe");
        }
        return iCategoryPersistencePort.saveCategory(category);
    }
    @Override
    public boolean existsByName(String name) {
        return iCategoryPersistencePort.existsByName(name);
    }

    @Override
    public List<Category> getCategories(int page, int size, String sort, boolean ascending) {
        return iCategoryPersistencePort.getCategories(page, size, sort, ascending);
    }

    public void validateCategory(Category category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new InvalidBrandException("The name cannot be empty");
        }
        if (category.getName().length() > MAX_NAME_LENGTH) {
            throw new InvalidBrandException("The name must not exceed 50 characters");
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new InvalidBrandException("The description cannot be empty");
        }
        if (category.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidBrandException("The description must not exceed 120 characters");
        }
    }
    
}
