package br.com.scad.scad.service.mapper;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.dto.response.AuthorSummaryResponse;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public abstract class AuthorMapper {

    /**
     * Converte a entidade Author para o DTO AutorResponse.
     * Usa o método qualificado 'toBookResponseWithoutAuthor' do BookMapper para evitar recursão.
     */
    @Mapping(source = "books", target = "books", qualifiedByName = "toBookResponseWithoutAuthor")
    public abstract AutorResponse toAutorResponse(Author author);

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
    public abstract List<AutorResponse> toResponseListAuthor(List<Author> authors);

    /**
     * Atualiza uma entidade Author existente a partir de um AutorRequest.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "dateSaved", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    public abstract void updateAuthorFromRequest(AutorRequest autorRequest, @MappingTarget Author author);

    /**
     * Converte uma entidade Author para um DTO de resumo (AuthorSummaryResponse).
     * Este método é a chave para quebrar o ciclo de recursão quando um autor é aninhado dentro de um livro.
     * Não possui mapeamentos complexos, pois os nomes dos campos coincidem.
     */
    public abstract AuthorSummaryResponse toAuthorSummaryResponse(Author author);
}
