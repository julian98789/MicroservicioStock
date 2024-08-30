package com.microservicio.stock.domain.api;

import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;


public interface ICategoryServicePort {

    Category saveCategory(Category category);
    boolean existsByName(String name);
    PaginatedResult<Category> getCategories(int page, int size, String sort, boolean ascending);

}
