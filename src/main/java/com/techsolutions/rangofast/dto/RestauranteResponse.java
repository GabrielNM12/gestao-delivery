package com.techsolutions.rangofast.dto;

import java.math.BigDecimal;

import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;

/**
 * DTO de saida (ResponseEntity) representando um Restaurante.
 */
public record RestauranteResponse(
        Long id,
        String nome,
        CategoriaRestaurante categoria,
        BigDecimal taxaEntrega,
        boolean ativo,
        EnderecoDTO endereco
) {
}
