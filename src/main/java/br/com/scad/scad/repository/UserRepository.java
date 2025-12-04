package br.com.scad.scad.repository;

import br.com.scad.scad.domain.UserDomain;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDomain, Long> {
    UserDomain findByLogin(@NotNull String login);
}