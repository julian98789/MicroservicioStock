package com.microservicio.stock.domain.spi;

import com.microservicio.stock.domain.model.Category;
import java.util.List;

public interface ICategoryPersistencePort {

    boolean existsByName(String name);
    Category saveCategory(Category category);
    List<Category> getCategories(int page, int size, String sort, boolean ascending);
    List<Category> getCategoriesByIds(List<Long> ids);
}
