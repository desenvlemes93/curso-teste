package com.ricardolemes.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardolemes.cursomc.domain.ItemPedido;
import com.ricardolemes.cursomc.domain.PagamentoComBoleto;
import com.ricardolemes.cursomc.domain.Pedido;
import com.ricardolemes.cursomc.domain.enums.EstadoPagamento;
import com.ricardolemes.cursomc.repositories.ItemPedidoRepository;
import com.ricardolemes.cursomc.repositories.PagamentoRepository;
import com.ricardolemes.cursomc.repositories.PedidoRepository;
import com.ricardolemes.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired 
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentorepository;
	
	 @Autowired
	 private ProdutoService produtoService;
	 
	 @Autowired
	 private ItemPedidoRepository itemPedidoRepository;
	 
	public Pedido buscar(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);		
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ",Tipo:"+ Pedido.class.getName()));		
		
	}

@Transactional
  public Pedido insert (Pedido obj) {
	  
	  obj.setId(null);
	  obj.setInstante(new Date());
	 obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
	 obj.getPagamento().setPedido(obj);
	 if (obj.getPagamento() instanceof PagamentoComBoleto) {
		 PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
		 boletoService.preenhcerPagamentoComBoleto(pagto, obj.getInstante());
	 } 
	 
	 obj = repo.save(obj);
	 pagamentorepository.save(obj.getPagamento()); 
	 
	 for (ItemPedido ip : obj.getItens()) {          
	   ip.setDesconto(0.0);
	   ip.setPreco(produtoService.buscar(ip.getProduto().getId()).getPreco());
	    ip.setPedido(obj); 
	 }
	  
	 itemPedidoRepository.saveAll(obj.getItens());
	 return obj;

}
}
