package com.techsolutions.rangofast.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * CLASSE ABSTRATA base para os atores humanos do sistema (Cliente e
 * Entregador).
 *
 * Demonstra:
 *  - HERANCA: Cliente e Entregador estendem Pessoa.
 *  - CLASSE ABSTRATA: nao pode ser instanciada diretamente.
 *  - POLIMORFISMO: define o metodo abstrato {@link #getTipo()}, que cada
 *    subclasse implementa a sua maneira.
 *  - INTERFACE: implementa {@link Notificavel}.
 *  - ENCAPSULAMENTO: atributos privados expostos por getters/setters.
 *
 * Estrategia de heranca JPA SINGLE_TABLE: todas as subclasses sao
 * persistidas na tabela "pessoa", diferenciadas por uma coluna
 * discriminadora.
 */
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pessoa")
public abstract class Pessoa implements Notificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    // ----- SOBRECARGA DE CONSTRUTORES -----
    protected Pessoa() {
    }

    protected Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    protected Pessoa(String nome, String email, String telefone) {
        this(nome, email);
        this.telefone = telefone;
    }

    /**
     * Metodo ABSTRATO: obriga cada subclasse a informar seu tipo.
     * Base do comportamento POLIMORFICO do sistema.
     */
    public abstract String getTipo();

    /**
     * Implementacao padrao da interface Notificavel. Pode ser
     * sobrescrita pelas subclasses (polimorfismo).
     */
    @Override
    public String gerarNotificacao(String mensagem) {
        return String.format("[%s] Ola %s, %s", getTipo(), nome, mensagem);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
