package com.microservicio.stock.application.handler.categoryhandler;

import com.microservicio.stock.application.dto.categorydto.CategoriRequest;
import com.microservicio.stock.application.dto.categorydto.CategoryResponse;
import com.microservicio.stock.application.mapper.categorymapper.ICategoryRequestMapper;
import com.microservicio.stock.application.mapper.categorymapper.ICategoryResponseMapper;
import com.microservicio.stock.domain.api.ICategoryServicePort;
import com.microservicio.stock.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort iCategoryServicePort;
    private final ICategoryRequestMapper iCategoryRequestMapper;
    private final ICategoryResponseMapper iCategoryResponseMapper;

    @Override
    public CategoryResponse saveCategory(CategoriRequest categoriRequest) {
        String name = categoriRequest.getName();
        if (iCategoryServicePort.existsByName(name)) {
            throw new IllegalArgumentException("Categoría con nombre '" + name + "' ya existe.");
        }
        Category category = iCategoryRequestMapper.categoryRequestToCategory(categoriRequest);
        Category savedCategory = iCategoryServicePort.saveCategory(category);
        return iCategoryResponseMapper.categoryResponseToResponse(savedCategory);
    }


    @Override
    public List<CategoryResponse> getCategories(int page, int size, String sort, boolean ascending) {
        List<Category> categories = iCategoryServicePort.getCategories(page, size, sort, ascending);
        return categories.stream()
                .map(iCategoryResponseMapper::categoryResponseToResponse).toList();

    }
}
