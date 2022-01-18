package br.com.mgk.Estagio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.CompraItensRepository;
import br.com.mgk.Estagio.Repository.CompraRepository;
import br.com.mgk.Estagio.Repository.FornecedorRepository;
import br.com.mgk.Estagio.Repository.FuncionarioRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.model.Compra;
import br.com.mgk.Estagio.model.CompraItens;
import br.com.mgk.Estagio.model.Produto;

@Controller
public class CompraController {
	
private List<CompraItens> listaCompra = new ArrayList<CompraItens>();
@Autowired
private CompraRepository compraRepository;

@Autowired
private CompraItensRepository compraItensRepository;

@Autowired
private ProdutoRepository produtoRepository;

@Autowired
private FornecedorRepository fornecedoresRepository;

@Autowired
private FuncionarioRepository funcionarioRepository;

@GetMapping(value = "compra/entrada/cadastro") /* Nosso primeiro método de API */
@ResponseBody /* Retorna os dados par ao corpo da resposta */
public ModelAndView cadastrar(Compra compra, CompraItens item) {
	ModelAndView mv = new ModelAndView("compra");
	mv.addObject("compra",compra);
	mv.addObject("listafornecedores", fornecedoresRepository.findAll());
	mv.addObject("listafuncionario", funcionarioRepository.findAll());
	mv.addObject("listaCompraItens", this.listaCompra);
	mv.addObject("CompraItem", item);	
	return mv;
}



@PostMapping(value = "compra/entrada/salvar") /* Nosso primeiro método de API */
@ResponseBody /* Retorna os dados par ao corpo da resposta */
public ModelAndView salvar(String acao, Compra compra, CompraItens item) {
	ModelAndView mv = new ModelAndView("compra");
	
	mv.addObject("listafornecedores", fornecedoresRepository.findAll());
	mv.addObject("listafuncionario", funcionarioRepository.findAll());
	
	mv.addObject("CompraItem", item);	
	if(acao.equals("itens")) {
		int i=0,j=0;
			for (CompraItens ucp : listaCompra) {
				if(ucp.getProduto().getId()== item.getProduto().getId())
					j=1;
			}
		
			if(j==1)
			{
				for (CompraItens upp : listaCompra) {
					
					if(upp.getProduto().getId() == item.getProduto().getId())
					{
						listaCompra.get(i).setQuantidade(listaCompra.get(i).getQuantidade()+upp.getQuantidade());
						listaCompra.get(i).setValor(listaCompra.get(i).getValor()+upp.getValor());	
					}
					i++;
				}
			}
			else
				this.listaCompra.add(item);
			mv.addObject("compra",compra);
			mv.addObject("listaCompraItens", this.listaCompra);
			return mv;
	}else if(acao.equals("remover")){
		List<CompraItens> listaauxliar= new ArrayList<CompraItens>();
		for (CompraItens upp : listaCompra) {
			if(upp.getProduto().getId() != item.getProduto().getId())
				listaauxliar.add(item);
		}
		this.listaCompra = listaauxliar;
		mv.addObject("compra", new Compra());
		mv.addObject("listaCompraItens", this.listaCompra);
		return mv;
	}
	else
	{
		if(acao.equals("salvar"))
		{
			if(!listaCompra.isEmpty())
			{
				compraRepository.saveAndFlush(compra);
				for(CompraItens it: this.listaCompra) 
				{
					it.setCompra(compra);
					if(compra.getTotal()==null)
						compra.setTotal(0.0);
					compra.setTotal(compra.getTotal()+(it.getValor()*it.getQuantidade()));
					compraItensRepository.save(it);
					Optional <Produto> prod = produtoRepository.findById(it.getProduto().getId());
					Produto produto = prod.get();
					produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()+it.getQuantidade());
					this.listaCompra = new ArrayList<>();
					produtoRepository.saveAndFlush(produto);
					
				}
				compraRepository.saveAndFlush(compra);
				this.listaCompra = new ArrayList<>();
				mv.addObject("CompraItem", new CompraItens());	
				mv.addObject("listaCompraItens", this.listaCompra);
				mv.addObject("compra",new Compra());
				return mv;
			}
			else
			{
				mv.addObject("erro","nao é possivel fazer compra com lista ");
				return mv;
			}
			
		}
	}
	mv.addObject("listaCompraItens", this.listaCompra);
	mv.addObject("compra",new Compra());
	return mv;
}

@GetMapping(value = "compra/entrada/buscarprodutoid") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<Produto> buscarmarcaid(@RequestParam(name = "idproduto") Long idproduto, Model model) { /* Recebe os dados para consultar */

	
	Produto produto = produtoRepository.findById(idproduto).get();
	return new ResponseEntity<Produto>(produto, HttpStatus.OK);
}
	

@GetMapping(value = "compra/entrada/buscarPorNome") 
@ResponseBody 
public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam(name = "name") String name) {

	List<Produto> usuario = produtoRepository.buscarPorNome(name.trim().toUpperCase());

	return new ResponseEntity<List<Produto>>(usuario, HttpStatus.OK);
	} 
}
