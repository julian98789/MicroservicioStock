package com.MicroservicioStock.demo.application.handler.categoriHandler;

import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriResponse;


import java.util.List;

public interface ICategoriHandler {
    CategoriResponse saveCategori(CategoriRequest categoriRequest);
    List<CategoriResponse> getCategories(int page, int size, String sort, boolean ascending);
}
