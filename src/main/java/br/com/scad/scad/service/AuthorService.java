package br.com.scad.scad.service;

import br.com.scad.scad.domain.Author;
import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.domain.validator.ValidateAuthor;
import br.com.scad.scad.generated.model.AutorRequest;
import br.com.scad.scad.repository.AuthorRepository;
import br.com.scad.scad.security.SecurityService;
import br.com.scad.scad.service.mapper.AuthorMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final ValidateAuthor validateAuthor;
    private final SecurityService securityService;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, ValidateAuthor validateAuthor, SecurityService securityService) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.validateAuthor = validateAuthor;
        this.securityService = securityService;
    }

    public Author createAuthor(AutorRequest autorRequest) {
        validateAuthor.validateAuthor(autorRequest.getName(), autorRequest.getDateBirth(), autorRequest.getNacionalityCountry());
        UserDomain userAuthen = securityService.getUserAuth();
        autorRequest.setIdUser(userAuthen.getId());
        Author authorEntity = authorMapper.toAuthor(autorRequest);
        return authorRepository.save(authorEntity);
    }

    @Transactional(readOnly = true)
    public List<Author> findAllAuthors(String nacionalityCountry, String name) {
        Author authorRequest = new Author();
        authorRequest.setNacionalityCountry(nacionalityCountry);
        authorRequest.setName(name);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> exampleAuthor = Example.of(authorRequest, exampleMatcher);

        return authorRepository.findAll(exampleAuthor);
    }

    public Optional<Author> updateAuthor(Long id, AutorRequest autorRequest) {
        validateAuthor.validateAuthor(autorRequest.getName(), autorRequest.getDateBirth(), autorRequest.getNacionalityCountry());
        return authorRepository.findById(id)
                .map(authorToUpdate -> {
                    authorMapper.updateAuthorFromRequest(autorRequest, authorToUpdate);
                    return authorToUpdate; // O repositório salvará automaticamente no final da transação
                });
    }

    public boolean deleteAuthor(Long id) {
        return authorRepository.findById(id)
                .map(author -> {
                    validateAuthor.validateAuthorCanBeDeleted(author);
                    authorRepository.delete(author);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}