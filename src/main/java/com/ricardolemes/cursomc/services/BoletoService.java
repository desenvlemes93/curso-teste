package com.ricardolemes.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ricardolemes.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	
	 public void   preenhcerPagamentoComBoleto(PagamentoComBoleto pagto, Date InstanteDoPedido ) {
		 Calendar cal = Calendar.getInstance();		 
		 cal.setTime(InstanteDoPedido);
		 cal.add(cal.DAY_OF_MONTH, 7);		 
		 pagto.setDataPagamento(cal.getTime());	
		 
	 }
}
