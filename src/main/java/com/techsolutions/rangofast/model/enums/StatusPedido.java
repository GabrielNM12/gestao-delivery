package com.techsolutions.rangofast.model.enums;

/**
 * Estados possiveis de um pedido ao longo do fluxo de delivery.
 */
public enum StatusPedido {
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    EM_PREPARO("Em preparo"),
    SAIU_PARA_ENTREGA("Saiu para entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
