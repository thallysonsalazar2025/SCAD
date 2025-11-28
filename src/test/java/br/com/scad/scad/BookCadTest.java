package br.com.scad.scad;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.Book;
import br.com.scad.scad.domain.GenreEnum;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class BookCadTest {
    @Autowired // LibraryRepository parece ser um nome antigo, talvez BookRepository seja mais claro.
    private BookRepository libraryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public static final long BOOKS_BY_ID = 4L;
    private static Double priceIs = 10.00;
    private static final LocalDate datePublisher = LocalDate.now();

//    @Test
//    public void sucessCreateNewBook() {
//        log.info("Sucess create new book");
//
//        Book book = new Book();
//
//
//        Author author1 = authorRepository.findById(10L).orElseGet(() -> {
//            Author newAuthor = new Author(BOOKS_BY_ID,mockGetbyBooks(),);
//
//            return authorRepository.save(newAuthor);
//        });
//        book.setAuthor(author1);
//        book.setIsbn("Matrix");
//        book.setGenre(PersonGenereEnum.TERROR);
//        book.setPrice(priceIs);
//        book.setTitle("007");
//        book.setDatePublisher(datePublisher);
//        libraryRepository.saveAndFlush(book);
//        System.out.println("Created book with id: " + book.getId() + ",  " + book.getGenre());
//    }


    // Forma correta usando Cascade
    @Test
    @DisplayName("Deve salvar um autor e seus livros com sucesso usando cascade")
    void saveSucessCreateAuthorAndBook() {
        // Arrange: Preparação dos objetos
        Author author = new Author();
        author.setName("Peris S Topson");
        author.setNacionalityCountry("Brasil");
        author.setDateBirth(LocalDate.of(1998, 5, 14));

        Book book = new Book();
        book.setTitle("BRexis");
        book.setAuthor(author);
        book.setGenre(GenreEnum.ACTION);


        Book book2 = new Book();
        book2.setTitle("Bosnia resurci");
        book.setAuthor(author);
        book.setGenre(GenreEnum.SUSPENSE);

        // CORREÇÃO: A relação bidirecional deve ser estabelecida para AMBOS os livros.
        // O autor deve ser definido em cada livro.
        book.setAuthor(author);
        book2.setAuthor(author);
        author.setBooks(List.of(book, book2));

        // Act: Execução da ação a ser testada
        // Salva a entidade "pai" (Author), que por cascade salvará as entidades "filhas" (Books).
        Author savedAuthor = authorRepository.save(author);

        // Assert: Verificação dos resultados
        assertAll("Verifica se autor e livros foram salvos corretamente",
                () -> assertNotNull(savedAuthor.getId(), "ID do autor não deveria ser nulo"),
                () -> assertNotNull(savedAuthor.getBooks(), "Lista de livros não deveria ser nula"),
                () -> assertEquals(2, savedAuthor.getBooks().size(), "Autor deveria ter 2 livros"),
                () -> assertNotNull(savedAuthor.getBooks().get(0).getId(), "ID do primeiro livro não deveria ser nulo"),
                () -> assertEquals("Peris S Topson", savedAuthor.getBooks().get(0).getAuthor().getName(), "Autor do primeiro livro está incorreto"),
                () -> assertNotNull(savedAuthor.getBooks().get(1).getId(), "ID do segundo livro não deveria ser nulo"),
                () -> assertEquals("Peris S Topson", savedAuthor.getBooks().get(1).getAuthor().getName(), "Autor do segundo livro está incorreto")
        );
    }

    private List<Book> mockGetbyBooks() {
        Book book = new Book();
        book.setIsbn("Matrix");
        book.setGenre(GenreEnum.TERROR);
        book.setPrice(BigDecimal.valueOf(priceIs));
        book.setTitle("007");
        book.setDatePublisher(datePublisher);
        return List.of(book);

    }
}
