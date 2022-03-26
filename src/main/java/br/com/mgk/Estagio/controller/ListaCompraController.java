package br.com.mgk.Estagio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.Repository.CompraRepository;
import br.com.mgk.Estagio.model.Compra;

@Controller
public class ListaCompraController {
	@Autowired
	private CompraRepository compraRepository;

	@RequestMapping(value ="/ListagemdeCompras")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("ListagemdeCompras");
		return mv;
	}


@PostMapping(value = "lista/mova/buscarPorCompra") /* mapeia a url */
@ResponseBody /* Descricao da resposta */
public ResponseEntity<List<Compra>>  buscarPorCompra() { /* Recebe os dados para consultar */
	
	List<Compra> usuario = compraRepository.findAll();

	return new ResponseEntity<List<Compra>>(usuario, HttpStatus.OK);
}

}
