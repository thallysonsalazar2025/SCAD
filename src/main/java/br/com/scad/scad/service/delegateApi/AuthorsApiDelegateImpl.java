package br.com.scad.scad.service.delegateApi;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.generated.api.AutoresApiDelegate;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import br.com.scad.scad.service.AuthorService;
import br.com.scad.scad.service.mapper.AuthorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorsApiDelegateImpl implements AutoresApiDelegate {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorsApiDelegateImpl(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Override
    public ResponseEntity<AutorResponse> registerAuthor(AutorRequest autorRequest) {
        Author savedAuthor = authorService.createAuthor(autorRequest);
        return new ResponseEntity<>(authorMapper.toAutorResponse(savedAuthor), HttpStatus.CREATED);
    }
    //todo incluir o author nessa retorno

    @Override
    public ResponseEntity<List<AutorResponse>> getAllAuthors(String nacionalityCountry, String name) {
        List<Author> authors = authorService.findAllAuthors(nacionalityCountry, name);
        List<AutorResponse> autorResponses = authorMapper.toResponseListAuthor(authors);
        return ResponseEntity.ok(autorResponses);
    }
    @Override
    @Transactional
    public ResponseEntity<AutorResponse> updateAuthor(Long id, AutorRequest autorRequest) {
        return authorService.updateAuthor(id, autorRequest)
                .map(authorMapper::toAutorResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<AutorResponse> getAuthorById(Long id) {
        return authorService.findAuthorById(id)
                .map(authorMapper::toAutorResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
