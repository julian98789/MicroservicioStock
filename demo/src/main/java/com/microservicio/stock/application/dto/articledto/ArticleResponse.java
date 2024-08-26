package com.microservicio.stock.application.dto.articledto;

import com.microservicio.stock.application.dto.branddto.BrandResponse;
import com.microservicio.stock.application.dto.categorydto.CategoryResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ArticleResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private BrandResponse brand;
    private Set<CategoryResponse> categories;

}
