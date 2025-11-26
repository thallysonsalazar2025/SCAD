package br.com.scad.scad.repository;

import br.com.scad.scad.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByNameAndDateBirthAndNacionalityCountry(String name, LocalDate dateBirth, String nacionalityCountry);

}
