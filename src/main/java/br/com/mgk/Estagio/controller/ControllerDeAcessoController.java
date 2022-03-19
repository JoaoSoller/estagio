package br.com.mgk.Estagio.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
			return "principal.html";
		}
		else
		{	
			model.addAttribute("erro","usuario ou senha invalido");
			return "controle.html";
		}
	}
	
}
