package com.techsolutions.rangofast.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.techsolutions.rangofast.dto.ClienteRequest;
import com.techsolutions.rangofast.dto.ClienteResponse;
import com.techsolutions.rangofast.dto.EnderecoDTO;
import com.techsolutions.rangofast.exception.BusinessException;
import com.techsolutions.rangofast.model.Cliente;
import com.techsolutions.rangofast.model.Endereco;
import com.techsolutions.rangofast.repository.ClienteRepository;
import com.techsolutions.rangofast.service.AbstractCrudService;
import com.techsolutions.rangofast.service.ClienteService;

/**
 * Implementacao do service de Cliente.
 */
@Service
public class ClienteServiceImpl
        extends AbstractCrudService<Cliente, Long>
        implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Cliente, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getNomeRecurso() {
        return "Cliente";
    }

    @Override
    public Page<ClienteResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public ClienteResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    public ClienteResponse criar(ClienteRequest request) {
        // Regra de negocio: email unico (HTTP 400 em caso de violacao)
        if (repository.existsByEmail(request.email())) {
            throw new BusinessException("Ja existe um cliente com o email " + request.email());
        }
        Cliente cliente = new Cliente();
        aplicar(cliente, request);
        return toResponse(repository.save(cliente));
    }

    @Override
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);
        repository.findByEmail(request.email())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new BusinessException("Email ja utilizado por outro cliente");
                });
        aplicar(cliente, request);
        return toResponse(repository.save(cliente));
    }

    @Override
    public void deletar(Long id) {
        removerEntidade(id);
    }

    // ----- Mapeamento -----

    private void aplicar(Cliente cliente, ClienteRequest request) {
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        cliente.setEndereco(toEndereco(request.endereco()));
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(
                c.getId(),
                c.getNome(),
                c.getEmail(),
                c.getTelefone(),
                toEnderecoDTO(c.getEndereco()));
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
