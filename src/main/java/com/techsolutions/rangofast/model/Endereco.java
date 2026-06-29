package com.techsolutions.rangofast.model;

import jakarta.persistence.Embeddable;

/**
 * Objeto de valor embutido (COMPOSICAO via @Embeddable).
 *
 * Um Endereco nao existe de forma independente: ele faz parte de um
 * Restaurante ou de um Cliente (relacao "tem-um" / composicao forte).
 */
@Embeddable
public class Endereco {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // ----- SOBRECARGA DE CONSTRUTORES -----
    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String bairro,
                    String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    /**
     * Retorna o endereco formatado em uma unica linha.
     */
    public String formatado() {
        return String.format("%s, %s - %s, %s/%s - CEP %s",
                logradouro, numero, bairro, cidade, estado, cep);
    }

    // ----- ENCAPSULAMENTO: getters e setters -----
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
