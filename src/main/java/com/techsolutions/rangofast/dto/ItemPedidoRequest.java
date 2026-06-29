package com.techsolutions.rangofast.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para um item dentro de um Pedido.
 */
public record ItemPedidoRequest(
        @NotBlank(message = "O nome do produto e obrigatorio")
        String produto,

        @NotNull(message = "A quantidade e obrigatoria")
        @Min(value = 1, message = "A quantidade minima e 1")
        Integer quantidade,

        @NotNull(message = "O preco unitario e obrigatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preco deve ser maior que zero")
        BigDecimal precoUnitario
) {
}
