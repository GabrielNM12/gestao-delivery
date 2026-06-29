package com.techsolutions.rangofast.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techsolutions.rangofast.dto.ClienteRequest;
import com.techsolutions.rangofast.dto.ClienteResponse;

/**
 * Contrato do service de Cliente (INTERFACE).
 */
public interface ClienteService
        extends CrudService<ClienteResponse, ClienteRequest, Long> {

    Page<ClienteResponse> listar(Pageable pageable);
}
