package br.com.mgk.Estagio.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import br.com.mgk.Estagio.Repository.ClienteRepository;
import br.com.mgk.Estagio.model.Cliente;
import br.com.mgk.Estagio.model.Funcionario;
import br.com.mgk.Estagio.model.Produto;

@Service
public class PDFUsuariosService {
	@Autowired
		ClienteRepository funcionariorepository;
	public void export(HttpServletResponse response,String nome) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        /*Paragraph paragraph = new Paragraph(" MGK DIESEL AUTOPEÇAS ", fontTitle);
   
        Paragraph paragraph3 = new Paragraph(" ", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(paragraph3);
        
        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("yyyy/MM/dd HH:mm-> "+dtf4.format(LocalDateTime.now()));
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        Paragraph paragraph5 = new Paragraph("Relatorio de Usuarios                             				                                                       "+dtf4.format(LocalDateTime.now()));
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);	
        paragraph5.setSpacingAfter(100);
        document.add(paragraph5); */
		 

        
       /* document.add(paragraph3);
        Paragraph paragraph4 = new Paragraph("CPF                                CIDADE                      NOME", fontTitle);
        document.add(paragraph4);
        */
		 PdfPTable table = new PdfPTable(3);
		    
			table.setWidthPercentage(80);
			table.setWidths(new int[] { 10, 10,10 });
			
			PdfPCell hcell2;
			hcell2 = new PdfPCell(new Phrase("MGK DIESEL", fontTitle));
			hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell2.setColspan(3);
			table.addCell(hcell2);

		
			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("CPF", fontTitle));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Nome", fontTitle));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cidade", fontTitle));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
		
			
        List<Cliente> prod;
        if(nome.isEmpty())
        	prod = funcionariorepository.findAll();
        else
        	prod = funcionariorepository.buscarPorNome(nome.toUpperCase());
        for (Cliente produto : prod) {
        	
        /*	Paragraph paragraph2 = new Paragraph(produto.getCpf() +"                               "+ produto.getCidade()+"                  "+ produto.getNome());
        	
        	paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        	document.add(paragraph2);*/
        	
        	PdfPCell cell;

			cell = new PdfPCell(new Phrase(produto.getCpf()));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(produto.getNome()));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(produto.getCidade()));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);
		} 
		
		
		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);
		Paragraph paragraph5 = new Paragraph("Pagina  "+document.getPageNumber()+1 + "                                                                                                      "+dtf4.format(LocalDateTime.now()));
	    paragraph5.setAlignment(Paragraph.ALIGN_LEFT);	
	    document.add(paragraph5);
		document.close();
		
    }
}
