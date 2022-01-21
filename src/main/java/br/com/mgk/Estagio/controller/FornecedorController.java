package br.com.mgk.Estagio.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.FornecedorRepository;
import br.com.mgk.Estagio.model.Fornecedor;


/**
 *
 * A sample greetings controller to return greeting text
 */

@RestController
public class FornecedorController {

	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private FornecedorRepository usuarioRepository;
	
	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	
	@RequestMapping(value ="/fornecedor")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("fornecedor");
		return mv;
	}

	@GetMapping(value = "fornecedor/listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Fornecedor>> listaUsuario() {

		List<Fornecedor> usuarios = usuarioRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Fornecedor>>(usuarios, HttpStatus.OK);/* Retorna a lista em JSON */

	}

	@PostMapping(value = "fornecedor/salvar") /* mapeia a url */
	
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor usuario){  /* Recebe os dados para salvar */
	
			Fornecedor user = usuarioRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Fornecedor>(user, HttpStatus.CREATED);
	}
	
    
	
	
	@PutMapping(value = "fornecedor/atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Fornecedor usuario) { /* Recebe os dados para salvar */
		

		Fornecedor user = usuarioRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Fornecedor>(user, HttpStatus.OK);
		
	}
	
	
	
	@DeleteMapping(value = "fornecedor/delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}
	
	
	
	@GetMapping(value = "fornecedor/buscaruserid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Fornecedor> buscaruserid(@RequestParam(name = "iduser") Long iduser) { /* Recebe os dados para consultar */

		Fornecedor usuario = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Fornecedor>(usuario, HttpStatus.OK);

	}
	
	
	
	
	@GetMapping(value = "fornecedor/buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Fornecedor>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Fornecedor> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Fornecedor>>(usuario, HttpStatus.OK);
	}
	
}
