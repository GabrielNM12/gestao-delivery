package com.techsolutions.rangofast.service.impl;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.techsolutions.rangofast.dto.EnderecoDTO;
import com.techsolutions.rangofast.dto.RestauranteRequest;
import com.techsolutions.rangofast.dto.RestauranteResponse;
import com.techsolutions.rangofast.model.Endereco;
import com.techsolutions.rangofast.model.Restaurante;
import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;
import com.techsolutions.rangofast.repository.RestauranteRepository;
import com.techsolutions.rangofast.service.AbstractCrudService;
import com.techsolutions.rangofast.service.RestauranteService;

/**
 * Implementacao do service de Restaurante.
 *
 * HERANCA: estende {@link AbstractCrudService} para reaproveitar busca/remocao.
 * POLIMORFISMO: implementa os metodos abstratos getRepository/getNomeRecurso.
 */
@Service
public class RestauranteServiceImpl
        extends AbstractCrudService<Restaurante, Long>
        implements RestauranteService {

    private final RestauranteRepository repository;

    public RestauranteServiceImpl(RestauranteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Restaurante, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getNomeRecurso() {
        return "Restaurante";
    }

    @Override
    public Page<RestauranteResponse> listar(String nome,
                                            CategoriaRestaurante categoria,
                                            Boolean ativo,
                                            Pageable pageable) {
        return repository.buscarComFiltros(nome, categoria, ativo, pageable)
                .map(this::toResponse);
    }

    @Override
    public RestauranteResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    public RestauranteResponse criar(RestauranteRequest request) {
        Restaurante restaurante = new Restaurante();
        aplicar(restaurante, request);
        return toResponse(repository.save(restaurante));
    }

    @Override
    public RestauranteResponse atualizar(Long id, RestauranteRequest request) {
        Restaurante restaurante = buscarEntidade(id);
        aplicar(restaurante, request);
        return toResponse(repository.save(restaurante));
    }

    @Override
    public void deletar(Long id) {
        removerEntidade(id);
    }

    // ----- Mapeamento DTO <-> Entidade -----

    private void aplicar(Restaurante restaurante, RestauranteRequest request) {
        restaurante.setNome(request.nome());
        restaurante.setCategoria(request.categoria());
        restaurante.setTaxaEntrega(request.taxaEntrega() != null
                ? request.taxaEntrega() : BigDecimal.ZERO);
        restaurante.setAtivo(request.ativo() == null || request.ativo());
        restaurante.setEndereco(toEndereco(request.endereco()));
    }

    private RestauranteResponse toResponse(Restaurante r) {
        return new RestauranteResponse(
                r.getId(),
                r.getNome(),
                r.getCategoria(),
                r.getTaxaEntrega(),
                r.isAtivo(),
                toEnderecoDTO(r.getEndereco()));
    }

    private Endereco toEndereco(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Endereco(dto.logradouro(), dto.numero(), dto.bairro(),
                dto.cidade(), dto.estado(), dto.cep());
    }

    private EnderecoDTO toEnderecoDTO(Endereco e) {
        if (e == null) {
            return null;
        }
        return new EnderecoDTO(e.getLogradouro(), e.getNumero(), e.getBairro(),
                e.getCidade(), e.getEstado(), e.getCep());
    }
}
