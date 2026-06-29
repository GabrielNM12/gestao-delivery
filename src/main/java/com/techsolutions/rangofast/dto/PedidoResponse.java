package com.techsolutions.rangofast.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.techsolutions.rangofast.model.enums.StatusPedido;

/**
 * DTO de saida representando um Pedido completo, com itens e totais.
 */
public record PedidoResponse(
        Long id,
        Long clienteId,
        String clienteNome,
        Long restauranteId,
        String restauranteNome,
        Long entregadorId,
        String entregadorNome,
        StatusPedido status,
        LocalDateTime dataCriacao,
        List<ItemPedidoResponse> itens,
        BigDecimal subtotal,
        BigDecimal taxaEntrega,
        BigDecimal total
) {
}
