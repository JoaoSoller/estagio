package br.com.mgk.Estagio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.mgk.Estagio.Repository.ClienteRepository;
import br.com.mgk.Estagio.model.Cliente;

@Controller 
public class ClienteController {
	
	
	@Autowired
	private ClienteRepository clienteRepository;
	@RequestMapping(value ="/cliente")
	public String cliente() {
		return "cliente.html";
	}
	
	@GetMapping(value = "cliente/cadastro/listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Cliente>> listaMarca() {

		List<Cliente> cliente = clienteRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Cliente>>(cliente, HttpStatus.OK);/* Retorna a lista em JSON */

	}
	
	@PostMapping(value = "cliente/cadastro/salvar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) { /* Recebe os dados para salvar */
		if(cliente.getId()==0)
			cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		Cliente user = clienteRepository.saveAndFlush(cliente);
		return new ResponseEntity<Cliente>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "cliente/cadastro/atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Cliente cliente) { /* Recebe os dados para salvar */
			if(!cliente.getSenha().isEmpty())
			cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
			Cliente user = clienteRepository.saveAndFlush(cliente);	
			return new ResponseEntity<Cliente>(user, HttpStatus.OK);
		
	}
	

	@DeleteMapping(value = "cliente/cadastro/delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long idcliente) { /* Recebe os dados para delete */

		clienteRepository.deleteById(idcliente);

		return new ResponseEntity<String>("Categoria deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "cliente/cadastro/buscarclienteid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Cliente> buscarmarcaid(@RequestParam(name = "idcliente") Long idcliente) { /* Recebe os dados para consultar */

		Cliente cliente = clienteRepository.findById(idcliente).get();
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@GetMapping(value = "cliente/cadastro/buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Cliente> cliente = clienteRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Cliente>>(cliente, HttpStatus.OK);
	}
}
