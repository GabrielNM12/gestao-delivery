package com.techsolutions.rangofast.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.techsolutions.rangofast.dto.ClienteRequest;
import com.techsolutions.rangofast.dto.ClienteResponse;
import com.techsolutions.rangofast.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller REST de Clientes (CRUD completo).
 */
@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestao de clientes da plataforma")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista clientes com paginacao e ordenacao")
    public ResponseEntity<Page<ClienteResponse>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente por id")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo cliente")
    public ResponseEntity<ClienteResponse> criar(
            @Valid @RequestBody ClienteRequest request,
            UriComponentsBuilder uriBuilder) {
        ClienteResponse criado = service.criar(request);
        URI uri = uriBuilder.path("/api/clientes/{id}")
                .buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(uri).body(criado); // 201 Created
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um cliente existente")
    public ResponseEntity<ClienteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um cliente")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
    }
}
