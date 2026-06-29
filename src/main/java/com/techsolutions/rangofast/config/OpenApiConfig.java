package com.techsolutions.rangofast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Configuracao da documentacao OpenAPI/Swagger.
 * Disponivel em http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rangoFastOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("RangoFast Delivery API")
                .description("API REST para gestao de delivery regional - "
                        + "restaurantes, clientes e pedidos.")
                .version("1.0.0")
                .contact(new Contact().name("TechSolutions")));
    }
}
