package br.com.scad.scad.domain.validator;

import br.com.scad.scad.domain.exception.AuthorException;
import br.com.scad.scad.generated.model.BookRequest;
import br.com.scad.scad.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateBook {
    @Autowired
    BookRepository bookRepository;

    public void validateBook(BookRequest bookRequest) throws AuthorException {
        if (bookRepository.existsByTitleAndDatePublisherAndAuthorId(bookRequest.getTitle(), bookRequest.getDatePublisher(), bookRequest.getAuthorId())){
            throw new AuthorException("Já existe um livro cadastrado com estes dados.");
        }

    }


}
