package com.MicroservicioStock.demo.domain.useCase;

import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;

public class CategoriUseCase implements ICategoriServicePort {

    private final ICategoriPersistencePort iCategoriPersistencePort;

    public CategoriUseCase(ICategoriPersistencePort iCategoriPersistencePort) {
        this.iCategoriPersistencePort = iCategoriPersistencePort;
    }

    @Override
    public Categori saveCategori(Categori categori) {
        return iCategoriPersistencePort.saveCategori(categori);
    }
    
}
