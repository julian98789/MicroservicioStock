package com.MicroservicioStock.demo.application.handler.categoriHandler;

import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriResponse;
import com.MicroservicioStock.demo.application.mapper.categoriMappper.ICategoriRequestMappper;
import com.MicroservicioStock.demo.application.mapper.categoriMappper.ICategoriResponseMapper;
import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.model.Categori;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriHandler implements ICategoriHandler {
    private final ICategoriServicePort iCategoriServicePort;
    private final ICategoriRequestMappper ICategoriRequestMappper;
    private final ICategoriResponseMapper ICategoriResponseMapper;

    @Override
    public CategoriResponse saveCategori(CategoriRequest categoriRequest) {
        String name = categoriRequest.getName();
        if (iCategoriServicePort.existsByName(name)) {
            throw new IllegalArgumentException("Categoría con nombre '" + name + "' ya existe.");
        }
        Categori categori = ICategoriRequestMappper.categoriRequestTocategori(categoriRequest);
        Categori savedCategori = iCategoriServicePort.saveCategori(categori);
        return ICategoriResponseMapper.categoriResponseToresponse(savedCategori);
    }


    @Override
    public List<CategoriResponse> getCategories(int page, int size, String sort, boolean ascending) {
        List<Categori> categories = iCategoriServicePort.getCategories(page, size, sort, ascending);
        return categories.stream()
                .map(ICategoriResponseMapper::categoriResponseToresponse).toList();

    }
}
