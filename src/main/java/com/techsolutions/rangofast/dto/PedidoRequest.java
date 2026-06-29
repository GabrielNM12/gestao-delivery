package com.techsolutions.rangofast.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada (@RequestBody) para criar um Pedido.
 */
public record PedidoRequest(
        @NotNull(message = "O id do cliente e obrigatorio")
        Long clienteId,

        @NotNull(message = "O id do restaurante e obrigatorio")
        Long restauranteId,

        Long entregadorId,

        @NotEmpty(message = "O pedido deve conter ao menos um item")
        @Valid
        List<ItemPedidoRequest> itens
) {
}
