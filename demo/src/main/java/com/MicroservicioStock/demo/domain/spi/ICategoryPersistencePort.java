package com.MicroservicioStock.demo.domain.spi;

import com.MicroservicioStock.demo.domain.model.Category;
import java.util.List;

public interface ICategoryPersistencePort {

    boolean existsByName(String name);
    Category saveCategory(Category category);
    List<Category> getCategories(int page, int size, String sort, boolean ascending);
}
