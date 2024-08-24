package com.microservicio.stock.domain.api;

import com.microservicio.stock.domain.model.Category;

import java.util.List;


public interface ICategoryServicePort {

    Category saveCategory(Category category);
    boolean existsByName(String name);
    List<Category> getCategories(int page, int size, String sort, boolean ascending);

}
