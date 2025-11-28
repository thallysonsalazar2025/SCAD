package br.com.scad.scad.repository;

import br.com.scad.scad.domain.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByNameAndDateBirthAndNacionalityCountry(String name, LocalDate dateBirth, String nacionalityCountry);

    /**
     * Sobrescreve o método findById padrão do JpaRepository.
     * Ao usar a anotação @EntityGraph, instruímos o Hibernate a buscar
     * a coleção 'books' na mesma consulta, evitando o problema N+1.
     * @param id O ID do autor a ser buscado.
     * @return um Optional contendo o Author com sua lista de livros inicializada.
     */
    @Override
    @EntityGraph(value = "Author.withBooks")
    Optional<Author> findById(Long id);
}
