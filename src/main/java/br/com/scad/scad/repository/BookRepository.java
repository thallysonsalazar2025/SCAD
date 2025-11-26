package br.com.scad.scad.repository;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthor(Author author);
}
