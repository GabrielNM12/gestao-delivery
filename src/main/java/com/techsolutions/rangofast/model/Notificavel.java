package com.techsolutions.rangofast.model;

/**
 * INTERFACE - contrato para entidades que podem receber notificacoes.
 *
 * Demonstra o conceito de POLIMORFISMO: cada implementacao (Cliente,
 * Entregador) define sua propria forma de ser notificada, mas todas
 * podem ser tratadas de maneira uniforme atraves deste contrato.
 */
public interface Notificavel {

    /**
     * Retorna o canal/mensagem de notificacao especifico da entidade.
     */
    String gerarNotificacao(String mensagem);

    /**
     * Metodo default (recurso de interface do Java 8+): identifica
     * se a entidade aceita notificacoes. Pode ser sobrescrito.
     */
    default boolean aceitaNotificacao() {
        return true;
    }
}
