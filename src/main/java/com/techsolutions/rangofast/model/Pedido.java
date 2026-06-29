package com.techsolutions.rangofast.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techsolutions.rangofast.model.enums.StatusPedido;

/**
 * Pedido realizado por um Cliente em um Restaurante.
 *
 * COMPOSICAO: contem uma COLLECTION de {@link ItemPedido} cujo ciclo de
 * vida e gerenciado pelo proprio pedido (cascade + orphanRemoval).
 * Demonstra tambem SOBRECARGA DE METODO em {@code calcularTotal}.
 */
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // COLLECTION + COMPOSICAO
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemPedido> itens = new ArrayList<>();

    // ----- SOBRECARGA DE CONSTRUTORES -----
    public Pedido() {
    }

    public Pedido(Cliente cliente, Restaurante restaurante) {
        this.cliente = cliente;
        this.restaurante = restaurante;
    }

    public Pedido(Cliente cliente, Restaurante restaurante, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.restaurante = restaurante;
        adicionarItens(itens);
    }

    /**
     * Metodo utilitario que mantem a consistencia da relacao bidirecional.
     */
    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        this.itens.add(item);
    }

    public void adicionarItens(List<ItemPedido> novosItens) {
        if (novosItens != null) {
            for (ItemPedido item : novosItens) {
                adicionarItem(item);
            }
        }
    }

    // ===== SOBRECARGA DE METODO (mesmo nome, assinaturas diferentes) =====

    /**
     * Calcula o total somando apenas o subtotal dos itens.
     */
    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    /**
     * Sobrecarga: calcula o total dos itens somando uma taxa de entrega.
     */
    public BigDecimal calcularTotal(BigDecimal taxaEntrega) {
        BigDecimal total = calcularTotal();
        if (taxaEntrega != null) {
            total = total.add(taxaEntrega);
        }
        return total;
    }

    // ----- ENCAPSULAMENTO -----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens.clear();
        adicionarItens(itens);
    }
}
