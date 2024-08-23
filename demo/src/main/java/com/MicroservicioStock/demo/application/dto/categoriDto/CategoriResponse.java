package com.MicroservicioStock.demo.application.dto.categoriDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriResponse {
    Long id;
    private String name;
    private String description;
}
