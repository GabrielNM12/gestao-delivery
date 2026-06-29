package com.techsolutions.rangofast.dto;

/**
 * DTO de Endereco usado em requisicoes e respostas.
 */
public record EnderecoDTO(
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
