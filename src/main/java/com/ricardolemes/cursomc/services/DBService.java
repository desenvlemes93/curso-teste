package com.ricardolemes.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardolemes.cursomc.domain.Categoria;
import com.ricardolemes.cursomc.domain.Cidade;
import com.ricardolemes.cursomc.domain.Cliente;
import com.ricardolemes.cursomc.domain.Endereco;
import com.ricardolemes.cursomc.domain.Estado;
import com.ricardolemes.cursomc.domain.ItemPedido;
import com.ricardolemes.cursomc.domain.Pagamento;
import com.ricardolemes.cursomc.domain.PagamentoComBoleto;
import com.ricardolemes.cursomc.domain.PagamentocomCartao;
import com.ricardolemes.cursomc.domain.Pedido;
import com.ricardolemes.cursomc.domain.Produto;
import com.ricardolemes.cursomc.domain.enums.EstadoPagamento;
import com.ricardolemes.cursomc.domain.enums.TipoCliente;
import com.ricardolemes.cursomc.repositories.CategoriaRepository;
import com.ricardolemes.cursomc.repositories.CidadeRepository;
import com.ricardolemes.cursomc.repositories.ClienteRepository;
import com.ricardolemes.cursomc.repositories.EnderecoRepository;
import com.ricardolemes.cursomc.repositories.EstadoRepository;
import com.ricardolemes.cursomc.repositories.ItemPedidoRepository;
import com.ricardolemes.cursomc.repositories.PagamentoRepository;
import com.ricardolemes.cursomc.repositories.PedidoRepository;
import com.ricardolemes.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	
	@Autowired
	  private CategoriaRepository categoriaRepository; 
	
	@Autowired
	private ProdutoRepository produtoRepository;
	  
	@Autowired
	private CidadeRepository cidadesRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienterepository;
	
	@Autowired
	private EnderecoRepository enderecorepository;
	
	@Autowired	
	 private PedidoRepository pedidorepository;
	
	@Autowired	
	 private PagamentoRepository pagamentorepository;
	@Autowired
	private ItemPedidoRepository itemPedidorepository;

    public void instatiateTestDatabase() throws ParseException {	
	
	  Categoria  cat1 = new Categoria(null,"MAQUIAGEM");
	  Categoria  cat2 = new Categoria(null,"Bijuteria");
	  Categoria  cat3 = new Categoria(null,"MASSAGEM");
	  Categoria  cat4 = new Categoria(null,"Bijuteria");
	  Categoria  cat5 = new Categoria(null,"MASSAGEM");
	  Categoria  cat6 = new Categoria(null,"Bijuteria");
	  Categoria  cat7 = new Categoria(null,"MASSAGEM");
	  Categoria  cat8 = new Categoria(null,"Bijuteria");
	  Categoria  cat9 = new Categoria(null,"MASSAGEM");
	  
	  
			
		Produto produto1 = new Produto(null,"Base",150.00);
		Produto produto2 = new Produto(null,"Brinco",300.00);
		Produto produto3 = new Produto(null,"Sei lá",80.00);
		 
		cat1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		cat2.getProdutos().addAll(Arrays.asList(produto3));
		 
		produto1.getCategorias().addAll(Arrays.asList(cat1));
		produto2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		produto3.getCategorias().addAll(Arrays.asList(cat1));
		produto3.getCategorias().addAll(Arrays.asList(cat3));
		
		  categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3,cat4,cat5,cat6,cat7,cat8,cat9));		
		  produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
		
		
		
		Estado est1 = new Estado(null,"Goias");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "GOIANIA", est1);
		Cidade cid2 = new Cidade(null,"Itaberai", est1);
		Cidade cid3 = new Cidade(null,"Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1 , cid2));
		est2.getCidades().addAll(Arrays.asList(cid3)); 	 
	  
  
	  estadoRepository.saveAll(Arrays.asList(est1 , est2));
	  cidadesRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
	
		
	  Cliente cliente1 = new Cliente(null,"Joao Da silva","joaosilva@gmail.com","12345",TipoCliente.PESSOAFISICA);
	  Cliente cliente2 = new Cliente(null,"Eu da SIlva Sauro","Euquequis@gmail.com","45698",TipoCliente.PESSOAJURIDICA);
	  cliente1.getTelefones().addAll(Arrays.asList("1111111111111","43254325354"));
	  
	  Endereco e1 = new Endereco(null, "TESTE", "50", "EU QUE QUIS", "AAAAAAAAAAAA", "DSLJFSKDLJF", cliente1, cid1);
	  Endereco e2 = new Endereco(null, "amor", "90", "U QUE QUIS", "AAAAAAAAAAAA", "DSLJFSKDLJF", cliente2, cid2);
	  
	  
	  cliente1.getEnderecos().addAll(Arrays.asList(e2,e1));
	  
	  clienterepository.saveAll(Arrays.asList(cliente1,cliente2));
	  enderecorepository.saveAll(Arrays.asList(e1,e2));
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	  
	  
	  Pedido pedi = new Pedido(null, sdf.parse("17/09/2018 22:48"), cliente1, e1);
	  Pedido pedi2 = new Pedido(null, sdf.parse("06/09/2018 00:00"), cliente1, e2);
	  
	  
	  Pagamento pagto1 = new PagamentocomCartao(null,EstadoPagamento.QUITADO,pedi, 6);
	  pedi.setPagamento(pagto1);
	  Pagamento pagto2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE,pedi2, sdf.parse("20/10/2017 12:12"), null);
	  pedi2.setPagamento(pagto2);
	  
	  cliente1.getPedidos().addAll(Arrays.asList(pedi, pedi2));
	  
	  pedidorepository.saveAll(Arrays.asList(pedi, pedi2));
	  
	  pagamentorepository.saveAll(Arrays.asList(pagto1));
	  
	  
	  ItemPedido ip1 = new ItemPedido(pedi, produto1,0.00,1,200.00);
	  ItemPedido ip2 = new ItemPedido(pedi, produto3,0.00,2,80.00);
	  ItemPedido ip3 = new ItemPedido(pedi2, produto2,100.00,1, 800.00);
	  
	  pedi.getItens().addAll(Arrays.asList(ip1, ip2));
	  pedi2.getItens().addAll(Arrays.asList(ip3));
	  
	  produto1.getItens().addAll(Arrays.asList(ip3));
	  produto2.getItens().addAll(Arrays.asList(ip2));	  
	  
	  itemPedidorepository.saveAll(Arrays.asList(ip1,ip2,ip3));  
	  
    }
}
