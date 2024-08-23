package com.MicroservicioStock.demo.domain.useCase;

import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;

import java.util.List;

public class CategoriUseCase implements ICategoriServicePort {

    private final ICategoriPersistencePort iCategoriPersistencePort;

    public CategoriUseCase(ICategoriPersistencePort iCategoriPersistencePort) {
        this.iCategoriPersistencePort = iCategoriPersistencePort;
    }

    @Override
    public Categori saveCategori(Categori categori) {
        if (iCategoriPersistencePort.existsByName(categori.getName())) {
            throw new CategoriAlreadyExistsException("La categoría ya existe");
        }
        return iCategoriPersistencePort.saveCategori(categori);
    }
    @Override
    public boolean existsByName(String name) {
        return iCategoriPersistencePort.existsByName(name);
    }

    @Override
    public List<Categori> getCategories(int page, int size, String sort, boolean ascending) {
        return iCategoriPersistencePort.getCategories(page, size, sort, ascending);
    }
    
}
