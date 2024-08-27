package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.api.ICategoryServicePort;
import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;


import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {


    private final ICategoryPersistencePort iCategoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort iCategoryPersistencePort) {
        this.iCategoryPersistencePort = iCategoryPersistencePort;
    }

    @Override
    public Category saveCategory(Category category) {

        if (iCategoryPersistencePort.existsByName(category.getName())) {
            throw new NameAlreadyExistsException("La categoria '" +category.getName()+"' ya existe");
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
    
}
