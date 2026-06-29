package com.techsolutions.rangofast.dto;

import java.math.BigDecimal;

/**
 * DTO de saida representando um item de pedido, incluindo o subtotal calculado.
 */
public record ItemPedidoResponse(
        Long id,
        String produto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
