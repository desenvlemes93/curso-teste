package com.ricardolemes.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {
	private static final long serialVersionUID = 1L;

  private List<FieldMessage> getErrors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);

	}
	
	public List<FieldMessage> getErrors(){
		  return getErrors;
	}
	
	public void addError( String fieldName, String messagem ) {
		getErrors.add(new FieldMessage(fieldName,messagem));
		
	}

}
