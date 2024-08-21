package com.MicroservicioStock.demo.application.handler;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.application.mapper.CategoriRequestMappper;
import com.MicroservicioStock.demo.application.mapper.CategoriResponseMapper;
import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.model.Categori;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriHandler implements ICategoriHandler{
    private final ICategoriServicePort iCategoriServicePort;
    private final CategoriRequestMappper categoriRequestMappper;
    private final CategoriResponseMapper categoriResponseMapper;

    @Override
    public CategoriResponse saveCategori(CategoriRequest categoriRequest) {
        String name = categoriRequest.getName();
        if (iCategoriServicePort.existsByName(name)) {
            throw new IllegalArgumentException("Categoría con nombre '" + name + "' ya existe.");
        }
        Categori categori = categoriRequestMappper.categoriRequestTocategori(categoriRequest);
        Categori savedCategori = iCategoriServicePort.saveCategori(categori);
        return categoriResponseMapper.categoriResponseToresponse(savedCategori);
    }


    @Override
    public List<CategoriResponse> getCategories(int page, int size, String sort, boolean ascending) {
        List<Categori> categories = iCategoriServicePort.getCategories(page, size, sort, ascending);
        return categories.stream()
                .map(categoriResponseMapper::categoriResponseToresponse)
                .collect(Collectors.toList());
    }
}
