package br.com.scad.scad.repository.specs;

import br.com.scad.scad.domain.Book;
import br.com.scad.scad.generated.model.Genre;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


//todo Specification trabalha de forma semelhante ao Example temos que passar o
// valor a ser comparado a query e o critério ou condição
public class BookSpecs {
    public static Specification<Book> requestPageableAndValidParamsForData(String title, String isbn, LocalDate datePublisher) {

        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (title != null) {
            specs.and(titleEqual(title));
        }
        if (isbn != null) {
            specs.and(isbnEqual(isbn));
        }
        if (datePublisher != null) {
            specs.and(datePublisherEqual(datePublisher));

        }
        return specs;

    }

    private static Specification<Book> datePublisherEqual(@NotNull LocalDate datePublisher) {
        return (root, query, cb) -> cb.equal(root.get("datePublisher"), datePublisher);
    }

    private static Specification<Book> titleEqual(@NotNull String title) {
        return (root, query, cb) -> cb.equal(root.get("title"), title);
    }

    private static Specification<Book> isbnEqual(@NotNull String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }
}
