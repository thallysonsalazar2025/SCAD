package br.com.scad.scad.domain;

import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;

import java.util.List;
import java.util.Optional;

public class AuthorMapper {
    public static Author toCreateAuthorEntity(AutorRequest autorRequest) {
        if (autorRequest != null) {
            Author author = new Author();
            author.setName(autorRequest.getName());
            author.setDateBirth(autorRequest.getDateBirth());
            author.setNacionalityCountry(autorRequest.getNacionalityCountry());
            return author;
        }
        return null;
    }

    public static AutorResponse createAutorResponse(Author returnSavedAuthor) {
        if (returnSavedAuthor != null) {
            AutorResponse response = new AutorResponse();
            response.setId(returnSavedAuthor.getId());
            response.setName(returnSavedAuthor.getName());
            response.setDateBirth(returnSavedAuthor.getDateBirth());
            response.setNacionalityCountry(returnSavedAuthor.getNacionalityCountry());
            return response;
        }
        return null;
    }

    public static List<AutorResponse> toResponseListAuthor(List<Author> authors) {
        Optional.ofNullable(authors).orElseThrow(() -> new IllegalArgumentException("Authors cannot be null"));
        return authors.stream()
                .map(AuthorMapper::createAutorResponse)
                .toList();
    }

    /**
     * Atualiza uma entidade Author existente com os dados não nulos de um AutorRequest.
     * @param request O objeto com os novos dados (pode ter campos nulos).
     * @param entityToUpdate A entidade vinda do banco de dados que será modificada.
     */
    public static void updateAuthorFromRequest(AutorRequest request, Author entityToUpdate) {
        if (request.getName() != null && !request.getName().isBlank()) {
            entityToUpdate.setName(request.getName());
        }
        if (request.getDateBirth() != null) {
            entityToUpdate.setDateBirth(request.getDateBirth());
        }
        if (request.getNacionalityCountry() != null && !request.getNacionalityCountry().isBlank()) {
            entityToUpdate.setNacionalityCountry(request.getNacionalityCountry());
        }
    }
}
