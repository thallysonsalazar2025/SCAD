package br.com.scad.scad.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) // Habilita a auditoria de datas
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    // Abordagem correta para List<String> com JPA padrão
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private List<String> roles;

    @CreatedDate
    @Column(name = "data_inclusao", nullable = false, updatable = false)
    private LocalDate dateInclusion;

    @LastModifiedDate
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dateUpdated;

    public User(String name, String cpf, String login, String password, List<String> roles) {
        this.name = name;
        this.cpf = cpf;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String cpf, String login, String password, LocalDate dateInclusion, List<String> roles, LocalDate dateUpdated) {
        this.name = name;
        this.cpf = cpf;
        this.login = login;
        this.password = password;
        this.dateInclusion = dateInclusion;
        this.roles = roles;
        this.dateUpdated = dateUpdated;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateInclusion() {
        return dateInclusion;
    }

    public void setDateInclusion(LocalDate dateInclusion) {
        this.dateInclusion = dateInclusion;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
