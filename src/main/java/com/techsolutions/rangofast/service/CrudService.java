package com.techsolutions.rangofast.service;

/**
 * INTERFACE generica que define o contrato CRUD comum a todos os services.
 *
 * @param <RES> tipo do DTO de resposta
 * @param <REQ> tipo do DTO de requisicao
 * @param <ID>  tipo do identificador
 */
public interface CrudService<RES, REQ, ID> {

    RES buscarPorId(ID id);

    RES criar(REQ request);

    RES atualizar(ID id, REQ request);

    void deletar(ID id);
}
