package com.techsolutions.rangofast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techsolutions.rangofast.model.Pedido;
import com.techsolutions.rangofast.model.enums.StatusPedido;

/**
 * Repositorio de Pedidos (Spring Data JPA).
 *
 * Demonstra FILTRO + PAGINACAO + ORDENACAO por status, cliente e restaurante.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("""
            SELECT p FROM Pedido p
            WHERE (:status IS NULL OR p.status = :status)
              AND (:clienteId IS NULL OR p.cliente.id = :clienteId)
              AND (:restauranteId IS NULL OR p.restaurante.id = :restauranteId)
            """)
    Page<Pedido> buscarComFiltros(
            @Param("status") StatusPedido status,
            @Param("clienteId") Long clienteId,
            @Param("restauranteId") Long restauranteId,
            Pageable pageable);
}
