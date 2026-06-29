package com.techsolutions.rangofast.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estrutura padronizada de resposta de erro retornada pela API.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> detalhes
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }

    public ErrorResponse(int status, String error, String message, String path, List<String> detalhes) {
        this(LocalDateTime.now(), status, error, message, path, detalhes);
    }
}
