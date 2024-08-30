package com.microservicio.stock.domain.util;

public class Util {

    public static final String NAME_REQUIRED = "El nombre es obligatorio.";
    public static final String NAME_SIZE = "El tamaño debe estar entre 1 y 50 caracteres.";
    public static final String DESCRIPTION_REQUIRED = "La descripción es obligatoria.";
    public static final String DESCRIPTION_SIZE = "El tamaño debe estar entre 1 y 120 caracteres.";
    public static final String ARTICLE_QUANTITY_MIN = "La cantidad mínima es 1.";
    public static final String ARTICLE_PRICE_REQUIRED = "El precio es obligatorio.";
    public static final String ARTICLE_PRICE_MIN = "El precio debe ser mayor que 0.";
    public static final String ARTICLE_BRAND_ID_REQUIRED = "El id del brand es obligatorio.";
    public static final String ARTICLE_CATEGORIES_UNIQUE = "No se pueden categorías repetidas.";
    public static final String ARTICLE_CATEGORIES_REQUIRED = "Debe proporcionar al menos una categoría.";
    public static final String ARTICLE_CATEGORIES_SIZE = "El artículo debe tener entre 1 y 3 categorías.";

    public static final int ARTICLE_QUANTITY_MIN_VALUE = 1;
    public static final String ARTICLE_PRICE_MIN_VALUE = "0.0";
    public static final int ARTICLE_CATEGORIES_MIN_VALUE = 1;
    public static final int ARTICLE_CATEGORIES_MAX_VALUE = 3;
    public static final int DESCRIPTION_MAX_VALUE = 120;
    public static final int DESCRIPTION_MIN_VALUE = 1;
    public static final int NAME_MAX_VALUE = 50;
    public static final int NAME_MIN_VALUE = 1;
    public static final int DESCRIPTION_CATEGORY_MAX_VALUE = 90;
    public static final String DESCRIPTION_CATEGORY_SIZE = "El tamaño debe estar entre 1 y 90 caracteres.";
    public static final String ARTICLE_NAME_ALREADY_EXISTS = "El articulo ya existe";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "La categoria ya existe";
    public static final String BRAND_NAME_ALREADY_EXISTS = "La marca ya existe";
    private Util() {}
}
