package com.techsolutions.rangofast.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada (@RequestBody) para criar/atualizar Cliente.
 */
public record ClienteRequest(
        @NotBlank(message = "O nome e obrigatorio")
        String nome,

        @NotBlank(message = "O email e obrigatorio")
        @Email(message = "Email invalido")
        String email,

        String telefone,

        @Valid
        EnderecoDTO endereco
) {
}
