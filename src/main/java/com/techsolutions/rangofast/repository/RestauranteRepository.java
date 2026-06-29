package com.techsolutions.rangofast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techsolutions.rangofast.model.Restaurante;
import com.techsolutions.rangofast.model.enums.CategoriaRestaurante;

/**
 * Repositorio de Restaurantes (Spring Data JPA - INTERFACE).
 *
 * Demonstra FILTRO + PAGINACAO + ORDENACAO: a consulta aceita parametros
 * opcionais (nome, categoria, ativo) e recebe um {@link Pageable} que
 * carrega informacoes de pagina, tamanho e ordenacao.
 */
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query("""
            SELECT r FROM Restaurante r
            WHERE (:nome IS NULL OR LOWER(r.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
              AND (:categoria IS NULL OR r.categoria = :categoria)
              AND (:ativo IS NULL OR r.ativo = :ativo)
            """)
    Page<Restaurante> buscarComFiltros(
            @Param("nome") String nome,
            @Param("categoria") CategoriaRestaurante categoria,
            @Param("ativo") Boolean ativo,
            Pageable pageable);
}
