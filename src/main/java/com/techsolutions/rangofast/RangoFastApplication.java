package com.techsolutions.rangofast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da API REST RangoFast Delivery.
 *
 * Sistema de Gestao para Delivery Regional desenvolvido pela TechSolutions
 * para o cliente RangoFast.
 */
@SpringBootApplication
public class RangoFastApplication {

    public static void main(String[] args) {
        SpringApplication.run(RangoFastApplication.class, args);
    }
}
