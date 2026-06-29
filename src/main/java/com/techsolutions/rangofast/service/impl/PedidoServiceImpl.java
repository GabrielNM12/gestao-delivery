package com.techsolutions.rangofast.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.techsolutions.rangofast.dto.ItemPedidoRequest;
import com.techsolutions.rangofast.dto.ItemPedidoResponse;
import com.techsolutions.rangofast.dto.PedidoRequest;
import com.techsolutions.rangofast.dto.PedidoResponse;
import com.techsolutions.rangofast.exception.BusinessException;
import com.techsolutions.rangofast.exception.ResourceNotFoundException;
import com.techsolutions.rangofast.model.Cliente;
import com.techsolutions.rangofast.model.Entregador;
import com.techsolutions.rangofast.model.ItemPedido;
import com.techsolutions.rangofast.model.Pedido;
import com.techsolutions.rangofast.model.Restaurante;
import com.techsolutions.rangofast.model.enums.StatusPedido;
import com.techsolutions.rangofast.repository.ClienteRepository;
import com.techsolutions.rangofast.repository.EntregadorRepository;
import com.techsolutions.rangofast.repository.PedidoRepository;
import com.techsolutions.rangofast.repository.RestauranteRepository;
import com.techsolutions.rangofast.service.AbstractCrudService;
import com.techsolutions.rangofast.service.PedidoService;

/**
 * Implementacao do service de Pedido.
 *
 * Reune as regras de negocio do dominio: validacao de entidades
 * relacionadas, calculo de totais (POLIMORFISMO via sobrecarga de
 * calcularTotal) e transicoes de status validas.
 */
@Service
public class PedidoServiceImpl
        extends AbstractCrudService<Pedido, Long>
        implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final EntregadorRepository entregadorRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             ClienteRepository clienteRepository,
                             RestauranteRepository restauranteRepository,
                             EntregadorRepository entregadorRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.entregadorRepository = entregadorRepository;
    }

    @Override
    protected JpaRepository<Pedido, Long> getRepository() {
        return pedidoRepository;
    }

    @Override
    protected String getNomeRecurso() {
        return "Pedido";
    }

    @Override
    public Page<PedidoResponse> listar(StatusPedido status,
                                       Long clienteId,
                                       Long restauranteId,
                                       Pageable pageable) {
        return pedidoRepository.buscarComFiltros(status, clienteId, restauranteId, pageable)
                .map(this::toResponse);
    }

    @Override
    public PedidoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    public PedidoResponse criar(PedidoRequest request) {
        // Demonstracao de TRY-CATCH: erros inesperados viram BusinessException
        try {
            Cliente cliente = clienteRepository.findById(request.clienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.clienteId()));
            Restaurante restaurante = restauranteRepository.findById(request.restauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.restauranteId()));

            Pedido pedido = new Pedido(cliente, restaurante);

            if (request.entregadorId() != null) {
                Entregador entregador = entregadorRepository.findById(request.entregadorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Entregador", request.entregadorId()));
                pedido.setEntregador(entregador);
            }

            pedido.adicionarItens(construirItens(request.itens()));
            pedido.setStatus(StatusPedido.PENDENTE);

            return toResponse(pedidoRepository.save(pedido));
        } catch (ResourceNotFoundException | BusinessException e) {
            throw e; // tratadas pelo GlobalExceptionHandler (404 / 400)
        } catch (Exception e) {
            throw new BusinessException("Nao foi possivel criar o pedido: " + e.getMessage());
        }
    }

    @Override
    public PedidoResponse atualizar(Long id, PedidoRequest request) {
        Pedido pedido = buscarEntidade(id);

        if (pedido.getStatus() == StatusPedido.ENTREGUE
                || pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new BusinessException(
                    "Nao e possivel alterar um pedido ja " + pedido.getStatus().getDescricao());
        }

        Restaurante restaurante = restauranteRepository.findById(request.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.restauranteId()));
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.clienteId()));

        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setItens(construirItens(request.itens()));

        if (request.entregadorId() != null) {
            Entregador entregador = entregadorRepository.findById(request.entregadorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Entregador", request.entregadorId()));
            pedido.setEntregador(entregador);
        }

        return toResponse(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoResponse atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = buscarEntidade(id);
        validarTransicao(pedido.getStatus(), novoStatus);
        pedido.setStatus(novoStatus);
        return toResponse(pedidoRepository.save(pedido));
    }

    @Override
    public void deletar(Long id) {
        removerEntidade(id);
    }

    // ----- Regras auxiliares -----

    private void validarTransicao(StatusPedido atual, StatusPedido novo) {
        if (atual == StatusPedido.ENTREGUE || atual == StatusPedido.CANCELADO) {
            throw new BusinessException(
                    "Pedido " + atual.getDescricao() + " nao pode mudar de status");
        }
    }

    private List<ItemPedido> construirItens(List<ItemPedidoRequest> itensRequest) {
        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoRequest req : itensRequest) {
            itens.add(new ItemPedido(req.produto(), req.quantidade(), req.precoUnitario()));
        }
        return itens;
    }

    // ----- Mapeamento -----

    private PedidoResponse toResponse(Pedido pedido) {
        List<ItemPedidoResponse> itens = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            itens.add(new ItemPedidoResponse(
                    item.getId(),
                    item.getProduto(),
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getSubtotal()));
        }

        BigDecimal subtotal = pedido.calcularTotal();
        BigDecimal taxa = pedido.getRestaurante() != null
                ? pedido.getRestaurante().getTaxaEntrega() : BigDecimal.ZERO;
        // Uso da SOBRECARGA DE METODO: calcularTotal(taxaEntrega)
        BigDecimal total = pedido.calcularTotal(taxa);

        Entregador entregador = pedido.getEntregador();

        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getRestaurante().getId(),
                pedido.getRestaurante().getNome(),
                entregador != null ? entregador.getId() : null,
                entregador != null ? entregador.getNome() : null,
                pedido.getStatus(),
                pedido.getDataCriacao(),
                itens,
                subtotal,
                taxa,
                total);
    }
}
