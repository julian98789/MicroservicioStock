package com.MicroservicioStock.demo.application.dto.brandDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {
    Long id;
    private String name;
    private String description;
}
