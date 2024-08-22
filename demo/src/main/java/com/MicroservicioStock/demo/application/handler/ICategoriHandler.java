package com.MicroservicioStock.demo.application.handler;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;


import java.util.List;

public interface ICategoriHandler {
    CategoriResponse saveCategori(CategoriRequest categoriRequest);
    List<CategoriResponse> getCategories(int page, int size, String sort, boolean ascending);
}
