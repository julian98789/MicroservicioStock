package com.microservicio.stock.domain.spi;

import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;

import java.util.List;

public interface ICategoryPersistencePort {

    boolean existsByName(String name);
    Category saveCategory(Category category);
    PaginatedResult<Category> getCategories(int page, int size, String sort, boolean ascending);
    List<Category> getCategoriesByIds(List<Long> ids);
}
