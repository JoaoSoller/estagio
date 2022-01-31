package br.com.mgk.Estagio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.swing.plaf.synth.SynthSeparatorUI;

import br.com.mgk.Estagio.Repository.ClienteRepository;
import br.com.mgk.Estagio.Repository.PedidoItensRepository;
import br.com.mgk.Estagio.Repository.PedidoRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.model.Cliente;
import br.com.mgk.Estagio.model.Pedido;
import br.com.mgk.Estagio.model.PedidoItens;
import br.com.mgk.Estagio.model.Produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarrinhoController {
	private List<PedidoItens> pedidosItens = new ArrayList<PedidoItens>();
	private Pedido pedido = new Pedido();
	private Cliente cliente;
	
	@Autowired
	private ProdutoRepository repositorioProduto;

	@Autowired
	private ClienteRepository repositorioCliente;

	@Autowired
	private PedidoRepository repositorioCompra;

	@Autowired
	private PedidoItensRepository repositorioPedidosItens;

	private void calcularTotal() {
		pedido.setValorTotal(0.0);
		for (PedidoItens it : pedidosItens) {
			pedido.setValorTotal(pedido.getValorTotal() + (it.getValorUnitario() * it.getQuantidade()));
		}
	}
	
	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String email = autenticado.getName();
			cliente = repositorioCliente.buscarClienteEmail(email).get(0);
		}
	}
	@GetMapping("/finalizar")
	public ModelAndView finalizarCompra() {
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("finalizar");
		calcularTotal();
		// System.out.println(compra.getValorTotal());
		mv.addObject("pedido", pedido);
		mv.addObject("listaItens", pedidosItens);
		mv.addObject("cliente", cliente);
		return mv;
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
				break;
			}
		}

		return "redirect:/carrinho";
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public String adicionarCarrinho(@PathVariable Long id) {

		Optional<Produto> prod = repositorioProduto.findById(id);
		Produto produto = prod.get();

		int controle = 0;
		for (PedidoItens it : pedidosItens) {
			
			if (it.getProduto().getId() == produto.getId() && it.getProduto().getQuantidadeEstoque()> it.getQuantidade()) {
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
			}
			else 
			if((it.getProduto().getId() == produto.getId() && it.getProduto().getQuantidadeEstoque()==it.getQuantidade()))
				controle=2;	
		}
		if (controle == 0 && produto.getQuantidadeEstoque()>=0) {
			PedidoItens item = new PedidoItens();
			item.setProduto(produto);
			item.setValorUnitario(produto.getPreco());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));

			pedidosItens.add(item);
		}
		controle = 0;
		return "redirect:/carrinho";
	}
}
