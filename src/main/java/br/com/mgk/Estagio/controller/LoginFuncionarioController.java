package br.com.mgk.Estagio.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.FuncionarioRepository;
import br.com.mgk.Estagio.model.Compra;
import br.com.mgk.Estagio.model.CompraItens;
import br.com.mgk.Estagio.model.Funcionario;

@Controller
public class LoginFuncionarioController {
	private Funcionario funcionario;
	private FuncionarioRepository FuncionarioRepository;
	
	@RequestMapping(value ="/logonof")
	public ModelAndView LoginFUncionario() {
		ModelAndView mv = new ModelAndView("loginfuncionario");
		//buscarUsuarioLogado();
		return mv;
	}
	
	/*private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String email = autenticado.getName();
			funcionario = FuncionarioRepository.buscarPorEmail(email).get(0);
		} 
	}
*/
	}
