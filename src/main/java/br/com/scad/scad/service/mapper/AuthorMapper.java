package br.com.scad.scad.service.mapper;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = BookMapper.class) // Adicionado 'uses = BookMapper.class' de volta
public abstract class AuthorMapper {

    /**
     * Converte a entidade Author para o DTO AutorResponse.
     * O BookMapper é passado como um parâmetro de contexto para quebrar o ciclo de dependência.
     */
    @Mapping(source = "books", target = "books", qualifiedByName = "toBookResponseWithoutAuthor")
    public abstract AutorResponse toAutorResponse(Author author, @Context BookMapper bookMapper);

    /**
     * Converte o DTO AutorRequest para a entidade Author.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "dateSaved", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    public abstract Author toAuthor(AutorRequest autorRequest);

    /**
     * Converte uma lista de entidades Author para uma lista de DTOs AutorResponse.
     */
    public abstract List<AutorResponse> toResponseListAuthor(List<Author> authors, @Context BookMapper bookMapper);

    /**
     * Atualiza uma entidade Author existente a partir de um AutorRequest.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "dateSaved", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    public abstract void updateAuthorFromRequest(AutorRequest autorRequest, @MappingTarget Author author);
}
