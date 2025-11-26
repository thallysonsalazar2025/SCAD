package br.com.scad.scad.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@EntityListeners(AuditingEntityListener.class) // Habilita a auditoria do Spring Data JPA
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

    @CreatedDate // Preenchido automaticamente na criação
    private LocalDateTime dateSaved;

    @LastModifiedDate // Preenchido automaticamente na atualização
    private LocalDateTime dateUpdated;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id") // Define explicitamente o nome da coluna de chave estrangeira
    private Author author;



    public Book() {
    }

    public Book(String title, String isbn, PersonGenereEnum genre, LocalDate datePublisher, Double price, LocalDateTime dateSaved, LocalDateTime dateUpdated, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.datePublisher = datePublisher;
        this.price = price;
        this.dateSaved = dateSaved;
        this.dateUpdated = dateUpdated;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public PersonGenereEnum getGenre() {
        return genre;
    }

    public void setGenre(PersonGenereEnum genre) {
        this.genre = genre;
    }

    public LocalDate getDatePublisher() {
        return datePublisher;
    }

    public void setDatePublisher(LocalDate datePublisher) {
        this.datePublisher = datePublisher;
    }

    public LocalDateTime getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(LocalDateTime dateSaved) {
        this.dateSaved = dateSaved;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
