package br.com.scad.scad.repository;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.Book;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthor(Author author);

    boolean existsByTitleAndDatePublisherAndAuthorId(@NotNull String title, @NotNull LocalDate datePublisher, @NotNull Long authorId);

    Page<Book> findAll(Specification<Book> specs, Pageable pageable);
}
