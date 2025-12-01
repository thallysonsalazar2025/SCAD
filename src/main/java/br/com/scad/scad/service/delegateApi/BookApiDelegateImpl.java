package br.com.scad.scad.service.delegateApi;

import br.com.scad.scad.domain.Book;
import br.com.scad.scad.generated.api.BooksApiDelegate;
import br.com.scad.scad.generated.model.BookPage;
import br.com.scad.scad.generated.model.BookRequest;
import br.com.scad.scad.generated.model.BookResponse;
import br.com.scad.scad.service.BookService;
import br.com.scad.scad.service.mapper.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookApiDelegateImpl implements BooksApiDelegate {

    private final BookMapper bookMapper;
    private final BookService bookService;

    public BookApiDelegateImpl(BookMapper bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
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

    @Override
    public ResponseEntity<BookPage> getAllBooks(String title, String isbn,
                                                LocalDate datePublisher,
                                                Integer page, Integer size,
                                                List<String> sort) {

        Page<Book> response = bookService.findAllBooks(title, isbn, datePublisher, size, page);
        return ResponseEntity.ok(bookMapper.toBookPage(response));
    }
}
