package com.microservicio.stock.domain.spi;

import com.microservicio.stock.domain.model.Category;
import java.util.List;
import java.util.Set;

public interface ICategoryPersistencePort {

    boolean existsByName(String name);
    Category saveCategory(Category category);
    List<Category> getCategories(int page, int size, String sort, boolean ascending);
    Set<Category> getCategoriesByIds(Set<Long> ids);
}
