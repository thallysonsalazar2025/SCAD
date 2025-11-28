package br.com.scad.scad.service;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.Book;
import br.com.scad.scad.domain.validator.ValidateBook;
import br.com.scad.scad.generated.api.BooksApiDelegate;
import br.com.scad.scad.generated.model.BookRequest;
import br.com.scad.scad.generated.model.BookResponse;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.repository.BookRepository;
import br.com.scad.scad.service.mapper.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookApiDelegateImpl implements BooksApiDelegate {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final ValidateBook validateBook;

    public BookApiDelegateImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, ValidateBook validateBook) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.validateBook = validateBook;
    }

    @Override
    @Transactional
    public ResponseEntity<BookResponse> registerBook(BookRequest bookRequest) {
        validateBook.validateBook(bookRequest);
        // 1. Converter o DTO (BookRequest) para a Entidade (Book)
        Book bookEntity = bookMapper.toCreateBookEntity(bookRequest);

        // 2. Buscar a entidade Author relacionada pelo ID fornecido
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com o ID: " + bookRequest.getAuthorId())); // Use uma exceção mais específica se tiver

        // 3. Associar o autor encontrado à nova entidade livro
        bookEntity.setAuthor(author);

        // 4. Salvar a ENTIDADE no repositório
        Book savedBook = bookRepository.save(bookEntity);

        // 5. Converter a entidade salva para a resposta da API e retornar
        return new ResponseEntity<>(bookMapper.toBookResponse(savedBook), HttpStatus.CREATED);
    }

    // TODO: Implemente os outros métodos do CRUD de livros aqui (updateBook, getBookById, etc.)
}
