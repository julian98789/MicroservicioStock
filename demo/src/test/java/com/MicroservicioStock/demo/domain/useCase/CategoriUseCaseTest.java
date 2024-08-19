package com.MicroservicioStock.demo.domain.useCase;


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

}
