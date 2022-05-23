package br.com.mgk.Estagio.relatorio;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.service.PDFCurvaABCService;

@Controller
public class PDFCurvaABCController {
	
	private String nome="";
	private final PDFCurvaABCService pdfCurvaABCService;
	
	public PDFCurvaABCController(PDFCurvaABCService pdfCurvaABCService) {
		this.pdfCurvaABCService = pdfCurvaABCService;
	}
	
	
	@GetMapping("/pdfcurvaabc/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfCurvaABCService.export(response,nome);
    }
	
	 @GetMapping(value = "/mudarabc") /* mapeia a url */
		@ResponseBody
		public void resultex(@RequestParam(name = "nome") String nome) { /* Recebe os dados para consultar */
			this.nome=nome;
			}
	    
	    @RequestMapping(value ="/relabc")
		public ModelAndView marca() {
			ModelAndView mv = new ModelAndView("relabc");
			return mv;
		}
}
