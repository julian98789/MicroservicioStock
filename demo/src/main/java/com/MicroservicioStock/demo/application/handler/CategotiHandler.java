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

@Service
@RequiredArgsConstructor
@Transactional
public class CategotiHandler implements ICategoriHandler{

    private final ICategoriServicePort iCategoriServicePort;
    private final CategoriRequestMappper categoriRequestMappper;


    @Override
    public void saveCategori(CategoriRequest categoriRequest) {
        Categori categori = categoriRequestMappper.categoriRequestTocategori(categoriRequest);
        iCategoriServicePort.saveCategori(categori);
    }
}
