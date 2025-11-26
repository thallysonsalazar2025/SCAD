package br.com.scad.scad.domain.validator;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.exception.AuthorException;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.repository.BookRepository;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateAuthor {

    public final AuthorRepository repository;
    public final BookRepository bookRepository;

    public ValidateAuthor(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }


    public void validateAuthor(String name, LocalDate dateBirth, String nacionalityCountry) throws AuthorException{
        if (repository.existsByNameAndDateBirthAndNacionalityCountry(name, dateBirth, nacionalityCountry)) {
            throw new AuthorException("Já existe um autor cadastrado com estes dados.");
        }
    }

    /**
     * Valida se um autor pode ser deletado, verificando se ele não possui livros associados.
     * @param author O autor a ser verificado.
     */
    public void validateAuthorCanBeDeleted(Author author){
        if (bookRepository.existsByAuthor(author)){
            throw new AuthorException("Não é possível deletar este autor, pois ele possui livros vinculados.");
        }
    }
}
