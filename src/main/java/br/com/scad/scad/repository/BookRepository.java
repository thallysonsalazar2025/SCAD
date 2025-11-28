package br.com.scad.scad.repository;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.Book;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthor(Author author);

    boolean existsByTitleAndDatePublisherAndAuthorId(@NotNull String title, @NotNull LocalDate datePublisher, @NotNull Long authorId);
}
