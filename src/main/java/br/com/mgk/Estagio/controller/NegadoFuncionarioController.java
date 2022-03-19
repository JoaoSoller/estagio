package br.com.mgk.Estagio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NegadoFuncionarioController {
		@GetMapping("/negadoFuncionario")
		public ModelAndView negadoFuncionario() {
			ModelAndView mv =  new ModelAndView("/negadoFuncionario");
			
			return mv;
		}
		
}

