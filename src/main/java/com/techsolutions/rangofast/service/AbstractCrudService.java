package com.techsolutions.rangofast.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.rangofast.exception.ResourceNotFoundException;

/**
 * CLASSE ABSTRATA base para os services.
 *
 * Centraliza operacoes comuns no nivel da ENTIDADE (buscar por id e
 * deletar), reaproveitando codigo via HERANCA. As subclasses informam,
 * de forma POLIMORFICA, qual repositorio e qual o nome do recurso usar.
 *
 * @param <E>  tipo da entidade
 * @param <ID> tipo do identificador
 */
public abstract class AbstractCrudService<E, ID> {

    /**
     * Metodo ABSTRATO: cada subclasse fornece seu proprio repositorio.
     */
    protected abstract JpaRepository<E, ID> getRepository();

    /**
     * Metodo ABSTRATO: nome do recurso usado nas mensagens de erro.
     */
    protected abstract String getNomeRecurso();

    /**
     * Busca a entidade ou lanca {@link ResourceNotFoundException} (HTTP 404).
     */
    protected E buscarEntidade(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getNomeRecurso(), id));
    }

    /**
     * Remove a entidade, garantindo previamente que ela existe.
     */
    protected void removerEntidade(ID id) {
        E entidade = buscarEntidade(id);
        getRepository().delete(entidade);
    }
}
