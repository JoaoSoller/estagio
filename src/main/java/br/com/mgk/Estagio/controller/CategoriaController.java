package br.com.mgk.Estagio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.mgk.Estagio.Repository.CategoriaRepository;
import br.com.mgk.Estagio.model.Categoria;

@Controller 
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@RequestMapping(value ="/categoria")
	public String categoria() {
		return "categoria.html";
	}
	
	@GetMapping(value = "categoria/cadastro/listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Categoria>> listaMarca() {

		List<Categoria> categoria = categoriaRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Categoria>>(categoria, HttpStatus.OK);/* Retorna a lista em JSON */

	}
	
	@PostMapping(value = "categoria/cadastro/salvar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria) { /* Recebe os dados para salvar */
	
			Categoria user = categoriaRepository.saveAndFlush(categoria);
			return new ResponseEntity<Categoria>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "categoria/cadastro/atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Categoria usuario) { /* Recebe os dados para salvar */
		

		    Categoria user = categoriaRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Categoria>(user, HttpStatus.OK);
		
	}
	

	@DeleteMapping(value = "categoria/cadastro/delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		categoriaRepository.deleteById(iduser);

		return new ResponseEntity<String>("Categoria deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "categoria/cadastro/buscarmarcaid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Categoria> buscarmarcaid(@RequestParam(name = "idmarca") Long idmarca) { /* Recebe os dados para consultar */

		Categoria categorias = categoriaRepository.findById(idmarca).get();
		return new ResponseEntity<Categoria>(categorias, HttpStatus.OK);
	}
	
	@GetMapping(value = "categoria/cadastro/buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Categoria>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Categoria> usuario = categoriaRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Categoria>>(usuario, HttpStatus.OK);
	}
}
