package com.ricardolemes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardolemes.cursomc.domain.Cidade;
import com.ricardolemes.cursomc.domain.Cliente;
import com.ricardolemes.cursomc.domain.Endereco;
import com.ricardolemes.cursomc.domain.enums.Perfil;
import com.ricardolemes.cursomc.domain.enums.TipoCliente;
import com.ricardolemes.cursomc.dto.ClienteDTO;
import com.ricardolemes.cursomc.dto.ClienteNewDTO;
import com.ricardolemes.cursomc.repositories.ClienteRepository;
import com.ricardolemes.cursomc.repositories.EnderecoRepository;
import com.ricardolemes.cursomc.resources.exception.DataIntegrityException;
import com.ricardolemes.cursomc.security.UserSS;
import com.ricardolemes.cursomc.services.exception.AuthorizationException;
import com.ricardolemes.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	 
	public Cliente buscar(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);		
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ",Tipo:"+ Cliente.class.getName()));		
		
	}

	@Transactional
	public Cliente insert(Cliente obj) {
	  obj.setId(null);
	  obj = repo.save(obj);
	  enderecoRepository.saveAll(obj.getEnderecos());
		
    	return	obj;
		
	}
	
	
	public Cliente update (Cliente obj) {
		Cliente newObj = buscar(obj.getId());
         upateData(newObj,obj);
		return repo.save(newObj);
		
	}
	
	public void delete (Integer id) {
		buscar(id);
		try {
		
		repo.deleteById(id);
		
		} catch(DataIntegrityViolationException e) {
		
			throw new DataIntegrityException("Não foi possivel excluir uma Cliente que possua pedidos vinculados");		  
		}		
	}

	public List<Cliente> findAll(){
	  return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer LinesPerPage, String orderBy, String direction){		
		PageRequest pageRequest = PageRequest.of(page, LinesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest) ;
	}
	
	
	public Cliente fromDTO(ClienteDTO objDto) {
     	return new  Cliente(objDto.getId(),objDto.getNome(), objDto.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfouPJ(), TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(),null , null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(),objDto.getNumero(),objDto.getComplemento(), objDto.getBairro(),objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        
        if (objDto.getTelefone2() != null) {
        	 cli.getTelefones().add(objDto.getTelefone2());
        	
        }
        
        if (objDto.getTelefone3() != null) {
       	 cli.getTelefones().add(objDto.getTelefone3());
       	
       }
        
      return cli;  
		
	}
	
	private void upateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
