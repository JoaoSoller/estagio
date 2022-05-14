package br.com.mgk.Estagio.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import br.com.mgk.Estagio.Repository.CategoriaRepository;
import br.com.mgk.Estagio.Repository.FuncionarioRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.Repository.PromocaoItensRepository;
import br.com.mgk.Estagio.Repository.PromocaoRepository;
import br.com.mgk.Estagio.model.Categoria;
import br.com.mgk.Estagio.model.Compra;
import br.com.mgk.Estagio.model.Produto;
import br.com.mgk.Estagio.model.Promocao;
import br.com.mgk.Estagio.model.PromocaoItens;
@Controller
public class PromocaoController {
	private List<Promocao> promocoes;
	private List<PromocaoItens> listapro = new ArrayList<PromocaoItens>();
	private boolean ex;
	private Long posfun, posfor= new Long(0);
	private List<PromocaoItens> listaPromocao = new ArrayList<PromocaoItens>();
	private Promocao promocao = new Promocao(); 
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PromocaoRepository promocaoRepository;

	@Autowired
	private PromocaoItensRepository promocaoItensRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
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

	/*
	 @PostMapping(value = "promocao/entrada/salvar") Nosso primeiro método de API 
	@ResponseBody  Retorna os dados par ao corpo da resposta 
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
	}*/ 

	@PostMapping("promocao/entrada/salvar") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ModelAndView salvar(String acao, Promocao promocao, PromocaoItens item) {
		promocoes = promocaoRepository.findAll();

		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd"); 
		ModelAndView mv = new ModelAndView("promocao");
		ModelAndView mv2 = new ModelAndView("promocao2");
		Date data;
		if(promocao.getFuncionario()==null);
		{
			promocao.setPosfun(0);
		}
		promocao.setPosfun(posfun);
		mv.addObject("listafuncionario", funcionarioRepository.findAll());
		mv2.addObject("listafuncionario", funcionarioRepository.findAll());
		this.promocao=promocao;
		if(acao.equals("pesquisar") )
		{
			promocao.setPosfun(0);
			
			return mv;
		}

		mv.addObject("PromocaoItem", item);
		if(acao.equals("itens")) {
			int i=0,j=0,k=0;
					for (PromocaoItens ucp : listaPromocao) {
						if(ucp.getProduto().getId()== item.getProduto().getId())
							j=1;
						k++;	
					}
				
					if(j==1)
					{
						for (PromocaoItens upp : listaPromocao) {
							
							if(upp.getProduto().getId() == item.getProduto().getId())
							{
								item.setDesconto(item.getPrecoantigo()*(promocao.getPorcentagem()/100));
								listaPromocao.get(i).setPreconovo(item.getPrecoantigo()-item.getDesconto());
							}
							//MUDAR POIS ESTA DUPLICANDO NA HORA DE MOSTRAR ESTAVA DANDO CERTO A REMOCAO MAS AGORA DEU ERRO DENOVO
							i++;
						}
					}
					else
					{
						item.setDesconto(item.getPrecoantigo()*(promocao.getPorcentagem()/100));
						listaPromocao.get(i).setPreconovo(item.getPrecoantigo()-item.getDesconto());
						this.listaPromocao.add(item);
					}
				
					mv.addObject("promocao",promocao);
					this.promocao=promocao;
					mv.addObject("listaPromocaoItens", this.listaPromocao);
					return mv;
		}else if(acao.equals("remover")|| acao.equals("")){
			/*
			int y=0, i=0;
			List<PromocaoItens> listaauxliar= new ArrayList<PromocaoItens>();
			for (PromocaoItens upp : listaPromocao)
			{
				y++;
				if(upp.getProduto().getId() == item.getProduto().getId())
					i++;
				else
					listaauxliar.add(upp);
			}
			if(i==0)
				listaauxliar.clear();;
				for (PromocaoItens upp : listaPromocao)
				{	
					if(i<y-1)
						listaauxliar.add(upp);
					i++;
				}*/
			listaPromocao.remove(listaPromocao.size()-1);
			mv2.addObject("promocao", promocao);
			mv2.addObject("listaPromocaoItens", this.listaPromocao);
			return mv2; 
		}
		else
		{
			if(acao.equals("salvar") || acao.equals("calcular"))
			{
				
				  LocalDate d1 = LocalDate.parse(promocao.getDtini());
			      LocalDate d2 = LocalDate.parse(promocao.getDtfim());
				if(d1.isAfter(d2))
				{
					mv.addObject("promocao",promocao);
					mv.addObject("erro","data fim menor que data inicio");
					mv.addObject("promocao",promocao);
					mv.addObject("listaPromocaoItens", this.listaPromocao);
					return mv;
				}
				for (Promocao prom : promocoes) {
					  LocalDate p1 = LocalDate.parse(prom.getDtini());
				      LocalDate p2 = LocalDate.parse(prom.getDtfim());
					if((d1.isEqual(p1) || d1.isAfter(p1)) && (d1.isEqual(p2) || d2.isBefore(p2)))
					{
						listapro = promocaoItensRepository.buscarPorPromocao(prom.getId());
						for (PromocaoItens proitem : listapro) {
							for(PromocaoItens novo : listaPromocao)
							{
								if(proitem.getProduto().getId() == novo.getProduto().getId() && proitem.getId()!=novo.getId())
								{
									mv.addObject("promocao",promocao);
									mv.addObject("erro","o produto "+ proitem.getProduto().getNome()+" ja esta em uma promoção ativa");
									mv.addObject("promocao",promocao);
									mv.addObject("listaPromocaoItens", this.listaPromocao);
									return mv;
								}
							}
						}
					}
				}
				promocaoItensRepository.deleteAll(promocaoItensRepository.buscarPorPromocao(promocao.getId()));
				if(!listaPromocao.isEmpty())
				{
					promocaoRepository.saveAndFlush(promocao);
					for(PromocaoItens it: this.listaPromocao) 
					{
						it.setDesconto(it.getPrecoantigo()*(promocao.getPorcentagem()/100));
						it.setPreconovo(it.getPrecoantigo()-it.getDesconto());
						it.setPromocao(promocao);
						promocaoItensRepository.saveAndFlush(it);
						
					}
					this.promocao=promocao;
				
					List<PromocaoItens> listaauxliarnovau= new ArrayList<PromocaoItens>();
					this.listaPromocao = listaauxliarnovau;
					mv.addObject("PromocaoItem", new PromocaoItens());	
					mv.addObject("listaPromocaoItens", this.listaPromocao);
					mv.addObject("promocao",new Promocao());
					return mv;
				}
				else
				{
					mv.addObject("erro","nao é possivel fazer compra com lista vazia ");
					return mv;
				}
				
			}else if(acao.equals("novo")) 
			{
				this.listaPromocao = new ArrayList<>();
				mv.addObject("PromocaoItem", new PromocaoItens());	
				mv.addObject("listaPromocaoItens", this.listaPromocao);
				mv.addObject("promocao",new Promocao());
				return mv;
				
			}else if(acao.equals("excluir") && ex)
			{
				promocaoItensRepository.deleteAll(promocaoItensRepository.buscarPorPromocao(promocao.getId()));
				promocaoRepository.delete(promocao);
				this.listaPromocao = new ArrayList<>();
				mv.addObject("promocao",new Promocao());
				mv.addObject("listaPromocaoItens", this.listaPromocao);
				return mv;
			}
			
		}
		List<PromocaoItens> listaauxliar= new ArrayList<PromocaoItens>();
		for (PromocaoItens upp : listaPromocao) {
			upp.setDesconto(upp.getPrecoantigo()*(promocao.getPorcentagem()/100));	
			upp.setPreconovo(upp.getPrecoantigo()-upp.getDesconto());
			listaauxliar.add(upp);
			
		}
		listaPromocao = listaauxliar;
		ModelAndView mvn = new ModelAndView("promocao2");
		mvn.addObject("promocao",promocao);
		mvn.addObject("listafuncionario", funcionarioRepository.findAll());
		mvn.addObject("listaPromocaoItens", this.listaPromocao);
		mvn.addObject("promocao",promocao);
		return mvn;
	
	}
	
	@GetMapping(value = "promocao/entrada/edicao") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Promocao> edicao(@RequestParam(name = "idcompra") Long idcompra) { /* Recebe os dados para consultar */
		ModelAndView mv = new ModelAndView("promocao");
		Promocao promocao = new Promocao();
		promocao = promocaoRepository.findById(idcompra).get();
		this.promocao = promocao;
		
		List<PromocaoItens> listacomp =promocaoItensRepository.buscarPorPromocao(promocao.getId());
		mv.addObject("promocao",promocao);
		mv.addObject("listaPromocaoItens", listacomp);
		mv.addObject("PromocaoItem", new PromocaoItens());
		PromocaoItens item = new PromocaoItens();
		long a = 37;
		item.setProduto(produtoRepository.getById(a));
		this.listaPromocao = listacomp;
		return new ResponseEntity<Promocao>(promocao, HttpStatus.OK);
	}

	
@GetMapping(value = "/buscarprodutoidtudo") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void buscarmarcaid(@RequestParam(name = "idproduto") Long idproduto, Model model) { /* Recebe os dados para consultar */
	double total = promocao.getPorcentagem()/100;
	total=total+1;
	Categoria cat = categoriaRepository.getById(idproduto);
	List<Produto> usuario = produtoRepository.findAll();
	List<PromocaoItens> listaauxliar= new ArrayList<PromocaoItens>();
	for (Produto produto : usuario) {
		if(produto.getCategoria()  == idproduto)
		{
		PromocaoItens upp = new PromocaoItens();
		upp.setProduto(produto);
		upp.setPromocao(promocao);
		upp.setPrecoantigo(produto.getPreco());
		upp.setDesconto(upp.getPrecoantigo()*(promocao.getPorcentagem()/100));
		
		upp.setPreconovo(upp.getPrecoantigo()-upp.getDesconto());
		//String valorFormatado = new DecimalFormat("0.00").format(upp.getPreconovo());
		//upp.setPreconovo(Double.valueOf(valorFormatado).doubleValue());
		listaauxliar.add(upp);
		}
	}
	listaPromocao = listaauxliar;
	cadastrar(promocao,null);
}

@GetMapping(value = "promocao/entrada/resultex") /* mapeia a url */
@ResponseBody 

public void  resultex(@RequestParam(name = "ex") Boolean ex) { /* Recebe os dados para consultar */
	this.ex=ex;
	}

@GetMapping(value = "/buscarPorNometudo") 
@ResponseBody 
public ResponseEntity<List<Categoria>> buscarPorNome(@RequestParam(name = "name") String name) {

	List<Categoria> usuario = categoriaRepository.buscarPorNome(name.trim().toUpperCase());

	return new ResponseEntity<List<Categoria>>(usuario, HttpStatus.OK);
	} 
@GetMapping(value = "promocao/entrada/posfun") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void  posfun(@RequestParam(name = "posfun") Long posfun) { /* Recebe os dados para consultar */
	this.posfun=posfun;
	}	

@GetMapping(value = "promocao/entrada/buscarListaItem") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<PromocaoItens>>  buscarItens(@RequestParam(name = "idcompra") Long idcompra) { /* Recebe os dados para consultar */
	
	List<PromocaoItens> lista = promocaoItensRepository.buscarPorPromocao(idcompra);
	this.listaPromocao = lista;
	return new ResponseEntity<List<PromocaoItens>>(lista, HttpStatus.OK);
}

@GetMapping(value = "promocao/entrada/buscarPorPromocaoFiltro") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<Promocao>>  buscarPorCompraFiltro(@RequestParam(name = "datalol") String  datalol) { /* Recebe os dados para consultar */
	List<Promocao> nova =  new ArrayList<Promocao>();
	List<Promocao> usuario = promocaoRepository.findAll();
	for (Promocao compra : usuario) {	
		if(compra.getDtini().equals(datalol)) {
			nova.add(compra);
		}		
	}

	return new ResponseEntity<List<Promocao>>(nova, HttpStatus.OK);
}
@GetMapping(value = "promocao/entrada/buscarmarcaid") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<Categoria> buscarmarcaid(@RequestParam(name = "idmarca") Long idmarca) { /* Recebe os dados para consultar */

	Categoria categorias = categoriaRepository.findById(idmarca).get();
	return new ResponseEntity<Categoria>(categorias, HttpStatus.OK);
}


@GetMapping(value = "promocao/entrada/buscarPorPromocao") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<Promocao>>  buscarPorPromocao() { /* Recebe os dados para consultar */
	
	List<Promocao> usuario = promocaoRepository.findAll();

	return new ResponseEntity<List<Promocao>>(usuario, HttpStatus.OK);
}

}
