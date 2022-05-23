package br.com.mgk.Estagio.relatorio;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.mgk.Estagio.service.PDFGeneratorService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PDFExportController {
	
	private String nome="";
    private final PDFGeneratorService pdfGeneratorService;

    public PDFExportController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    
    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.export(response,nome);
    }
    
    @GetMapping(value = "/mudareltod") /* mapeia a url */
	@ResponseBody
	public void resultex(@RequestParam(name = "nome") String nome) { /* Recebe os dados para consultar */
		this.nome=nome;
		}
    
    @RequestMapping(value ="/reltodprod")
	public ModelAndView marca() {
		ModelAndView mv = new ModelAndView("reltodprod");
		return mv;
	}
}