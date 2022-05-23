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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mgk.Estagio.model.Usuario;

import br.com.mgk.Estagio.Repository.UsuarioRepository;


/**
 *
 * A sample greetings controller to return greeting text
 */


@RestController
public class UsuarioController {

	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private UsuarioRepository usuarioRepository;
	
	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	

	@GetMapping(value = "listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/* Retorna a lista em JSON */

	}

	@PostMapping(value = "salvar") /* mapeia a url */
	
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){  /* Recebe os dados para salvar */
		Usuario usuarios = usuarioRepository.buscarPorLogin(usuario.getLogin());
		
		if(usuarios != null && usuarios.getId()!= usuario.getId()) 
		{	
			Usuario user = new Usuario(); user.setId(-99);
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		}
		else
		{	
			Usuario user = usuarioRepository.saveAndFlush(usuario);
		
			return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
		}
	}
	
    
	
	
	@PutMapping(value = "atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { /* Recebe os dados para salvar */
		

			Usuario user = usuarioRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		
	}
	
	
	
	@DeleteMapping(value = "delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}
	
	
	
	@GetMapping(value = "buscaruserid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) { /* Recebe os dados para consultar */

		Usuario usuario = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

	}
	
	
	
	
	@GetMapping(value = "buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}
	
	
}
