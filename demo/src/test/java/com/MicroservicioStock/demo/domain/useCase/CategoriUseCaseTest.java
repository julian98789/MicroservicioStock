package com.MicroservicioStock.demo.domain.useCase;

import com.MicroservicioStock.demo.domain.exception.DescriptionCannotBeEmptyException;
import com.MicroservicioStock.demo.domain.exception.DescriptionTooLongException;
import com.MicroservicioStock.demo.domain.exception.NameCannotBeEmptyException;
import com.MicroservicioStock.demo.domain.exception.NameTooLongException;
import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CategoriUseCaseTest {

    @Mock
    private ICategoriPersistencePort iCategoriPersistencePort;

    @InjectMocks
    private CategoriUseCase categoriUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe llamar al puerto de persistencia cuando la categoria se valida")
    void saveCategori_ShouldCallPersistencePort_WhenCategoriIsValid() {
        // Dado
        Long id = 1L;
        String name = "Electronics";
        String description = "Devices and gadgets";
        Categori categori = new Categori(id, name, description);

        // Cuando
        categoriUseCase.saveCategori(categori);

        // Entonces
        verify(iCategoriPersistencePort, times(1)).saveCategori(categori);
    }


    @Test
    @DisplayName("Debe lanzar excepcion cuando el nombre este vacio")
    void saveCategori_ShouldThrowException_WhenNameIsEmpty() {
        // Dado
        Categori categori = new Categori(1L, "", "Valid description");

        // Entonces
        assertThrows(NameCannotBeEmptyException.class, () -> categoriUseCase.saveCategori(categori));
    }


    @Test
    @DisplayName("Debe lanzar excepcion cuando la descripcion  este vacio")
    void saveCategori_ShouldThrowException_WhenDescriptionIsEmpty() {
        // Dado
        Categori categori = new Categori(1L, "Valid name", "");

        // Entonces
        assertThrows(DescriptionCannotBeEmptyException.class, () -> categoriUseCase.saveCategori(categori));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el nombre tenga más de 50 caracteres")
    void saveCategori_ShouldThrowException_WhenNameHasMoreThan50Characters() {
        // Dado
        Categori categori = new Categori(1L, "A".repeat(51), "Valid description");

        // Entonces
        assertThrows(NameTooLongException.class, () -> categoriUseCase.saveCategori(categori));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la descripción tenga más de 90 caracteres")
    void saveCategori_ShouldThrowException_WhenDescriptionHasMoreThan90Characters() {
        // Dado
        Categori categori = new Categori(1L, "Valid name", "A".repeat(91));

        // Entonces
        assertThrows(DescriptionTooLongException.class, () -> categoriUseCase.saveCategori(categori));
    }
}
