package com.techsolutions.rangofast.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techsolutions.rangofast.dto.RestauranteRequest;
import com.techsolutions.rangofast.dto.RestauranteResponse;
import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;

/**
 * Contrato do service de Restaurante (INTERFACE).
 */
public interface RestauranteService
        extends CrudService<RestauranteResponse, RestauranteRequest, Long> {

    /**
     * Listagem com FILTRO + PAGINACAO + ORDENACAO.
     */
    Page<RestauranteResponse> listar(String nome,
                                     CategoriaRestaurante categoria,
                                     Boolean ativo,
                                     Pageable pageable);
}
