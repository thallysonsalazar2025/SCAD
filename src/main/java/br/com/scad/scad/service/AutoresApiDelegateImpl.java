package br.com.scad.scad.service;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.AuthorMapper;
import br.com.scad.scad.generated.api.AutoresApiDelegate;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.generated.model.AutorResponse;
import br.com.scad.scad.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class AutoresApiDelegateImpl implements AutoresApiDelegate {
    private final AuthorRepository authorRepository;

    public AutoresApiDelegateImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public ResponseEntity<AutorResponse> registerAuthor(AutorRequest autorRequest) {
        Author returnSavedAuthor = authorRepository.save(AuthorMapper.toCreateAuthorEntity(autorRequest));
        return new ResponseEntity<>(AuthorMapper.createAutorResponse(returnSavedAuthor), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AutorResponse>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AutorResponse> autorResponses = AuthorMapper.toResponseListAuthor(authors);
        return ResponseEntity.ok(autorResponses);
    }

    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AutorResponse> updateAuthor(Long id, AutorRequest autorRequest) {
        // 1. Busca a entidade Author pelo ID.
        return authorRepository.findById(id)
                .map(authorToUpdate -> {
                    // 2. Usa o Mapper para atualizar apenas os campos necessários.
                    AuthorMapper.updateAuthorFromRequest(autorRequest, authorToUpdate);

                    // 3. Salva a entidade atualizada. O @Transactional garante que um UPDATE será executado.
                    Author updatedAuthor = authorRepository.save(authorToUpdate);

                    // 4. Retorna a resposta com a entidade atualizada.
                    return ResponseEntity.ok(AuthorMapper.createAutorResponse(updatedAuthor));
                })
                .orElse(ResponseEntity.notFound().build()); // Se não encontrou o autor, retorna 404 Not Found.
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.delete(author.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
