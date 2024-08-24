package com.microservicio.stock.application.dto.categorydto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    Long id;
    private String name;
    private String description;
}
