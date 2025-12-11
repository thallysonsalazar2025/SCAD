package br.com.scad.scad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;


public record ClientRequest(
        @NotBlank(message = "O ID do cliente não pode estar em branco.")
        @Size(max = 150, message = "O ID do cliente deve ter no máximo 150 caracteres.")
        String clientId,

        @NotBlank(message = "O segredo do cliente não pode estar em branco.")
        @Size(max = 400, message = "O segredo do cliente deve ter no máximo 400 caracteres.")
        String clientSecret,

        @NotBlank(message = "A URI de redirecionamento não pode estar em branco.")
        @URL(message = "A URI de redirecionamento deve ser uma URL válida.")
        @Size(max = 200, message = "A URI de redirecionamento deve ter no máximo 200 caracteres.")
        String redirectUri,

        @NotBlank(message = "O escopo não pode estar em branco.")
        String scope
) {
}
