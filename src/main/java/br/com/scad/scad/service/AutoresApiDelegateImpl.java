package br.com.scad.scad.service;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.AuthorMapper;
import br.com.scad.scad.domain.validator.ValidateAuthor;
import br.com.scad.scad.generated.api.AutoresApiDelegate;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import br.com.scad.scad.repository.AuthorRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class AutoresApiDelegateImpl implements AutoresApiDelegate {
    private final AuthorRepository authorRepository;
    private final ValidateAuthor validateAuthor;

    public AutoresApiDelegateImpl(AuthorRepository authorRepository, ValidateAuthor validateAuthor) {
        this.authorRepository = authorRepository;
        this.validateAuthor = validateAuthor;
    }

    @Override
    public ResponseEntity<AutorResponse> registerAuthor(AutorRequest autorRequest) {
        // 1. Valida as regras de negócio. Se o autor já existir, uma exceção será
        //    lançada e capturada pelo RestExceptionHandler, interrompendo o fluxo.
        validateAuthor.validateAuthor(autorRequest.getName(), autorRequest.getDateBirth(), autorRequest.getNacionalityCountry());

        // 2. Se a validação passar, o código continua para criar e salvar a entidade.
        Author returnSavedAuthor = authorRepository.save(AuthorMapper.toCreateAuthorEntity(autorRequest));
        return new ResponseEntity<>(AuthorMapper.createAutorResponse(returnSavedAuthor), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AutorResponse>> getAllAuthors(String nacionalityCountry, String name) {
        Author authorRequest = new  Author();
        authorRequest.setNacionalityCountry(nacionalityCountry);
        authorRequest.setName(name);
        //todo cria um Objeto do tipo ExampleMatcher com as regras para a busca.
       ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        //todo cria uma lista do tipo Example passado as regras e o objeto de retorno da busca
        Example<Author> exampleAuthor = Example.of(authorRequest,exampleMatcher);

        List<Author> authors = authorRepository.findAll(exampleAuthor);
        List<AutorResponse> autorResponses = AuthorMapper.toResponseListAuthor(authors);
        return ResponseEntity.ok(autorResponses);
    }

    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AutorResponse> updateAuthor(Long id, AutorRequest autorRequest) {
        validateAuthor.validateAuthor(autorRequest.getName(), autorRequest.getDateBirth(), autorRequest.getNacionalityCountry());
        // 1. Busca a entidade Author pelo ID.
        return authorRepository.findById(id)
                .map(authorToUpdate -> {
                    // 2. Usa o Mapper para atualizar apenas os campos necessários.
                    AuthorMapper.updateAuthorFromRequest(autorRequest, authorToUpdate);

                    // 3. O @Transactional garante que as alterações serão salvas automaticamente (dirty checking).
                    // A chamada explícita ao 'save' não é necessária para atualizações.
                    return ResponseEntity.ok(AuthorMapper.createAutorResponse(authorToUpdate));
                })
                .orElse(ResponseEntity.notFound().build()); // Se não encontrou o autor, retorna 404 Not Found.
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            validateAuthor.validateAuthorCanBeDeleted(author.get());
            authorRepository.delete(author.get());
            return ResponseEntity.noContent().build();
            }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(value -> ResponseEntity.ok(
                        AuthorMapper.createAutorResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
