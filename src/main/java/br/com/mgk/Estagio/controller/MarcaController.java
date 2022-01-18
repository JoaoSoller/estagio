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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import br.com.mgk.Estagio.Repository.MarcaRepository;

import br.com.mgk.Estagio.model.Marca;
import br.com.mgk.Estagio.model.Usuario;

@Controller 
@CrossOrigin(origins = "localhost:8000")
public class MarcaController {


	
	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private MarcaRepository marcaRepository;
	
	@RequestMapping(value ="/marca")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("marca");
		return mv;
	}
	
	
	@GetMapping(value = "marca/cadastro/listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Marca>> listaMarca() {

		List<Marca> marcas = marcaRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Marca>>(marcas, HttpStatus.OK);/* Retorna a lista em JSON */

	}
	
	@PostMapping(value = "marca/cadastro/salvar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Marca> salvar(@RequestBody Marca marca) { /* Recebe os dados para salvar */
	
			Marca user = marcaRepository.saveAndFlush(marca);
			return new ResponseEntity<Marca>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "marca/cadastro/atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Marca usuario) { /* Recebe os dados para salvar */
		

			Marca user = marcaRepository.saveAndFlush(usuario);	
			return new ResponseEntity<Marca>(user, HttpStatus.OK);
		
	}
	

	@DeleteMapping(value = "marca/cadastro/delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		marcaRepository.deleteById(iduser);

		return new ResponseEntity<String>("Marca deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "marca/cadastro/buscarmarcaid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Marca> buscarmarcaid(@RequestParam(name = "idmarca") Long idmarca) { /* Recebe os dados para consultar */

		Marca marca = marcaRepository.findById(idmarca).get();
		return new ResponseEntity<Marca>(marca, HttpStatus.OK);
	}
	
	@GetMapping(value = "marca/cadastro/buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Marca>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Marca> usuario = marcaRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Marca>>(usuario, HttpStatus.OK);
	}
	
}
