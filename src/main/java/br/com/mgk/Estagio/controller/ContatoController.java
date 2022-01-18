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

import br.com.mgk.Estagio.Repository.ContatoRepository;
import br.com.mgk.Estagio.model.Contato;

@Controller 
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;
	
	@RequestMapping(value ="/contato")
	public String marca() {
		return "contato.html";
	}
	
	
	@GetMapping(value = "contato/cadastro/listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Contato>> listaMarca() {

		List<Contato> marcas = contatoRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Contato>>(marcas, HttpStatus.OK);/* Retorna a lista em JSON */

	}
	
	@PostMapping(value = "contato/cadastro/salvar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Contato> salvar(@RequestBody Contato marca) { /* Recebe os dados para salvar */
	
		    Contato user = contatoRepository.saveAndFlush(marca);
			return new ResponseEntity<Contato>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "contato/cadastro/atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Contato usuario) { /* Recebe os dados para salvar */
		

	     	Contato user = contatoRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Contato>(user, HttpStatus.OK);
		
	}
	

	@DeleteMapping(value = "contato/cadastro/delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long idcontato) { /* Recebe os dados para delete */

		contatoRepository.deleteById(idcontato);

		return new ResponseEntity<String>("Contato deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "contato/cadastro/buscarcontatoid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Contato> buscarmarcaid(@RequestParam(name = "idcontato") Long idmarca) { /* Recebe os dados para consultar */

		Contato marca = contatoRepository.findById(idmarca).get();
		return new ResponseEntity<Contato>(marca, HttpStatus.OK);
	}
	
	@GetMapping(value = "contato/cadastro/buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Contato>> buscarPorNome(@RequestParam(name = "contato") String name) { /* Recebe os dados para consultar */

		List<Contato> usuario = contatoRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Contato>>(usuario, HttpStatus.OK);
	}
}
