package com.microservicio.stock.application.dto.articledto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ArticleRequest {
    @NotBlank(message = "El nombre del artículo es obligatorio.")
    @Size(min = 1, max = 50, message = "El nombre del artículo no puede tener más de 50 caracteres y minimo 1.")
    private String name;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 1, max = 120, message = "La descripción no puede tener más de 120 caracteres y minimo 1.")
    private String description;

    @Min(value = 1, message = "La cantidad minima es 1.")
    private int quantity;

    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0.")
    private BigDecimal price;


    private Long brandId;

    @NotEmpty(message = "Debe proporcionar al menos una categoría.")
    @Size(min = 1,max = 3, message = "El artículo debe tener entre 1 y 3 categorías")
    private List<Long> categoryIds;
}
