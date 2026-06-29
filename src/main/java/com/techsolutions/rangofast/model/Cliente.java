package com.techsolutions.rangofast.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

/**
 * Cliente da plataforma RangoFast.
 *
 * HERANCA: estende a classe abstrata {@link Pessoa}.
 * COMPOSICAO: possui um {@link Endereco} embutido.
 * POLIMORFISMO: implementa {@link #getTipo()} e sobrescreve
 * {@link #gerarNotificacao(String)}.
 */
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa {

    @Embedded
    private Endereco endereco;

    // ----- SOBRECARGA DE CONSTRUTORES -----
    public Cliente() {
        super();
    }

    public Cliente(String nome, String email, String telefone) {
        super(nome, email, telefone);
    }

    public Cliente(String nome, String email, String telefone, Endereco endereco) {
        super(nome, email, telefone);
        this.endereco = endereco;
    }

    @Override
    public String getTipo() {
        return "CLIENTE";
    }

    // Sobrescrita (polimorfismo) com mensagem especifica de cliente.
    @Override
    public String gerarNotificacao(String mensagem) {
        return String.format("Ola %s! Sobre seu pedido: %s", getNome(), mensagem);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
