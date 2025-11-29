package br.com.scad.scad.service;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.Book;
import br.com.scad.scad.domain.validator.ValidateBook;
import br.com.scad.scad.generated.model.BookRequest;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.repository.BookRepository;
import br.com.scad.scad.service.mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final ValidateBook validateBook;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, ValidateBook validateBook) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.validateBook = validateBook;
    }

    public Book createBook(BookRequest bookRequest) {
        validateBook.validateBook(bookRequest);
        Book bookEntity = bookMapper.toCreateBookEntity(bookRequest);
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + bookRequest.getAuthorId()));
        bookEntity.setAuthor(author);
        return bookRepository.save(bookEntity);
    }

    @Transactional(readOnly = true)
    public Optional<Book> findBookById(Long id) {
        return Optional.of(bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id)));

    }

    @Transactional(readOnly = true)
    public Optional<Book> updateBook(Long id, BookRequest bookRequest) {
        validateBook.validateBook(bookRequest);
        return bookRepository.findById(id)
                .map(bookToUpdate -> {
                    bookToUpdate.setTitle(bookRequest.getTitle());
                    bookToUpdate.setIsbn(bookRequest.getIsbn());
                    bookToUpdate.setDatePublisher(bookRequest.getDatePublisher());
                    bookToUpdate.setPrice(new BigDecimal(bookRequest.getPrice()));
                    if (!bookToUpdate.getAuthor().getId().equals(bookRequest.getAuthorId())) {
                        Author newAuthor = authorRepository.findById(bookRequest.getAuthorId())
                                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + bookRequest.getAuthorId()));
                        bookToUpdate.setAuthor(newAuthor);
                    }
                    return bookToUpdate;
                });
    }

    @Transactional
    public boolean deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBooks(String title, String isbn, LocalDate datePublisher) {
        return bookRepository.findAll();
    }


}
