package com.techsolutions.rangofast.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.techsolutions.rangofast.dto.AtualizarStatusRequest;
import com.techsolutions.rangofast.dto.PedidoRequest;
import com.techsolutions.rangofast.dto.PedidoResponse;
import com.techsolutions.rangofast.model.enums.StatusPedido;
import com.techsolutions.rangofast.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller REST de Pedidos (CRUD completo + PATCH de status).
 *
 * Demonstra todos os verbos REST: GET, POST, PUT, PATCH e DELETE,
 * alem de FILTRO + PAGINACAO + ORDENACAO na listagem.
 */
@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestao dos pedidos de delivery")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista pedidos com filtros, paginacao e ordenacao")
    public ResponseEntity<Page<PedidoResponse>> listar(
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long restauranteId,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {
        return ResponseEntity.ok(service.listar(status, clienteId, restauranteId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido por id")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo pedido")
    public ResponseEntity<PedidoResponse> criar(
            @Valid @RequestBody PedidoRequest request,
            UriComponentsBuilder uriBuilder) {
        PedidoResponse criado = service.criar(request);
        URI uri = uriBuilder.path("/api/pedidos/{id}")
                .buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(uri).body(criado); // 201 Created
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza por completo um pedido")
    public ResponseEntity<PedidoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza apenas o status de um pedido")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusRequest request) {
        return ResponseEntity.ok(service.atualizarStatus(id, request.status()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um pedido")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
    }
}
