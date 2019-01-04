package com.ricardolemes.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardolemes.cursomc.domain.Cliente;
import com.ricardolemes.cursomc.domain.ItemPedido;
import com.ricardolemes.cursomc.domain.PagamentoComBoleto;
import com.ricardolemes.cursomc.domain.Pedido;
import com.ricardolemes.cursomc.domain.enums.EstadoPagamento;
import com.ricardolemes.cursomc.repositories.ItemPedidoRepository;
import com.ricardolemes.cursomc.repositories.PagamentoRepository;
import com.ricardolemes.cursomc.repositories.PedidoRepository;
import com.ricardolemes.cursomc.security.UserSS;
import com.ricardolemes.cursomc.services.exception.AuthorizationException;
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

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	public Pedido buscar(Integer id) {

		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ",Tipo:" + Pedido.class.getName()));

	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.buscar(obj.getCliente().getId()));
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
			ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
			ip.setPreco((ip.getProduto().getPreco()));
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;

	}
	
	public Page<Pedido> findPage(Integer page, Integer LinesPerPage, String orderBy, String direction){	
		UserSS user = UserService.authenticated();
		 if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		 }
		
		PageRequest pageRequest = PageRequest.of(page, LinesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.buscar(user.getId());
		
		return repo.findByCliente(cliente, pageRequest); 
	}
}
