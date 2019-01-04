package com.ricardolemes.cursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ricardolemes.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentocomCartao")
public class PagamentocomCartao extends Pagamento {
	private static final long serialVersionUID = 1L;

	private Integer numerodeParcelas;

	public PagamentocomCartao() {

	}

	public PagamentocomCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numerodeParcelas) {
		super(id, estado, pedido);

		this.numerodeParcelas = numerodeParcelas;
	}

	public Integer getNumerodeParcelas() {
		return numerodeParcelas;
	}

	public void setNumerodeParcelas(Integer numerodeParcelas) {
		this.numerodeParcelas = numerodeParcelas;
	}

}
