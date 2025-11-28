package br.com.scad.scad.domain;

/**
 * Enum que representa o gênero literário de um livro.
 * Os valores aqui devem corresponder exatamente aos definidos no contrato OpenAPI (scad.yaml).
 */
public enum GenreEnum {
    ROMANCE,
    FICCAO_CIENTIFICA,
    FANTASIA,
    TERROR,
    AVENTURA,
    BIOGRAFIA,
    HISTORIA,
    ACTION,
    SUSPENSE;
}
