package com.microservicio.stock.application.dto.categorydto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriRequest {
    @NotBlank
    @Size(min = 1, max = 50, message = "el tamaño debe estar entre 1 y 50 caracteres")
    private String name;

    @NotBlank
    @Size(min = 1, max = 90, message = "el tamaño debe estar entre 1 y 90 caracteres")
    private String description;
}
