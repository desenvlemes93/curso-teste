package com.ricardolemes.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricardolemes.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends  JpaRepository<Pedido,Integer> {
	
	
	

}
