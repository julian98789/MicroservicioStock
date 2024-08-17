package com.MicroservicioStock.demo.application.handler;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.domain.model.Categori;

public interface ICategoriHandler {
    void saveCategori(CategoriRequest categoriRequest);
}
