package com.MicroservicioStock.demo.domain.model;



public class Categori {
    private Long id;
    private String name;
    private String description;

    public Categori(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.length() > 50) {
            throw new IllegalArgumentException("El nombre no puede ser nulo y debe tener un máximo de 50 caracteres.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.length() > 90) {
            throw new IllegalArgumentException("La descripción no puede ser nula y debe tener un máximo de 90 caracteres.");
        }
        this.description = description;
    }
}
