package br.com.scad.scad.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    @Enumerated(EnumType.STRING)
    private PersonGenereEnum genre;
    private LocalDate datePublisher;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Book(Double price, LocalDate datePublisher, PersonGenereEnum genre, String isbn, String title, Author author) {
        this.price = price;
        this.datePublisher = datePublisher;
        this.genre = genre;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDatePublisher() {
        return datePublisher;
    }

    public void setDatePublisher(LocalDate datePublisher) {
        this.datePublisher = datePublisher;
    }

    public PersonGenereEnum getGenre() {
        return genre;
    }

    public void setGenre(PersonGenereEnum genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
