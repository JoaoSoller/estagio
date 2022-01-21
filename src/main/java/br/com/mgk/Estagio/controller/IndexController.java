package br.com.mgk.Estagio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.ProdutoRepository;

@Controller
@CrossOrigin(origins = "localhost:8000")
public class IndexController {

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@GetMapping("/inicial")
	public ModelAndView inicial() {
		ModelAndView mv =  new ModelAndView("/inicial");	
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}
	
}
