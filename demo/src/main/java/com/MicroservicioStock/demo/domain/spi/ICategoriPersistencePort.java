package com.MicroservicioStock.demo.domain.spi;

import com.MicroservicioStock.demo.domain.model.Categori;

public interface ICategoriPersistencePort {

    Categori saveCategori(Categori categori);
}
