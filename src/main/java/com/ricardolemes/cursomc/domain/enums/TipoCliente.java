package com.ricardolemes.cursomc.domain.enums;

import org.hibernate.boot.model.naming.IllegalIdentifierException;

public enum TipoCliente {

	PESSOAFISICA(1, "fiSICA"),
	PESSOAJURIDICA(2, "JURIDICA");
	
  private int cod;
  private String descricao;

  private TipoCliente(int cod, String descricao) {
	  this.cod = cod;
	  this.descricao = descricao; 
	  
  }
  
  public int getCod() {
   return cod;
  }
  
  public String getDescricao() {
	  return descricao;
  }
  
  public static TipoCliente toEnum(Integer cod) {
	  
	  if (cod == null) {
		  return null;
	  }
		  
	  for (TipoCliente x : TipoCliente.values()) {
		  if (cod.equals(x.getCod())) {
			  return x;
		  }
	  }
	  
	  throw new IllegalIdentifierException("Id inválido:"+ cod);
  }
   
}
