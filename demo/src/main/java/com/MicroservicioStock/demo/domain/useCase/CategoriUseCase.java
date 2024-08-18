package com.MicroservicioStock.demo.domain.useCase;

import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.exception.DescriptionCannotBeEmptyException;
import com.MicroservicioStock.demo.domain.exception.DescriptionTooLongException;
import com.MicroservicioStock.demo.domain.exception.NameCannotBeEmptyException;
import com.MicroservicioStock.demo.domain.exception.NameTooLongException;
import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;

public class CategoriUseCase implements ICategoriServicePort {

    private final ICategoriPersistencePort iCategoriPersistencePort;

    public CategoriUseCase(ICategoriPersistencePort iCategoriPersistencePort) {
        this.iCategoriPersistencePort = iCategoriPersistencePort;
    }

    @Override
    public Categori saveCategori(Categori categori) {
        if (categori.getName() == null || categori.getName().isEmpty()) {
            throw new NameCannotBeEmptyException("El nombre no puede estar vacio");
        }
        if (categori.getDescription() == null || categori.getDescription().isEmpty()) {
            throw new DescriptionCannotBeEmptyException("La descripcion no puede estar vacia");
        }
        if (categori.getName().length() > 50) {
            throw new NameTooLongException("El nombre no puede tener mas de 50 caracteres");
        }
        if (categori.getDescription().length() > 90) {
            throw new DescriptionTooLongException("La descripción no puede tener mas de 90 caracteres");
        }
        return iCategoriPersistencePort.saveCategori(categori);
    }
    
}
