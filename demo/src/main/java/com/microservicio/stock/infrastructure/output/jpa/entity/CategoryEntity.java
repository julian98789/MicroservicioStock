package com.microservicio.stock.infrastructure.output.jpa.entity;

import com.microservicio.stock.domain.model.Article;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name",  length = 50, unique = true)
    private String name;

    @NotBlank
    @Size(max = 90)
    @Column(name = "description", length = 90)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<ArticleEntity> articles;
}
