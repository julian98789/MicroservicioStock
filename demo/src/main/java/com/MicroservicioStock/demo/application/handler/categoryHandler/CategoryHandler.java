package com.MicroservicioStock.demo.application.handler.categoryHandler;

import com.MicroservicioStock.demo.application.dto.categoryDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoryDto.CategoryResponse;
import com.MicroservicioStock.demo.application.mapper.categoryMappper.ICategoryRequestMappper;
import com.MicroservicioStock.demo.application.mapper.categoryMappper.ICategoryResponseMapper;
import com.MicroservicioStock.demo.domain.api.ICategoryServicePort;
import com.MicroservicioStock.demo.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort iCategoryServicePort;
    private final ICategoryRequestMappper iCategoryRequestMappper;
    private final ICategoryResponseMapper iCategoryResponseMapper;

    @Override
    public CategoryResponse saveCategory(CategoriRequest categoriRequest) {
        String name = categoriRequest.getName();
        if (iCategoryServicePort.existsByName(name)) {
            throw new IllegalArgumentException("Categoría con nombre '" + name + "' ya existe.");
        }
        Category category = iCategoryRequestMappper.categoryRequestToCategory(categoriRequest);
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
