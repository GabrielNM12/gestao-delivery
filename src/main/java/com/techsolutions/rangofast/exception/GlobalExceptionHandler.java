package com.techsolutions.rangofast.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Tratamento CENTRALIZADO de excecoes via @RestControllerAdvice.
 *
 * Converte excecoes em respostas HTTP padronizadas com os status
 * adequados (400, 404, 500), conforme exigido pelos criterios de aceite.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404 - Recurso nao encontrado. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,
                                                        HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /** 400 - Violacao de regra de negocio. */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex,
                                                       HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.badRequest().body(body);
    }

    /** 400 - Falha de validacao dos campos (@Valid). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                         HttpServletRequest request) {
        List<String> detalhes = new ArrayList<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            detalhes.add(erro.getField() + ": " + erro.getDefaultMessage());
        }
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Erro de validacao nos campos enviados",
                request.getRequestURI(),
                detalhes);
        return ResponseEntity.badRequest().body(body);
    }

    /** 400 - Parametro com tipo invalido (ex.: enum/numero incorreto). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                           HttpServletRequest request) {
        String msg = String.format("Valor invalido '%s' para o parametro '%s'",
                ex.getValue(), ex.getName());
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                msg,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(body);
    }

    /** 500 - Qualquer erro nao previsto. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
                                                      HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocorreu um erro inesperado: " + ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
