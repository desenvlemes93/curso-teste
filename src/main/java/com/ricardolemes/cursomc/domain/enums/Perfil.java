package com.ricardolemes.cursomc.domain.enums;

import org.hibernate.boot.model.naming.IllegalIdentifierException;

public enum Perfil {

	ADMIN(1, "ROLE_ADM"),
	CLIENTE(2, "ROLE_CLIENTE");
	
  private int cod;
  private String descricao;

  private Perfil(int cod, String descricao) {
	  this.cod = cod;
	  this.descricao = descricao; 
	  
  }
  
  public int getCod() {
   return cod;
  }
  
  public String getDescricao() {
	  return descricao;
  }
  
  public static Perfil toEnum(Integer cod) {
	  
	  if (cod == null) {
		  return null;
	  }
		  
	  for (Perfil x : Perfil.values()) {
		  if (cod.equals(x.getCod())) {
			  return x;
		  }
	  }
	  
	  throw new IllegalIdentifierException("Id inválido:"+ cod);
  }
   
}
