package com.techsolutions.rangofast.exception;

/**
 * Excecao lancada quando um recurso solicitado nao e encontrado.
 * Mapeada para HTTP 404 (Not Found) pelo {@code GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA DE CONSTRUTORES: monta a mensagem a partir do recurso e id.
    public ResourceNotFoundException(String recurso, Object id) {
        super(String.format("%s nao encontrado(a) para o id %s", recurso, id));
    }
}
