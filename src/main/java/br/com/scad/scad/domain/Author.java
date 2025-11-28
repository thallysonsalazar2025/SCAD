package br.com.scad.scad.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NamedEntityGraph(
    name = "Author.withBooks",
    attributeNodes = @NamedAttributeNode("books")
)
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateBirth;
    private String nacionalityCountry;

    private LocalDate dateSaved;
    private LocalDate dateUpdated;
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id_user;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Book> books;

    public Author(String name, LocalDate dateBirth, String nacionalityCountry) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.nacionalityCountry = nacionalityCountry;
    }


    public Author(List<Book> books, LocalDate dateBirth, String name, String nacionalityCountry) {
        this.books = books;
        this.dateBirth = dateBirth;
        this.name = name;
        this.nacionalityCountry = nacionalityCountry;
    }

    public Author() {

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

    public UUID getId_user() {
        return id_user;
    }

    public void setId_user(UUID id_user) {
        this.id_user = id_user;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LocalDate getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(LocalDate dateSaved) {
        this.dateSaved = dateSaved;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }




}
