package br.com.scad.scad.repository.specs;

import br.com.scad.scad.domain.Book;
import br.com.scad.scad.domain.GenreEnum;
import org.springframework.data.jpa.domain.Specification;
//todo Specification trabalha de forma semelhante ao Example temos que passar o
// valor a ser comparado a query e o critério ou condição
public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn){
        return (root, query, builder) -> builder.equal(root.get("isbn"), isbn);
    }

    static Specification<Book> titleLike(String title){
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title+ "%");
    }
    static Specification<Book>genreEquals(GenreEnum genreEnum){
        return (root, query, cb) -> cb.equal(root.get("genre"), genreEnum);
    }
}
