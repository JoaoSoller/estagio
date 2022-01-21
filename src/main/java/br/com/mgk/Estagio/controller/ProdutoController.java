package br.com.mgk.Estagio.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.ResponseBody;

import br.com.mgk.Estagio.Repository.CategoriaRepository;
import br.com.mgk.Estagio.Repository.MarcaRepository;
import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.model.Categoria;
import br.com.mgk.Estagio.model.Marca;
import br.com.mgk.Estagio.model.Produto;


	@Controller
	@CrossOrigin(origins = "localhost:8000")
	public class ProdutoController {

		@Autowired 
		private static String caminhoImagens = "C://IMAGENS SITE/";
		
		@Autowired
		private CategoriaRepository categoriaRepository;
		@Autowired
		private MarcaRepository marcaRepository;

		@Autowired /* IC/CD ou CDI - Injeção de dependencia */
		private ProdutoRepository produtoRepository;
		
		@RequestMapping(value ="/produto")
		public String produto(Model model) {
			List<Categoria> catelist = categoriaRepository.findAll();
			List<Marca> marcalist = marcaRepository.findAll();
			model.addAttribute("produto", new Produto());
			model.addAttribute("marcas",marcalist);
			model.addAttribute("marca", new Marca());
			model.addAttribute("categorias",catelist);
			model.addAttribute("categoria", new Categoria());
			
			return "produto.html";
		}
		
		
		@GetMapping(value = "produto/cadastro/listatodos") /* Nosso primeiro método de API */
		@ResponseBody /* Retorna os dados par ao corpo da resposta */
		public ResponseEntity<List<Produto>> listaMarca() {

			List<Produto> marcas = produtoRepository.findAll();/* executa a consulta no banco de dados */

			return new ResponseEntity<List<Produto>>(marcas, HttpStatus.OK);/* Retorna a lista em JSON */

		}
		
		@PostMapping(value = "produto/cadastro/salvar")  
		@ResponseBody   
		public ResponseEntity<Produto> atualizar(@RequestBody Produto usuario) 
		{  
			
		
			//usuario.setNomeImagem(String.valueOf(usuario.getId()) + arquivo.getOriginalFilename());
					
			Produto user = produtoRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Produto>(user, HttpStatus.CREATED);
		
		}
		

		@DeleteMapping(value = "produto/cadastro/delete") /* mapeia a url */
		@ResponseBody /* Descricao da resposta */
		public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

			produtoRepository.deleteById(iduser);

			return new ResponseEntity<String>("Produto deletado com sucesso", HttpStatus.OK);
		}
		
		@GetMapping(value = "produto/cadastro/buscarprodutoid") /* mapeia a url */
		@ResponseBody /* Descricao da resposta */
		public ResponseEntity<Produto> buscarmarcaid(@RequestParam(name = "idproduto") Long idproduto, Model model) { /* Recebe os dados para consultar */

			
			Produto produto = produtoRepository.findById(idproduto).get();
			return new ResponseEntity<Produto>(produto, HttpStatus.OK);
		}
		
		
		@GetMapping(value = "produto/cadastro/buscarPorNome") 
		 
		@ResponseBody  
		public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam(name = "name") String name) {  

			List<Produto> usuario = produtoRepository.buscarPorNome(name.trim().toUpperCase());

			return new ResponseEntity<List<Produto>>(usuario, HttpStatus.OK);
		}
		
	

}
