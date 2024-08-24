package com.microservicio.stock.application.handler.categoryhandler;

import com.microservicio.stock.application.dto.categorydto.CategoriRequest;
import com.microservicio.stock.application.dto.categorydto.CategoryResponse;


import java.util.List;

public interface ICategoryHandler {
    CategoryResponse saveCategory(CategoriRequest categoriRequest);
    List<CategoryResponse> getCategories(int page, int size, String sort, boolean ascending);
}
