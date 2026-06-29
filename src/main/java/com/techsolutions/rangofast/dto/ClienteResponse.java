package com.techsolutions.rangofast.dto;

/**
 * DTO de saida representando um Cliente.
 */
public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        EnderecoDTO endereco
) {
}
