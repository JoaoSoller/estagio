package br.com.mgk.Estagio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.model.Produto;

@Controller
public class ControllerInicio {



	@Autowired
	private ProdutoRepository produtoRepositorio;
	
	private List<Produto> lista = new ArrayList();
	private int id=0;
	List<Produto> listanova = new ArrayList();

	
	
	
	
}
