package com.techsolutions.rangofast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techsolutions.rangofast.model.Entregador;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}
