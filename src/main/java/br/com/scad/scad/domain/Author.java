package br.com.scad.scad.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateBirth;
    private String nacionalityCountry;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author(String name, LocalDate dateBirth, String nacionalityCountry) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.nacionalityCountry = nacionalityCountry;
    }

    public Author() {
    }

    public Author(List<Book> books, LocalDate dateBirth, String name, String nacionalityCountry) {
        this.books = books;
        this.dateBirth = dateBirth;
        this.name = name;
        this.nacionalityCountry = nacionalityCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getNacionalityCountry() {
        return nacionalityCountry;
    }

    public void setNacionalityCountry(String nacionalityCountry) {
        this.nacionalityCountry = nacionalityCountry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
