package com.MicroservicioStock.demo.domain.api;

import com.MicroservicioStock.demo.domain.model.Category;

import java.util.List;


public interface ICategoryServicePort {

    Category saveCategory(Category category);
    boolean existsByName(String name);
    List<Category> getCategories(int page, int size, String sort, boolean ascending);

}
