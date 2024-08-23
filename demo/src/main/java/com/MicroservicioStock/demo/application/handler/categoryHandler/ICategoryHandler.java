package com.MicroservicioStock.demo.application.handler.categoryHandler;

import com.MicroservicioStock.demo.application.dto.categoryDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoryDto.CategoryResponse;


import java.util.List;

public interface ICategoryHandler {
    CategoryResponse saveCategory(CategoriRequest categoriRequest);
    List<CategoryResponse> getCategories(int page, int size, String sort, boolean ascending);
}
