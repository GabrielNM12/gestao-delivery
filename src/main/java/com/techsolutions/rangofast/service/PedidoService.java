package com.techsolutions.rangofast.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techsolutions.rangofast.dto.PedidoRequest;
import com.techsolutions.rangofast.dto.PedidoResponse;
import com.techsolutions.rangofast.model.enums.StatusPedido;

/**
 * Contrato do service de Pedido (INTERFACE).
 */
public interface PedidoService
        extends CrudService<PedidoResponse, PedidoRequest, Long> {

    /**
     * Listagem com FILTRO + PAGINACAO + ORDENACAO.
     */
    Page<PedidoResponse> listar(StatusPedido status,
                                Long clienteId,
                                Long restauranteId,
                                Pageable pageable);

    /**
     * Atualiza apenas o status do pedido (usado no PATCH).
     */
    PedidoResponse atualizarStatus(Long id, StatusPedido novoStatus);
}
