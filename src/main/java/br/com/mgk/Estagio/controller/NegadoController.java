package br.com.mgk.Estagio.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NegadoController {
	
	@GetMapping("/negadoCliente")
	public ModelAndView negadoCliente() {
		ModelAndView mv =  new ModelAndView("/negadoCliente");
		
		return mv;
	}
	
}
