package com.MicroservicioStock.demo.domain.api;

import com.MicroservicioStock.demo.domain.model.Categori;

import java.util.Optional;

public interface ICategoriServicePort {

    Categori saveCategori(Categori categori);
    boolean existsByName(String name);

}
