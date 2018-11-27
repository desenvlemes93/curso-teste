package com.ricardolemes.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ricardolemes.cursomc.domain.Categoria;
import com.ricardolemes.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends  JpaRepository<Produto,Integer> {
	
	@Transactional(readOnly=true)
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest) ;	 
	

}
