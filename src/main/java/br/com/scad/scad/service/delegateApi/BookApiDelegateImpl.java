package br.com.scad.scad.service.delegateApi;

import br.com.scad.scad.domain.Book;
import br.com.scad.scad.domain.validator.ValidateBook;
import br.com.scad.scad.generated.api.BooksApiDelegate;
import br.com.scad.scad.generated.model.BookRequest;
import br.com.scad.scad.generated.model.BookResponse;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.repository.BookRepository;
import br.com.scad.scad.service.BookService;
import br.com.scad.scad.service.mapper.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookApiDelegateImpl implements BooksApiDelegate {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final ValidateBook validateBook;
    private final BookService bookService;

    public BookApiDelegateImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, ValidateBook validateBook, BookService bookService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.validateBook = validateBook;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public ResponseEntity<BookResponse> registerBook(BookRequest bookRequest) {
        Book savedBook = bookService.createBook(bookRequest);
        return new ResponseEntity<>(bookMapper.toBookResponse(savedBook), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<BookResponse> getBookById(Long id) {
        Optional<Book> book = bookService.findBookById(id);
        return ResponseEntity.ok(bookMapper.toBookResponseWithoutAuthor(book.get()));
    }

    @Override
    public ResponseEntity<BookResponse> updateBook(Long id, BookRequest bookRequest) {
        Optional<Book>bookResponse = bookService.updateBook(id,bookRequest);
        return ResponseEntity.ok(bookMapper.toBookResponse(bookResponse.get()));
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
