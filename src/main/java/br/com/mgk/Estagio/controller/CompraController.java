package br.com.mgk.Estagio.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.CompraItensRepository;
import br.com.mgk.Estagio.Repository.CompraRepository;
import br.com.mgk.Estagio.Repository.FornecedorRepository;
import br.com.mgk.Estagio.Repository.FuncionarioRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.model.Cliente;
import br.com.mgk.Estagio.model.Compra;
import br.com.mgk.Estagio.model.CompraItens;
import br.com.mgk.Estagio.model.PedidoItens;
import br.com.mgk.Estagio.model.Produto;

@Controller
public class CompraController {
private String datalolfim;
private long posfun= new Long(0);
private long posfor = new Long(0);
private Double compraaux = new Double(0.0);
private boolean ex = false;
private int idnovo;
private Compra compra = new Compra();
private String id;
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

public Double calculaTotal(List<CompraItens> lista) { /* Recebe os dados para consultar */

	double total = 0.0;
	for(CompraItens it: lista) 
	{
		total = (total +(it.getValor()*it.getQuantidade()));
	}
	return total;
}

@GetMapping(value = "compra/entrada/mudardata") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void mudardata(@RequestParam(name = "datalolfim") String datalolfim) { /* Recebe os dados para consultar */
	this.datalolfim=datalolfim;
}	

@PostMapping("compra/entrada/salvar") /* Nosso primeiro método de API */
@ResponseBody /* Retorna os dados par ao corpo da resposta */
public ModelAndView salvar(String acao, Compra compra, CompraItens item) {
	ModelAndView mv = new ModelAndView("compra");
	ModelAndView mv2 = new ModelAndView("compra2");
	double totalcompra=0.0 ,aux1=0;
	
	
	if(compra.getData()==null)
	{
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		compra.setData(date.format(formatter));
	}
	compra.setPosfor(posfor);
	compra.setPosfun(posfun);
	mv.addObject("listafornecedores", fornecedoresRepository.findAll());
	mv.addObject("listafuncionario", funcionarioRepository.findAll());
	mv2.addObject("listafornecedores", fornecedoresRepository.findAll());
	mv2.addObject("listafuncionario", funcionarioRepository.findAll());
	this.compra=compra;
	if(acao.equals("pesquisar") )
	{
		compra.setPosfor(0);
		compra.setPosfun(0);
		
		return mv;
	}
	mv.addObject("CompraItem", item);
	mv2.addObject("CompraItem", item);
	if(acao.equals("itens")) {
		int i=0,j=0,k=0;
				for (CompraItens ucp : listaCompra) {
					if(ucp.getProduto().getId()== item.getProduto().getId())
						j=1;
					k++;	
				}
				if(item.getQuantidade()>0 && item.getValor()>0)
				{
					if(j==1)
					{
						aux1=0;
						for (CompraItens upp : listaCompra) {
							
							if(upp.getProduto().getId() == item.getProduto().getId())
							{
								listaCompra.get(i).setQuantidade(listaCompra.get(i).getQuantidade()+item.getQuantidade());
								listaCompra.get(i).setValor(item.getValor());
								listaCompra.get(i).setValoritem();
							}
							//MUDAR POIS ESTA DUPLICANDO NA HORA DE MOSTRAR ESTAVA DANDO CERTO A REMOCAO MAS AGORA DEU ERRO DENOVO
							i++;
						}
					}
					else
					{
						this.listaCompra.add(item);
						listaCompra.get(k).setValoritem();
					}
					totalcompra = calculaTotal(this.listaCompra);
					mv2.addObject("compra",compra);
					mv.addObject("compra",compra);
					this.compra=compra;
					
					mv2.addObject("valorcompratotallol",totalcompra);
					mv2.addObject("listaCompraItens", this.listaCompra);
					mv.addObject("valorcompratotallol",totalcompra);
					mv.addObject("listaCompraItens", this.listaCompra);
					return mv2;
				}
				else
				{
					if(this.listaCompra.size()>0)
					{
					mv2.addObject("erro", "valor ou quantidade nao pode ser 0");
					totalcompra = calculaTotal(this.listaCompra);
					mv2.addObject("valorcompratotallol",totalcompra);
					mv2.addObject("compra",compra);
					mv2.addObject("listaCompraItens", this.listaCompra);
					return mv2;
					}
					else
					{
						mv.addObject("erro", "valor ou quantidade nao pode ser 0");
						totalcompra = calculaTotal(this.listaCompra);
						mv.addObject("valorcompratotallol",totalcompra);
						mv.addObject("compra",compra);
						mv.addObject("listaCompraItens", this.listaCompra);
						return mv;
					}
				}
	}else if(acao.equals("remover")|| acao.equals("")){
		int y=0, i=0;
		List<CompraItens> listaauxliar= new ArrayList<CompraItens>();
		for (CompraItens upp : listaCompra)
		{
			y++;
			if(upp.getProduto().getId() == item.getProduto().getId())
				i++;
			else
				listaauxliar.add(upp);
		}
		if(i==0)
			for (CompraItens upp : listaCompra)
			{	
				if(i<y-1)
					listaauxliar.add(upp);
				i++;
			}
		listaCompra = listaauxliar;
		totalcompra = calculaTotal(this.listaCompra);
		mv2.addObject("compra", compra);
		mv2.addObject("valorcompratotallol",totalcompra);
		mv2.addObject("listaCompraItens", this.listaCompra);
		return mv; 
	}
	else
	{
		if(acao.equals("salvar"))
		{
			for (CompraItens compraItens : compraItensRepository.buscarPorCompra(compra.getId())) {
				Optional <Produto> prod = produtoRepository.findById(compraItens.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()-compraItens.getQuantidade());
				produtoRepository.saveAndFlush(produto);
			}
			compraItensRepository.deleteAll(compraItensRepository.buscarPorCompra(compra.getId()));
			if(!listaCompra.isEmpty())
			{
				compraRepository.saveAndFlush(compra);
				for(CompraItens it: this.listaCompra) 
				{
					it.setCompra(compra);
					if(compra.getTotal()==null)
						compra.setTotal(0.0);
					compra.setTotal(compra.getTotal()+(it.getValor()*it.getQuantidade()));
					it.setNome();
					it.setIdproduto();
					compraItensRepository.saveAndFlush(it);
					Optional <Produto> prod = produtoRepository.findById(it.getProduto().getId());
					Produto produto = prod.get();
					produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()+it.getQuantidade());
					produto.setAtivo(77);
					this.listaCompra = new ArrayList<>();
					produtoRepository.saveAndFlush(produto);
					
				}
				this.compra=compra;
				compraRepository.saveAndFlush(compra);
				this.listaCompra = new ArrayList<>();
				mv.addObject("CompraItem", new CompraItens());	
				mv.addObject("listaCompraItens", this.listaCompra);
				mv.addObject("compra",new Compra());
				return mv;
			}
			else
			{
				mv.addObject("erro","nao é possivel fazer compra com lista vazia ");
				return mv;
			}
			
		}else if(acao.equals("novo")) 
		{
			this.listaCompra = new ArrayList<>();
			mv.addObject("CompraItem", new CompraItens());	
			mv.addObject("listaCompraItens", this.listaCompra);
			mv.addObject("compra",new Compra());
			return mv;
			
		}else if(acao.equals("excluir") && ex)
		{
			for (CompraItens it : listaCompra) {
				Optional <Produto> prod = produtoRepository.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()-it.getQuantidade());
				this.listaCompra = new ArrayList<>();
				produtoRepository.saveAndFlush(produto);
			}
			
			
			compraItensRepository.deleteAll(compraItensRepository.buscarPorCompra(compra.getId()));
			compraRepository.delete(compra);
			this.listaCompra = new ArrayList<>();
			totalcompra = calculaTotal(this.listaCompra);
			mv.addObject("compra", new Compra());
			mv.addObject("valorcompratotallol",totalcompra);
			mv.addObject("listaCompraItens", this.listaCompra);
			mv.addObject("CompraItem", new CompraItens());
			return mv;
		}
		
	}
	mv.addObject("listaCompraItens", this.listaCompra);
	mv.addObject("compra",compra);
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


@GetMapping(value = "compra/entrada/buscarPorCompra") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<Compra>>  buscarPorCompra() { /* Recebe os dados para consultar */
	
	List<Compra> usuario = compraRepository.findAll();

	return new ResponseEntity<List<Compra>>(usuario, HttpStatus.OK);
}

@GetMapping(value = "compra/entrada/buscarPorCompraFiltro") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<Compra>>  buscarPorCompraFiltro(@RequestParam(name = "datalol") String  datalol) { /* Recebe os dados para consultar */
	 LocalDate d1; //= LocalDate.parse(promocao.getDtini());
     LocalDate d2; //= LocalDate.parse(promocao.getDtfim());
     LocalDate p1 = LocalDate.parse(datalol);
     LocalDate p2 = LocalDate.parse(datalolfim);
    
	List<Compra> nova =  new ArrayList<Compra>();
	List<Compra> usuario = compraRepository.findAll();
	for (Compra compra : usuario) {	
		 d1 = LocalDate.parse(compra.getData());
		//if(compra.getData().equals(datalol)) {
		if((d1.isEqual(p1) ||d1.isAfter(p1))	&&  (d1.isEqual(p2) || d1.isBefore(p2)))	
			nova.add(compra);
		//}		
	}

	return new ResponseEntity<List<Compra>>(nova, HttpStatus.OK);
}


@GetMapping(value = "compra/entrada/edicao") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<Compra> edicao(@RequestParam(name = "idcompra") Long idcompra) { /* Recebe os dados para consultar */
	ModelAndView mv = new ModelAndView("compra");
	double totalcompra =0;
	Compra compra = new Compra();
	compra = compraRepository.findById(idcompra).get();
	this.compra = compra;
	
	List<CompraItens> listacomp =compraItensRepository.buscarPorCompra(compra.getId());
	this.compraaux = totalcompra = calculaTotal(listacomp);
	compra.setTotal(totalcompra);
	mv.addObject("compra",compra);
	mv.addObject("valorcompratotallol",totalcompra);
	mv.addObject("listaCompraItens", listacomp);
	mv.addObject("CompraItem", new CompraItens());
	CompraItens item = new CompraItens();
	long a = 37;
	item.setProduto(produtoRepository.getById(a));
	this.listaCompra = listacomp;
	return new ResponseEntity<Compra>(compra, HttpStatus.OK);
}

@GetMapping(value = "compra/entrada/buscarListaItem") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<CompraItens>>  buscarItens(@RequestParam(name = "idcompra") Long idcompra) { /* Recebe os dados para consultar */
	
	Compra compra = compraRepository.findById(idcompra).get();
	List<CompraItens> lista = compraItensRepository.buscarPorCompra(idcompra);
	this.listaCompra = lista;
	return new ResponseEntity<List<CompraItens>>(lista, HttpStatus.OK);
}

@GetMapping(value = "compra/entrada/posfor") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void  posfor(@RequestParam(name = "posfor") Long posfor) { /* Recebe os dados para consultar */
	this.posfor=posfor;
	}


@GetMapping(value = "compra/entrada/posfun") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void  posfun(@RequestParam(name = "posfun") Long posfun) { /* Recebe os dados para consultar */
	this.posfun=posfun;
	}

@GetMapping(value = "compra/entrada/resultex") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void  resultex(@RequestParam(name = "ex") Boolean ex) { /* Recebe os dados para consultar */
	this.ex=ex;
	}


@GetMapping(value = "compra/entrada/excluir") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public void  excluiressaporra(){ /* Recebe os dados para consultar */
	int i =0, y;
	y=listaCompra.size();
	List<CompraItens> listaauxliar= new ArrayList<CompraItens>();
	listaauxliar = listaCompra ;
	listaauxliar.remove(0);
	listaCompra = listaauxliar;
}

@PostMapping("alterarQuantidadeCompra/{id}")
@ResponseBody
public void alterarQuantidade(@RequestParam(name = "id") int id) {
	this.idnovo = id;
	/*ModelAndView mv = new ModelAndView("/compra");
	List<CompraItens> listaauxliar= new ArrayList<CompraItens>();
	int i=0, y=0;
	for (CompraItens upp : listaCompra)
	{
		y++;
		if(upp.getProduto().getId() == id)
			i++;
		else
			listaauxliar.add(upp);
	}
		listaCompra = listaauxliar;
		compraaux = calculaTotal(listaauxliar);
		mv.addObject("compra",this.compra);
		mv.addObject("valorcompratotallol",compraaux);
		mv.addObject("listaCompraItens", listaCompra);
		mv.addObject("listafornecedores", fornecedoresRepository.findAll());
		mv.addObject("listafuncionario", funcionarioRepository.findAll());;
	*/
	
	}
}