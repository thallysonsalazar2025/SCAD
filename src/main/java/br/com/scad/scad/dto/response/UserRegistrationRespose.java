package br.com.scad.scad.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
public record UserRegistrationRespose(
        @NotBlank(message = "O nome não pode ser vazio.")
        String name,
        @NotBlank(message = "O CPF não pode ser vazio.")
        String cpf,
        @NotBlank(message = "O login não pode ser vazio.")
        String login,
        @NotBlank(message = "A senha não pode ser vazia.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String password,
        @NotEmpty(message = "O usuário deve ter pelo menos uma role.")
        List<String> roles){
}