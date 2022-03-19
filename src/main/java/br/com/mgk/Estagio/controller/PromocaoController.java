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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.FuncionarioRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.Repository.PromocaoItensRepository;
import br.com.mgk.Estagio.Repository.PromocaoRepository;
import br.com.mgk.Estagio.model.Produto;
import br.com.mgk.Estagio.model.Promocao;
import br.com.mgk.Estagio.model.PromocaoItens;
@Controller
public class PromocaoController {

	private List<PromocaoItens> listaPromocao = new ArrayList<PromocaoItens>();
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PromocaoRepository promocaoRepository;

	@Autowired
	private PromocaoItensRepository promocaoItensRepository;

	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@GetMapping(value = "promocao/entrada/cadastro") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ModelAndView cadastrar(Promocao promocao, PromocaoItens item) {
		ModelAndView mv = new ModelAndView("promocao");
		mv.addObject("promocao",promocao);
		mv.addObject("listafuncionario", funcionarioRepository.findAll());
		mv.addObject("listaPromocaoItens", this.listaPromocao);
		mv.addObject("PromocaoItem", item);	
		return mv;
	}

	@PostMapping(value = "promocao/entrada/salvar") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ModelAndView salvar(String acao, Promocao promocao, PromocaoItens item) {
		ModelAndView mv = new ModelAndView("promocao");
		mv.addObject("listafuncionario", funcionarioRepository.findAll());
		
		mv.addObject("PromocaoItem", item);	
		if(acao.equals("itens")) {
			int i=0,j=0; 
				for (PromocaoItens ucp : listaPromocao) {
					if(ucp.getProduto().getId()== item.getProduto().getId())
					{
						j=1;
					}	
				}
				if(i==0) {

					item.setPrecoantigo(item.getProduto().getPreco());
					this.listaPromocao.add(item);
				}
				mv.addObject("datainiciala",promocao.getDtini());
				mv.addObject("datafinala",promocao.getDtfim());
				mv.addObject("promocao",promocao);
				mv.addObject("listaPromocaoItens", this.listaPromocao);
				return mv;
		}else if(acao.equals("remover")){
			List<PromocaoItens> listaauxliar= new ArrayList<PromocaoItens>();
			for (PromocaoItens upp : listaPromocao) {
				if(upp.getProduto().getId() != item.getProduto().getId())
					listaauxliar.add(item);
			}
			this.listaPromocao = listaauxliar;
			mv.addObject("compra", new Promocao());
			mv.addObject("listaPromocaoItens", this.listaPromocao);
			return mv;
		}
		else
		{
			if(acao.equals("salvar"))
			{
					if(!listaPromocao.isEmpty())
					{
						promocaoRepository.saveAndFlush(promocao);
						for(PromocaoItens it: this.listaPromocao) 
						{	
							it.setPromocao(promocao);	
							promocaoItensRepository.save(it);
						}
						promocaoRepository.saveAndFlush(promocao);
						this.listaPromocao = new ArrayList<>();
						mv.addObject("CompraItem", new PromocaoItens());	
						mv.addObject("listaPromocaoItens", this.listaPromocao);
						mv.addObject("promocao",new Promocao());
						return mv;
					}
					else
					{
						mv.addObject("erro","nao é possivel fazer promocao com lista vazia ");
						return mv;
					}
				
			}
		}
		mv.addObject("listaPromocaoItens", this.listaPromocao);
		mv.addObject("promocao",new Promocao());
		return mv;
	}
	

@GetMapping(value = "/buscarprodutoidtudo") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<Produto> buscarmarcaid(@RequestParam(name = "idproduto") Long idproduto, Model model) { /* Recebe os dados para consultar */

	
	Produto produto = produtoRepository.findById(idproduto).get();
	return new ResponseEntity<Produto>(produto, HttpStatus.OK);
}


@GetMapping(value = "/buscarPorNometudo") 
@ResponseBody 
public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam(name = "name") String name) {

	List<Produto> usuario = produtoRepository.buscarPorNome(name.trim().toUpperCase());

	return new ResponseEntity<List<Produto>>(usuario, HttpStatus.OK);
	} 
	
}
