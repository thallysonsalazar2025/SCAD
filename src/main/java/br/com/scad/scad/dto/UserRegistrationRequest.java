package br.com.scad.scad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
public record UserRegistrationRequest(
        @NotBlank(message = "O nome não pode ser vazio.")
        String nome,
        @NotBlank(message = "O CPF não pode ser vazio.")
        String cpf,
        @NotBlank(message = "O login não pode ser vazio.")
        String login,
        @NotBlank(message = "A senha não pode ser vazia.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,
        @NotEmpty(message = "O usuário deve ter pelo menos uma role.")
        List<String> roles) {


        @Override
        public String nome() {
                return nome;
        }

        @Override
        public String cpf() {
                return cpf;
        }

        @Override
        public String login() {
                return login;
        }

        @Override
        public String senha() {
                return senha;
        }

        @Override
        public List<String> roles() {
                return roles;
        }
}