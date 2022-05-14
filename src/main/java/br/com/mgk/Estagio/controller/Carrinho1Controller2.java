package br.com.mgk.Estagio.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.plaf.synth.SynthSeparatorUI;

import br.com.mgk.Estagio.Repository.ClienteRepository;
import br.com.mgk.Estagio.Repository.PedidoItensRepository;
import br.com.mgk.Estagio.Repository.PedidoRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.Repository.PromocaoItensRepository;
import br.com.mgk.Estagio.Repository.PromocaoRepository;
import br.com.mgk.Estagio.model.Cliente;
import br.com.mgk.Estagio.model.Fretenator;
import br.com.mgk.Estagio.model.FretenatorResult;
import br.com.mgk.Estagio.model.FretenatorResultItem;
import br.com.mgk.Estagio.model.Pedido;
import br.com.mgk.Estagio.model.PedidoItens;
import br.com.mgk.Estagio.model.Produto;
import br.com.mgk.Estagio.model.Promocao;
import br.com.mgk.Estagio.model.PromocaoItens;

import org.hibernate.criterion.BetweenExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

@Controller
public class Carrinho1Controller2 {
	private List<PedidoItens> pedidosItens = new ArrayList<PedidoItens>();
	private List<Pedido> listapedidonova= new ArrayList<Pedido>();
	private List<PromocaoItens> promocaoItens = new ArrayList<PromocaoItens>();
	private List<Promocao> promocoes = new ArrayList<Promocao>();
	private Pedido pedido = new Pedido();
	private Cliente cliente;
	private String cep= " ";
	private long modo= new Long(0);
	private Double altura, largura,comprimento;
	private int prazo= 0;
	private String peso, mensagem = " ";
	private boolean erro= false;
	private double fretefim = 0.0;
	private double total = 0.0;
	private String id;
	@Autowired
	private ProdutoRepository produtoRepositorio;
	
	private List<Produto> lista = new ArrayList();

	@Autowired
	private ProdutoRepository repositorioProduto;

	@Autowired
	private ClienteRepository repositorioCliente;

	@Autowired
	private PedidoRepository pedidorepositorio;
	@Autowired
	private PromocaoItensRepository promocaoitensRepository;
	
	@Autowired
	private PromocaoRepository promocaoRepository;
	@Autowired
	private PedidoItensRepository PedidosItensrepositorio;
	private Boolean ex;

	
	private double calcularTotal() {
		double total=0;
		pedido.setValorTotal(0.0);
		for (PedidoItens it : pedidosItens) {
			pedido.setValorTotal(pedido.getValorTotal() + (it.getValorUnitario() * it.getQuantidade()));
			total += pedido.getValorTotal();
		}
		this.total = total;
		return total;
	}
	
	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String email = autenticado.getName();
			
			cliente = repositorioCliente.buscarClienteEmail(email).get(0);
		}
	}
	
	@GetMapping(value = "/modo") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public void  posfun(@RequestParam(name = "modo") Long modo) { /* Recebe os dados para consultar */
		this.modo=modo;
		}

	@GetMapping("/pagamento")
	public ModelAndView pagamentoCompra() {
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("cartao");
	
		// System.out.println(compra.getValorTotal());
		return mv;
	}
	
	@GetMapping("/clilogout")
	@ResponseBody
	public ModelAndView clientelogout() {
		
		SecurityContextHolder.clearContext();
	
		ModelAndView mv = new ModelAndView("/carrinho");
		calcularTotal();
		// System.out.println(compra.getValorTotal());
		mv.addObject("pedido", pedido);
		mv.addObject("listaItens", pedidosItens);
		return mv;
	}
	
	@GetMapping("/pedidoaltera/{id}/{val}")
	public ModelAndView pedidoaltera(@PathVariable Long id, @PathVariable String val) {
			List<Pedido> usuario = pedidorepositorio.findAll();
			Pedido pedidonovo = new Pedido();
			pedidonovo = pedidorepositorio.getById(id);
			if(!val.equals("Cancelado"))
			{
				if(val.equals("Em preparacao"))
					val = "Enviando entrega";
				else
				if(val.equals("Enviando entrega"))
					val = "Enviado";
				else val = "Finalizado";
			}
			pedidonovo.setStatus(val);
			pedidorepositorio.saveAndFlush(pedidonovo);
			Cliente cli = new Cliente();
			ModelAndView mv = new ModelAndView("pedidos");
			mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido", usuario);
			mv.addObject("listaItens", pedidosItens);
			mv.addObject("cliente", cli);
			mv.addObject("valor",fretefim );
			mv.addObject("Totalvenda", fretefim + this.total);
			return mv;
	}
	
	@GetMapping("/pedidoalteratira/{id}/{val}")
	public ModelAndView pedidoalteratira(@PathVariable Long id, @PathVariable String val) {
			List<Pedido> usuario = pedidorepositorio.findAll();
			Pedido pedidonovo = new Pedido();
			pedidonovo = pedidorepositorio.getById(id);
			if(!val.equals("Cancelado"))
			{
				if(val.equals("Enviando entrega"))
					val = "Em preparacao";
				else
				if(val.equals("Enviado"))
					val = "Enviando entrega";
				if(val.equals("Finalizado"))
					val = "Enviado";
			}
			pedidonovo.setStatus(val);
			pedidorepositorio.saveAndFlush(pedidonovo);
			Cliente cli = new Cliente();
			ModelAndView mv = new ModelAndView("pedidos");
			mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido", usuario);
			mv.addObject("listaItens", pedidosItens);
			mv.addObject("cliente", cli);
			mv.addObject("valor",fretefim );
			mv.addObject("Totalvenda", fretefim + this.total);
			return mv;
	}
	
	@GetMapping("/cancela/{id}")
	public ModelAndView cancela(@PathVariable Long id) {
			List<Pedido> usuario = pedidorepositorio.findAll();
			Pedido pedidonovo = new Pedido();
			pedidonovo = pedidorepositorio.getById(id);
			pedidonovo.setStatus("Cancelado");
			pedidorepositorio.saveAndFlush(pedidonovo);
			Cliente cli = new Cliente();
			ModelAndView mv = new ModelAndView("pedidos");
			mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido", usuario);
			mv.addObject("listaItens", pedidosItens);
			mv.addObject("cliente", cli);
			mv.addObject("valor",fretefim );
			mv.addObject("Totalvenda", fretefim + this.total);
			return mv;
	}
	
	@GetMapping("/pedidorelparamcli/{id}")
	public ModelAndView pedidorelparamcli(@PathVariable Long id) {
			Pedido pedidonovo;
			List<PedidoItens> pedidositens = PedidosItensrepositorio.buscarPorPedido(id);
			pedidonovo = pedidorepositorio.getById(id);
	
			ModelAndView mv = new ModelAndView("pedidocliente");
			mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido", listapedidonova);
			mv.addObject("cliente", pedidonovo.getCliente());
			mv.addObject("valor",pedido.getFrete() );
			mv.addObject("Totalvenda",pedido.getValorTotal());
			// System.out.println(compra.getValorTotal());
			return mv;
	}
	@GetMapping("/pedidocliente")
	public ModelAndView pedidocliente(){
		buscarUsuarioLogado();
		List<Pedido> usuario = pedidorepositorio.findAll();
		List<Pedido> novo = new ArrayList<>();
		for (Pedido pedido : usuario) {
			if(pedido.getCliente()==cliente)
				novo.add(pedido);
		}
		listapedidonova = novo;
		Pedido pedidonovo = new Pedido();
		Cliente cliente = new Cliente();
		cliente.setNome("Pedidos");
		pedidonovo.setCliente(cliente);
		ModelAndView mv = new ModelAndView("pedidocliente");
		mv.addObject("pedido", pedidonovo);
		mv.addObject("listaPedido", listapedidonova);
		mv.addObject("cliente", pedidonovo.getCliente());
		mv.addObject("valor",pedido.getFrete() );
		mv.addObject("Totalvenda",pedido.getValorTotal());
		// System.out.println(compra.getValorTotal());
		return mv;
}
	
	@GetMapping("/pedidook")
	public ModelAndView pedidook() {

			ModelAndView mv = new ModelAndView("Confirma");
			mv.addObject("pedido", pedido);
			mv.addObject("listaItens", pedidosItens);
			mv.addObject("cliente", cliente);
			mv.addObject("valor",fretefim );
			mv.addObject("Totalvenda", fretefim + this.total);
			// System.out.println(compra.getValorTotal());
			LocalDate date = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			pedido.setDataCompra(date.format(formatter));
			pedido.setFrete(fretefim);
			//pedido.setValorTotal(this.total);
			pedido.setCliente(cliente);
			pedido.setStatus("Em preparacao");
			pedidorepositorio.save(pedido);
			for (PedidoItens item : pedidosItens) {
				item.setPedido(pedido);
				PedidosItensrepositorio.save(item);
				Optional <Produto> prod = produtoRepositorio.findById(item.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()-item.getQuantidade());
				produtoRepositorio.saveAndFlush(produto);
			}
			SecurityContextHolder.clearContext();
			pedido = new Pedido();
			pedidosItens =  new ArrayList<PedidoItens>();
			cliente = new Cliente();
			
			return mv;
	}
	
	@GetMapping("/pedidorel")
	public ModelAndView pedidorel() {
			List<Pedido> usuario = pedidorepositorio.findAll();
			Pedido pedidonovo = new Pedido();
			Cliente cli = new Cliente();
			for (Pedido pedido : usuario) {
				if(cli==null)
				cli = pedido.getCliente();
			}
			ModelAndView mv = new ModelAndView("pedidos");
			mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido", usuario);
			mv.addObject("listaItens", pedidosItens);
			mv.addObject("cliente", cli);
			mv.addObject("valor",fretefim );
			mv.addObject("Totalvenda", fretefim + this.total);
			// System.out.println(compra.getValorTotal());
			return mv;
	}
	
	@GetMapping("/pedidorelparam/{id}")
	public ModelAndView pedidorelparam(@PathVariable Long id) {
			List<Pedido> usuario = pedidorepositorio.findAll();
			Pedido pedidonovo;
			List<PedidoItens> pedidositens = PedidosItensrepositorio.buscarPorPedido(id);
			pedidonovo = pedidorepositorio.getById(id);
	
			ModelAndView mv = new ModelAndView("pedidos");
			mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido", usuario);
			mv.addObject("listaItens", pedidositens);
			mv.addObject("cliente", pedidonovo.getCliente());
			mv.addObject("valor",pedido.getFrete() );
			mv.addObject("Totalvenda",pedido.getValorTotal());
			// System.out.println(compra.getValorTotal());
			return mv;
	}
	@GetMapping("/pedidoreldata/{data}")
	public ModelAndView pedidorelparam(@PathVariable String data) {
			List<Pedido> usuario = pedidorepositorio.findAll();
			List<Pedido> nova = new ArrayList<Pedido>();
			for (Pedido pedido : usuario) {
				if(pedido.getDataCompra().equals(data))
					nova.add(pedido);
			}
			Pedido pedidonovo = new Pedido();
			List<PedidoItens> pedidositens = new ArrayList<>();
			ModelAndView mv = new ModelAndView("pedidos");
		//	mv.addObject("pedido", pedidonovo);
			mv.addObject("listaPedido",nova );
		//	mv.addObject("listaItens", pedidositens);
		//	mv.addObject("cliente", pedidonovo.getCliente());
		//	mv.addObject("valor",pedido.getFrete() );
		//	mv.addObject("Totalvenda",pedido.getValorTotal());
			// System.out.println(compra.getValorTotal());
			return mv;
	}
	@GetMapping("/finalizar")
	public ModelAndView finalizarCompra() {
	
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("finalizar");
		// System.out.println(compra.getValorTotal());
		
		
		mv.addObject("listaItens", pedidosItens);
		mv.addObject("cliente", cliente);
		calcularfrete(cliente.getCep()); 
		mv.addObject("prazo", prazo);
		mv.addObject("valor",fretefim );	
		pedido.setValorTotal(this.total);
		pedido.setValorFinal(this.total+fretefim-pedido.getDesconto());
		mv.addObject("Totalvenda", fretefim + this.total);
		mv.addObject("pedido", pedido);
		return mv;
	}
	@GetMapping("/frete")
	public ModelAndView fretea() {
		ModelAndView mv = new ModelAndView("calcularfrete");
		return mv;
	}
	
	public void calcularfrete(String cep) {
		fretefim = 0.0;
		ModelAndView mv = new ModelAndView("calcularfrete");
		calcularTotal();
		long teste = -1;
		// System.out.println(compra.getValorTotal());
		Fretenator fretenator = new Fretenator();
		fretenator.codServico("41106");
		fretenator.cepOrigem("19050000");
		fretenator.cepDestino(cep);
		FretenatorResult result = null; 
		FretenatorResultItem servico = null;
		if(cep!= "")
		 for (PedidoItens item : pedidosItens) {
			 	if(item.getProduto().getId() !=  teste)
			 	{
					fretenator.codFormato(item.getProduto().getCodFormato());
					fretenator.altura(item.getProduto().getAltura());
					fretenator.largura(item.getProduto().getLargura());
					fretenator.comprimento(item.getProduto().getComprimento());
					fretenator.peso(item.getProduto().getPeso());
					result = fretenator.result();
					servico = result.getServico(41106);
					
					fretefim += servico.getValor();
					erro = servico.getErro();
					mensagem.equals(servico.getMensagemDeErro());
					prazo = servico.getPrazo();
			 	}
			 	teste = item.getProduto().getId();
		} 
	
	}
	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho() {
		ModelAndView mv = new ModelAndView("/carrinho");
		calcularTotal();
		// System.out.println(compra.getValorTotal());
		mv.addObject("pedido", pedido);
		mv.addObject("listaItens", pedidosItens);
		return mv;
	}
	
	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
		ModelAndView mv = new ModelAndView("/carrinho");

		int a =0;
		for (PedidoItens it : pedidosItens) {
			if (it.getProduto().getId() == id) {
				
				// System.out.println(it.getValorTotal());
				if (acao.equals(1) && it.getProduto().getQuantidadeEstoque()> it.getQuantidade()) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					calcularTotal();
				} else if (acao == 0 && it.getQuantidade() != 1) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					calcularTotal();
				}
				a++;
			}
		}

		return "redirect:/carrinho";
	}

	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Long id) {

		for (PedidoItens it : pedidosItens) {
			if (it.getProduto().getId() == (id)) {
				pedidosItens.remove(it);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				pedido.setDesconto(pedido.getDesconto()-it.getDesconto());
				break;
			}
		}

		return "redirect:/carrinho";
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public String adicionarCarrinho(@PathVariable Long id) {
		PedidoItens item = new PedidoItens();
		Optional<Produto> prod = repositorioProduto.findById(id);
		Produto produto = prod.get();

		
		promocoes = promocaoRepository.findAll();
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			for (Promocao promo : promocoes) {
				//comparar se a data esta entre a data de hoje
		        LocalDate d1 = LocalDate.parse(promo.getDtini());
		        LocalDate d2 = LocalDate.parse(promo.getDtfim());
				if((d1.isEqual(date)|| d1.isBefore(date)) && (d2.isEqual(date) || d2.isAfter(date)))
				{
					promocaoItens = promocaoitensRepository.buscarPorPromocao(promo.getId());
					for (PromocaoItens up : promocaoItens) {
						if(produto.getId()==up.getProduto().getId())
						{
							if(item.getDesconto()==null)
								item.setDesconto(0.0);
							pedido.setDesconto(pedido.getDesconto()+up.getDesconto());
							item.setDesconto(up.getDesconto());
						}
					}
				}
			}

		int controle = 0;
		for (PedidoItens it : pedidosItens) {

			if (it.getProduto().getId() == produto.getId() && it.getProduto().getQuantidadeEstoque()> it.getQuantidade()) {
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
				String valorFormatado = new DecimalFormat("0.##").format(it.getValorTotal());
				it.setValorTotal(Double.valueOf(valorFormatado).doubleValue()-it.getDesconto());
			}
			else 
			if((it.getProduto().getId() == produto.getId() && it.getProduto().getQuantidadeEstoque()==it.getQuantidade()))
				controle=2;	
		}
		if (controle == 0 && produto.getQuantidadeEstoque()>=0) {
			
			item.setProduto(produto);
			item.setValorUnitario(produto.getPreco());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
			String valorFormatado = new DecimalFormat("0.##").format(item.getValorTotal());
			item.setValorTotal(Double.valueOf(valorFormatado).doubleValue()-item.getDesconto());
			pedidosItens.add(item);
		}
		controle = 0;
		return "redirect:/carrinho";
	}

	
	
	
	@RequestMapping(value ="/inicio-tcc")
	public ModelAndView marca() {
		List<Produto> nova = new ArrayList();
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lista = produtoRepositorio.findAll();
		
	//promocoes = promocaoRepository.findAll();
	/*for (Produto produto : lista) {
		
		for (Promocao promo : promocoes) {
			//comparar se a data esta entre a data de hoje
	        LocalDate d1 = LocalDate.parse(promo.getDtini());
	        LocalDate d2 = LocalDate.parse(promo.getDtfim());
			if((d1.isEqual(date)|| d1.isBefore(date)) && (d2.isEqual(date) || d2.isAfter(date)))
			{
				promocaoItens = promocaoitensRepository.buscarPorPromocao(promo.getId());
			for (PromocaoItens up : promocaoItens) {
				if(produto.getId()==up.getProduto().getId())
				{
					//produto.setPreco(up.getPreconovo());
					valorFormatado = new DecimalFormat("0.##").format(up.getPreconovo());
					produto.setPreco(Double.valueOf(valorFormatado).doubleValue());
				}
			}	
			}
		}
		nova.add(produto);
	}
	lista = nova;*/
		ModelAndView mv = new ModelAndView("tcc-joao");	
			mv.addObject("listaProdutos", produtoRepositorio.findAll());
			mv.addObject("carrinqnt", pedidosItens.size());
		return mv;
	}
	
	@RequestMapping(value ="/rodarel")
	public ModelAndView roda() {
		List<Produto> listanova = new ArrayList();
		ModelAndView mv = new ModelAndView("relatoriosRodas");		
		for(Produto produto : lista)
		{
			if(produto.getCategoria() == 37)
				listanova.add(produto);		 
		}
			mv.addObject("listaProdutos", listanova);
			mv.addObject("carrinqnt", pedidosItens.size());
		return mv;
	}
	
	@RequestMapping(value ="/motorrel")
	public ModelAndView motor() {
		List<Produto> listanova = new ArrayList();
		ModelAndView mv = new ModelAndView("relatoriosRodas");		
		for(Produto produto : lista)
		{
			if(produto.getCategoria() == 38)
				listanova.add(produto);		 
		}
		mv.addObject("listaProdutos", listanova);
		mv.addObject("carrinqnt", pedidosItens.size());	return mv;
	}
	@RequestMapping(value ="/bateriarel")
	public ModelAndView bateria() {
		List<Produto> listanova = new ArrayList();
		ModelAndView mv = new ModelAndView("relatoriosRodas");		
		for(Produto produto : lista)
		{
			if(produto.getCategoria() == 39)
				listanova.add(produto);		 
		}
		mv.addObject("listaProdutos", listanova);
		mv.addObject("carrinqnt", pedidosItens.size());	return mv;
	}
	
	@RequestMapping(value ="/suspensaorel")
	public ModelAndView suspensao() {
		List<Produto> listanova = new ArrayList();	
		ModelAndView mv = new ModelAndView("relatoriosRodas");		
		for(Produto produto : lista)
		{
			if(produto.getCategoria() == 40)
				listanova.add(produto);		 
		}
		mv.addObject("listaProdutos", listanova);
		mv.addObject("carrinqnt", pedidosItens.size());	return mv;
	}
	
	@RequestMapping(value ="/novalista")
	public ModelAndView novalista() {
		
		ModelAndView mv = new ModelAndView("relatoriosRodas");	
		List<Produto> usuario = produtoRepositorio.buscarPorNome(id.trim().toUpperCase());
		mv.addObject("listaProdutos", usuario);
		mv.addObject("carrinqnt", pedidosItens.size());	
		return mv;
	}
	@GetMapping(value = "getcep") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public void  posfun(@RequestParam(name = "cep") String cep) { /* Recebe os dados para consultar */
		this.cep=cep;
	}
	
	@GetMapping(value = "getcartao") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public void  poscartao(@RequestParam(name = "cartao") String cartao) { /* Recebe os dados para consultar */
		pedido.setNumerocartao(cartao);
	}
	
	@GetMapping(value = "buscarPorPedidoNovo") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Pedido>>  buscarPorPedido() { /* Recebe os dados para consultar */
		
		List<Pedido> usuario = pedidorepositorio.findAll();

		return new ResponseEntity<List<Pedido>>(usuario, HttpStatus.OK);
	}
	
	@GetMapping(value = "buscarPorPromocaoFiltro") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Pedido>>  buscarPorCompraFiltro(@RequestParam(name = "datalol") String  datalol) { /* Recebe os dados para consultar */
		List<Pedido> nova =  new ArrayList<Pedido>();
		List<Pedido> usuario = pedidorepositorio.findAll();
		for (Pedido pedido : usuario) {	
			if(pedido.getDataCompra().contains(datalol)) {
				nova.add(pedido);
			}		
		}

		return new ResponseEntity<List<Pedido>>(nova, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "buscarListaItempedido") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<PedidoItens>>  buscarItens(@RequestParam(name = "idcompra") Long idcompra) { /* Recebe os dados para consultar */
		
		List<PedidoItens> lista = PedidosItensrepositorio.buscarPorPedido(idcompra);
		this.pedidosItens = lista;
		return new ResponseEntity<List<PedidoItens>>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "BUSCAPORLETRA") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public void BUSCAPORLETRA(@RequestParam(name = "id") String id) { /* Recebe os dados para consultar */
		this.id = id;
	}	
	
	

@GetMapping(value = "pedido/resultexnovo") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void  resultex(@RequestParam(name = "ex") Boolean ex) { /* Recebe os dados para consultar */
	this.ex=ex;
	}
}
