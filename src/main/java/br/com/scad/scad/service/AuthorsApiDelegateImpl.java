package br.com.scad.scad.service;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.validator.ValidateAuthor;
import br.com.scad.scad.generated.api.AutoresApiDelegate;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.service.mapper.AuthorMapper;
import br.com.scad.scad.service.mapper.BookMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsApiDelegateImpl implements AutoresApiDelegate {
    private final AuthorRepository authorRepository;
    private final ValidateAuthor validateAuthor;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper; // Injetado para ser usado como contexto

    public AuthorsApiDelegateImpl(AuthorRepository authorRepository, ValidateAuthor validateAuthor, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.validateAuthor = validateAuthor;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public ResponseEntity<AutorResponse> registerAuthor(AutorRequest autorRequest) {
        validateAuthor.validateAuthor(autorRequest.getName(), autorRequest.getDateBirth(), autorRequest.getNacionalityCountry());
        Author authorEntity = authorMapper.toAuthor(autorRequest);
        Author savedAuthor = authorRepository.save(authorEntity);
        // Passa o bookMapper como contexto para o método do authorMapper
        return new ResponseEntity<>(authorMapper.toAutorResponse(savedAuthor, bookMapper), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AutorResponse>> getAllAuthors(String nacionalityCountry, String name) {
        Author authorRequest = new Author();
        authorRequest.setNacionalityCountry(nacionalityCountry);
        authorRequest.setName(name);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> exampleAuthor = Example.of(authorRequest, exampleMatcher);

        List<Author> authors = authorRepository.findAll(exampleAuthor);
        // Passa o bookMapper como contexto para o método do authorMapper
        List<AutorResponse> autorResponses = authorMapper.toResponseListAuthor(authors, bookMapper);
        return ResponseEntity.ok(autorResponses);
    }

    @Override
    @Transactional
    public ResponseEntity<AutorResponse> updateAuthor(Long id, AutorRequest autorRequest) {
        validateAuthor.validateAuthor(autorRequest.getName(), autorRequest.getDateBirth(), autorRequest.getNacionalityCountry());
        return authorRepository.findById(id)
                .map(authorToUpdate -> {
                    authorMapper.updateAuthorFromRequest(autorRequest, authorToUpdate);
                    // Passa o bookMapper como contexto para o método do authorMapper
                    return ResponseEntity.ok(authorMapper.toAutorResponse(authorToUpdate, bookMapper));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            validateAuthor.validateAuthorCanBeDeleted(author.get());
            authorRepository.delete(author.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<AutorResponse> getAuthorById(Long id) {
        // Usando o método findById padrão, que agora está anotado com @EntityGraph
        return authorRepository.findById(id)
                .map(author -> authorMapper.toAutorResponse(author, bookMapper)) // Passa o bookMapper como contexto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
