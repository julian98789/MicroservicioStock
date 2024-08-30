package com.microservicio.stock.application.handler.categoryhandler;

import com.microservicio.stock.application.dto.categorydto.CategoriRequest;
import com.microservicio.stock.application.dto.categorydto.CategoryResponse;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;


public interface ICategoryHandler {
    CategoryResponse saveCategory(CategoriRequest categoriRequest);
    PaginatedResult<CategoryResponse> getCategories(int page, int size, String sort, boolean ascending);
}
