package com.ricardolemes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ricardolemes.cursomc.domain.Categoria;
import com.ricardolemes.cursomc.domain.Produto;
import com.ricardolemes.cursomc.repositories.CategoriaRepository;
import com.ricardolemes.cursomc.repositories.ProdutoRepository;
import com.ricardolemes.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;	 

	 @Autowired
	 private CategoriaRepository categoriaRepository;
	  
	 public Produto buscar(Integer id)  {
			
			Optional<Produto> obj = repo.findById(id);		
			return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ",Tipo:"+ Categoria.class.getName()));		
	 }
	 
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer LinesPerPage, String orderBy, String direction){		
		PageRequest pageRequest = PageRequest.of(page, LinesPerPage, Direction.valueOf(direction), orderBy); 
     
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
			
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias,pageRequest);
   
	}

}