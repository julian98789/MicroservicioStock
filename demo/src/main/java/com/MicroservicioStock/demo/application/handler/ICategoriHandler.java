package com.MicroservicioStock.demo.application.handler;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.domain.model.Categori;

public interface ICategoriHandler {
    CategoriResponse saveCategori(CategoriRequest categoriRequest);
}
