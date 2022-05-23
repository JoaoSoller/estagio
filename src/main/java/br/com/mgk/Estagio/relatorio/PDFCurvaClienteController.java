package br.com.mgk.Estagio.relatorio;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.service.PDFCurvaClienteService;

@Controller
public class PDFCurvaClienteController {
	
	private String nome="";
	private final PDFCurvaClienteService pdfCurvaClienteService;
	
	public PDFCurvaClienteController(PDFCurvaClienteService pdfCurvaClienteService) {
		this.pdfCurvaClienteService = pdfCurvaClienteService;
	}

	@RequestMapping(value ="/relprod")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("relprod");
		return mv;
	}
	
	
	@GetMapping(value = "/mudaprodutorel") /* mapeia a url */
	@ResponseBody
	public void resultex(@RequestParam(name = "nome") String nome) { /* Recebe os dados para consultar */
		this.nome=nome;
		}

	
	@GetMapping("/pdfclientemais/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfCurvaClienteService.export(response,nome);
    }
}
