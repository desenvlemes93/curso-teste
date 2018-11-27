package com.ricardolemes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.ricardolemes.cursomc.domain.Categoria;
import com.ricardolemes.cursomc.domain.Cliente;
import com.ricardolemes.cursomc.dto.CategoriaDTO;
import com.ricardolemes.cursomc.repositories.CategoriaRepository;
import com.ricardolemes.cursomc.resources.exception.DataIntegrityException;
import com.ricardolemes.cursomc.services.exception.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	 
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);		
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ",Tipo:"+ Categoria.class.getName()));		
	}

	@PostMapping
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		
    	return	repo.save(obj);
		
	}
	
	public Categoria update (Categoria obj) {
		
	  Categoria newObj = buscar(obj.getId());
	  UpdateData(newObj,obj);
		return repo.save(newObj);
		
	}
	
	public void delete (Integer id) {
		buscar(id);
		try {
		
		repo.deleteById(id);
		
		} catch(DataIntegrityViolationException e) {
		
			throw new DataIntegrityException("Não foi possivel excluir uma Categoria que possua produtos vinculados");		  
		}		
	}

	public List<Categoria> findAll(){
	  return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer LinesPerPage, String orderBy, String direction){		
		PageRequest pageRequest = PageRequest.of(page, LinesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest) ;
	}
	
	
	public Categoria fromDTO(CategoriaDTO objDto) {
     	return new Categoria(objDto.getId(),objDto.getNome());
	}
	
	private void UpdateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
		
	}
}
