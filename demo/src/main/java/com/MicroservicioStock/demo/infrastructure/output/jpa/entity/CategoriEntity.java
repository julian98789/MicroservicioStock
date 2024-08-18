package com.MicroservicioStock.demo.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categori")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name",  length = 50)
    private String name;

    @NotBlank
    @Size(max = 90)
    @Column(name = "description", length = 90)
    private String description;
}
