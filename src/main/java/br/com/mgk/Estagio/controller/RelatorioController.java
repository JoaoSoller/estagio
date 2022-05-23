package br.com.mgk.Estagio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.PedidoRepository;
import br.com.mgk.Estagio.model.Pedido;

@Controller
public class RelatorioController {

	@RequestMapping(value ="/dashboard")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("dashboard");
		return mv;
	}
	
	@RequestMapping(value ="/dashboardcadastros")
	public ModelAndView dash() {
		ModelAndView mv = new ModelAndView("dashboardcadastros");
		return mv;
	}
	
	@RequestMapping(value ="/dashboardmovimentacao")
	public ModelAndView dashmov() {
		ModelAndView mv = new ModelAndView("dashboardmovimentacao");
		return mv;
	}
	
	@RequestMapping(value ="/dashboardrelatorio")
	public ModelAndView dashrel() {
		ModelAndView mv = new ModelAndView("dashboardrelatorio");
		return mv;
	}
	@RequestMapping(value ="/motivo")
	public ModelAndView motivo() {
		ModelAndView mv = new ModelAndView("motivo");
		return mv;
	}
	
}
