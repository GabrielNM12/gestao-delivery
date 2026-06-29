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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.techsolutions.rangofast.dto.RestauranteRequest;
import com.techsolutions.rangofast.dto.RestauranteResponse;
import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;
import com.techsolutions.rangofast.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller REST de Restaurantes.
 *
 * Expoe o CRUD completo e a listagem com FILTRO + PAGINACAO + ORDENACAO.
 * Usa ResponseEntity e os verbos REST corretos com os status HTTP adequados.
 */
@RestController
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "Gestao de restaurantes parceiros")
public class RestauranteController {

    private final RestauranteService service;

    public RestauranteController(RestauranteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista restaurantes com filtros, paginacao e ordenacao")
    public ResponseEntity<Page<RestauranteResponse>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) CategoriaRestaurante categoria,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<RestauranteResponse> page = service.listar(nome, categoria, ativo, pageable);
        return ResponseEntity.ok(page); // 200 OK
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um restaurante por id")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id)); // 200 OK / 404 se nao existir
    }

    @PostMapping
    @Operation(summary = "Cria um novo restaurante")
    public ResponseEntity<RestauranteResponse> criar(
            @Valid @RequestBody RestauranteRequest request,
            UriComponentsBuilder uriBuilder) {
        RestauranteResponse criado = service.criar(request);
        URI uri = uriBuilder.path("/api/restaurantes/{id}")
                .buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(uri).body(criado); // 201 Created
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um restaurante existente")
    public ResponseEntity<RestauranteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RestauranteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request)); // 200 OK
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um restaurante")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
    }
}
