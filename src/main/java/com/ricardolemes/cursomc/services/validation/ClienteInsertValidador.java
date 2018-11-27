package com.ricardolemes.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ricardolemes.cursomc.domain.Cliente;
import com.ricardolemes.cursomc.domain.enums.TipoCliente;
import com.ricardolemes.cursomc.dto.ClienteNewDTO;
import com.ricardolemes.cursomc.repositories.ClienteRepository;
import com.ricardolemes.cursomc.resources.exception.FieldMessage;
import com.ricardolemes.cursomc.services.validation.utils.BR;
public class ClienteInsertValidador implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
 @Override
 public void initialize(ClienteInsert ann) {
 
 }
 
 @Override
 public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
 
	 List<FieldMessage> list = new ArrayList<>();
 
	if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfouPJ())) {
	  
		 list.add(new FieldMessage("cpfouPJ", "CPF inválido"));

	}

	if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfouPJ())) {
  
			 list.add(new FieldMessage("cpfouPJ", "CNPJ inválido"));

	}
	  Cliente aux  = repo.findByEmail(objDto.getEmail());
	  
	  if (aux != null) {
		  list.add(new FieldMessage("email", "Email já existente"));
	  }
 
		 for (FieldMessage e : list) {
    		 context.disableDefaultConstraintViolation();
	     	 context.buildConstraintViolationWithTemplate(e.getMessage())
		      .addPropertyNode(e.getFieldName()).addConstraintViolation();
		 }
		   return list.isEmpty();
		 }
		}
 