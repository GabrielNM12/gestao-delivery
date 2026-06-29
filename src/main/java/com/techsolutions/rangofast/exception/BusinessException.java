package com.techsolutions.rangofast.exception;

/**
 * Excecao para violacoes de regra de negocio.
 * Mapeada para HTTP 400 (Bad Request) pelo {@code GlobalExceptionHandler}.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
