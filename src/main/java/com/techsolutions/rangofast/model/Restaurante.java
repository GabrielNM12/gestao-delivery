package com.techsolutions.rangofast.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;

/**
 * Restaurante parceiro da RangoFast.
 *
 * COMPOSICAO: possui um {@link Endereco} embutido.
 * ENCAPSULAMENTO: atributos privados com acesso controlado.
 */
@Entity
@Table(name = "restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaRestaurante categoria;

    @Column(name = "taxa_entrega", nullable = false)
    private BigDecimal taxaEntrega = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean ativo = true;

    @Embedded
    private Endereco endereco;

    // ----- SOBRECARGA DE CONSTRUTORES -----
    public Restaurante() {
    }

    public Restaurante(String nome, CategoriaRestaurante categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public Restaurante(String nome, CategoriaRestaurante categoria,
                       BigDecimal taxaEntrega, Endereco endereco) {
        this.nome = nome;
        this.categoria = categoria;
        this.taxaEntrega = taxaEntrega;
        this.endereco = endereco;
    }

    // ----- ENCAPSULAMENTO -----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaRestaurante getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRestaurante categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
