package com.MicroservicioStock.demo.domain.spi;

import com.MicroservicioStock.demo.domain.model.Categori;
import java.util.List;

public interface ICategoriPersistencePort {

    boolean existsByName(String name);
    Categori saveCategori(Categori categori);
    List<Categori> getCategories(int page, int size, String sort, boolean ascending);
}
