package br.com.scad.scad.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Author {

    public static final String CAMPO_OBRIGATÓRIO = "Field is required. ";
    @Id
    // Recomenda-se usar IDENTITY para MySQL para melhor performance.
    // O banco de dados gerenciará o auto-incremento.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255, min = 3, message = "Name must be between 3 and 255 characters")
    @NotBlank(message = CAMPO_OBRIGATÓRIO + "Name")
    private String name;

    @Past(message = "Date Birth must be in the past")
    @NotNull(message = CAMPO_OBRIGATÓRIO + "Date Birth")
    private LocalDate dateBirth;

    @NotBlank(message = CAMPO_OBRIGATÓRIO + "Nacionality Country")
    private String nacionalityCountry;

    @CreatedDate
    private LocalDateTime dateSaved;

    @LastModifiedDate
    private LocalDateTime dateUpdated;

    private String idUser;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;


    public Author(Long id, List<Book> books, String idUser, LocalDateTime dateUpdated,
                  LocalDateTime dateSaved, String nacionalityCountry, LocalDate dateBirth, String name) {
        this.id = id;
        this.books = books;
        this.idUser = idUser;
        this.dateUpdated = dateUpdated;
        this.dateSaved = dateSaved;
        this.nacionalityCountry = nacionalityCountry;
        this.dateBirth = dateBirth;
        this.name = name;
    }

    public Author() {
    }

    /**
     * Este método é chamado automaticamente pelo JPA antes de um novo autor ser salvo.
     * Ele gera um ID customizado para o campo idUser se ele ainda não existir.
     */
    @PrePersist
    public void generateCustomId() {
        if (this.idUser == null) {
            String part1 = RandomStringUtils.randomNumeric(4);
            String part2 = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
            String part3 = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
            String part4 = RandomStringUtils.randomNumeric(4);
            this.idUser = String.join("-", part1, part2, part3, part4);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNacionalityCountry() {
        return nacionalityCountry;
    }

    public void setNacionalityCountry(String nacionalityCountry) {
        this.nacionalityCountry = nacionalityCountry;
    }

    public LocalDateTime getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(LocalDateTime dateSaved) {
        this.dateSaved = dateSaved;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
