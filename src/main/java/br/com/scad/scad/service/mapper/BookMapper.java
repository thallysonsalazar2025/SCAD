package br.com.scad.scad.service.mapper;

import br.com.scad.scad.domain.Book;
import br.com.scad.scad.generated.model.BookPage;
import br.com.scad.scad.generated.model.BookRequest;
import br.com.scad.scad.generated.model.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

/**
 * Mapper para a entidade Book e seus DTOs.
 */
@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    /**
     * Converte um BookRequest em uma entidade Book para CRIAÇÃO.
     * Ignoramos 'id' e 'author' pois são responsabilidade da camada de serviço.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    Book toCreateBookEntity(BookRequest bookRequest);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "author", ignore = true)
//    List<BooksOfAuthorResponse> toCreateBookEntityBooksOfAuthorResponse(BookRequest bookRequest);

    /**
     * Converte uma entidade Book para o DTO BookResponse (incluindo o autor).
     * Este é o mapeamento padrão.
     */
    // O MapStruct é inteligente: ele vê que o campo 'author' no BookResponse é do tipo AuthorSummaryResponse
    // e procura no AuthorMapper (declarado em 'uses') um método que converta Author -> AuthorSummaryResponse.
    // Ele encontrará 'toAuthorSummaryResponse' e o usará automaticamente.
    BookResponse toBookResponse(Book book);


    /**
     * Converte uma entidade Book para o DTO BookResponse, mas IGNORA o campo 'author'.
     * Este método é usado para quebrar o ciclo de recursão ao mapear um autor com seus livros.
     * A anotação @Named permite que outros mappers se refiram a este método específico.
     */
    @Named("toBookResponseWithoutAuthor")
    @Mapping(target = "author", ignore = true)
    BookResponse toBookResponseWithoutAuthor(Book book);


    BookPage toBookPage(Page<Book> response);

}
