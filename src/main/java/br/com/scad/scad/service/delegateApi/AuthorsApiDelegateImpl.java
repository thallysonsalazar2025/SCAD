package br.com.scad.scad.service.delegateApi;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.generated.api.AutoresApiDelegate;
import br.com.scad.scad.generated.model.AuthorResponse;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import br.com.scad.scad.service.AuthorService;
import br.com.scad.scad.service.mapper.AuthorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorResponse> registerAuthor(AutorRequest autorRequest) {
        Author savedAuthor = authorService.createAuthor(autorRequest);
        return new ResponseEntity<>(authorMapper.toAutorResponse(savedAuthor), HttpStatus.CREATED);
    }

    //todo incluir o author nessa retorno

    @Override
    @PreAuthorize("hasAnyRole('ADMIN' ,'USER')")
    public ResponseEntity<List<AutorResponse>> getAllAuthors(String nacionalityCountry, String name) {
        List<Author> authors = authorService.findAllAuthors(nacionalityCountry, name);
        List<AutorResponse> autorResponses = authorMapper.toResponseListAuthor(authors);
        return ResponseEntity.ok(autorResponses);
    }
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AutorResponse> updateAuthor(Long id, AutorRequest autorRequest) {
        return new ResponseEntity<>(authorMapper.toUpdateAutorResponse(authorService.findAuthorById(id).get()), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN' ,'USER')")
    public ResponseEntity<AutorResponse> getAuthorById(Long id) {
        return new ResponseEntity<>(authorMapper.toUpdateAutorResponse(authorService.findAuthorById(id).get()), HttpStatus.CREATED);
    }
}
