package com.techsolutions.rangofast.dto;

import com.techsolutions.rangofast.model.enums.StatusPedido;

import jakarta.validation.constraints.NotNull;

/**
 * DTO usado no PATCH para atualizar apenas o status de um pedido.
 */
public record AtualizarStatusRequest(
        @NotNull(message = "O novo status e obrigatorio")
        StatusPedido status
) {
}
