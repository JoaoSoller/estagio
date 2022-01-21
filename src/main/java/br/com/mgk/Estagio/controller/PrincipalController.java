package br.com.mgk.Estagio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "localhost:8000")
public class PrincipalController {

	@RequestMapping(value ="/principal")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("principal");
		return mv;
	}
	
}
