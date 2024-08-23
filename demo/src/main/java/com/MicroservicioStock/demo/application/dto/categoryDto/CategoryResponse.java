package com.MicroservicioStock.demo.application.dto.categoryDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    Long id;
    private String name;
    private String description;
}
