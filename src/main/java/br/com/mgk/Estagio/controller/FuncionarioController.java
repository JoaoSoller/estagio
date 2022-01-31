package br.com.mgk.Estagio.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import br.com.mgk.Estagio.model.Funcionario;
import br.com.mgk.Estagio.Repository.FuncionarioRepository;


/**
 *
 * A sample greetings controller to return greeting text
 */

@RestController
public class FuncionarioController {

	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private FuncionarioRepository usuarioRepository;
	
	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	
	@RequestMapping(value ="/funcionario")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("funcionario");
		return mv;
	}

	@GetMapping(value = "funcionario/listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Funcionario>> listaUsuario() {

		List<Funcionario> usuarios = usuarioRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Funcionario>>(usuarios, HttpStatus.OK);/* Retorna a lista em JSON */

	}

	@PostMapping(value = "funcionario/salvar") /* mapeia a url */
	
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Funcionario> salvar(@RequestBody Funcionario usuario){  /* Recebe os dados para salvar */
	
			Funcionario user = usuarioRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Funcionario>(user, HttpStatus.CREATED);
	}
	
    
	
	
	@PutMapping(value = "funcionario/atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Funcionario usuario) { /* Recebe os dados para salvar */
		
		if(usuario.getId()!=0)// só alterar senha se for primeiro cadastro
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		Funcionario user = usuarioRepository.saveAndFlush(usuario);	
		return new ResponseEntity<Funcionario>(user, HttpStatus.OK);
		
	}
	
	
	
	@DeleteMapping(value = "funcionario/delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}
	
	
	
	@GetMapping(value = "funcionario/buscaruserid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Funcionario> buscaruserid(@RequestParam(name = "iduser") Long iduser) { /* Recebe os dados para consultar */

		Funcionario usuario = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Funcionario>(usuario, HttpStatus.OK);

	}
	
	
	
	
	@GetMapping(value = "funcionario/buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Funcionario>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Funcionario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Funcionario>>(usuario, HttpStatus.OK);
	}
	
}
