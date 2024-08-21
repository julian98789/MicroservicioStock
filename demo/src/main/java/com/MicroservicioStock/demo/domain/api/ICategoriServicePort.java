package com.MicroservicioStock.demo.domain.api;

import com.MicroservicioStock.demo.domain.model.Categori;

import java.util.List;
import java.util.Optional;

public interface ICategoriServicePort {

    Categori saveCategori(Categori categori);
    boolean existsByName(String name);
    List<Categori> getCategories(int page, int size, String sort, boolean ascending);

}
