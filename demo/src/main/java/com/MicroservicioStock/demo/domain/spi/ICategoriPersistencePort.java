package com.MicroservicioStock.demo.domain.spi;

import com.MicroservicioStock.demo.domain.model.Categori;

import java.util.Optional;

public interface ICategoriPersistencePort {

    boolean existsByName(String name);
    Categori saveCategori(Categori categori);
}
