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

import br.com.mgk.Estagio.Repository.UsuarioRepository;
import br.com.mgk.Estagio.model.Usuario;

@CrossOrigin(origins = "localhost:8000")
@Controller
public class ControllerDeAcessoController {
	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private UsuarioRepository usuarioRepository;
	
	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */

	@RequestMapping(value ="/controle")
	public String Controle() {
		return "controle.html";
	}
	

	
	@PostMapping(value = "controle/confirmar") /* mapeia a url */
	public String confirmar(Model model, String login, String senha){  /* Recebe os dados para salvar */

		Usuario usuarios = usuarioRepository.buscarPorLogin(login);
		if(usuarios!= null && usuarios.getLogin().equals(login)==true && usuarios.getSenha().equals(senha)==true)
		{
			return "acesso.html";
		}
		else
		{	
			model.addAttribute("erro","usuario ou senha invalido");
			return "controle.html";
		}
	}
	
}
