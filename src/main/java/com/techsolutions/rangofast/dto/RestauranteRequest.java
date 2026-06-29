package com.techsolutions.rangofast.dto;

import java.math.BigDecimal;

import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada (@RequestBody) para criar/atualizar Restaurante.
 */
public record RestauranteRequest(
        @NotBlank(message = "O nome do restaurante e obrigatorio")
        String nome,

        @NotNull(message = "A categoria e obrigatoria")
        CategoriaRestaurante categoria,

        @NotNull(message = "A taxa de entrega e obrigatoria")
        @DecimalMin(value = "0.0", message = "A taxa de entrega nao pode ser negativa")
        BigDecimal taxaEntrega,

        Boolean ativo,

        @Valid
        EnderecoDTO endereco
) {
}
