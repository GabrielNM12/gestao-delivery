package com.techsolutions.rangofast.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entregador da frota da RangoFast.
 *
 * HERANCA: estende a classe abstrata {@link Pessoa}.
 * POLIMORFISMO: implementa {@link #getTipo()} de forma diferente do Cliente.
 */
@Entity
@DiscriminatorValue("ENTREGADOR")
public class Entregador extends Pessoa {

    private String veiculo;
    private boolean disponivel = true;

    // ----- SOBRECARGA DE CONSTRUTORES -----
    public Entregador() {
        super();
    }

    public Entregador(String nome, String email, String telefone) {
        super(nome, email, telefone);
    }

    public Entregador(String nome, String email, String telefone, String veiculo) {
        super(nome, email, telefone);
        this.veiculo = veiculo;
    }

    @Override
    public String getTipo() {
        return "ENTREGADOR";
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
