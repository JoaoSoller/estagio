package br.com.mgk.Estagio.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import br.com.mgk.Estagio.Repository.ProdutoRepository;
import br.com.mgk.Estagio.model.Produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PDFGeneratorService {
	
	@Autowired
    ProdutoRepository produtoRepository;
	
	public void export(HttpServletResponse response,String nome) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        
        PdfPTable table = new PdfPTable(4);
	    
		table.setWidthPercentage(80);
		table.setWidths(new int[] { 10, 10,10,10 });
		
		PdfPCell hcell2;
		hcell2 = new PdfPCell(new Phrase(" MGK DIESEL  ", fontTitle));
		hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell2.setColspan(4);
		table.addCell(hcell2);

		/*hcell2 = new PdfPCell(new Phrase()));
		hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell2);

		hcell2 = new PdfPCell(new Phrase("      ", fontTitle));
		hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell2);
		
		hcell2 = new PdfPCell(new Phrase("      ", fontTitle));
		hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell2);*/
		
		PdfPCell hcell;
		hcell = new PdfPCell(new Phrase("ID", fontTitle));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		hcell = new PdfPCell(new Phrase("Nome", fontTitle));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		hcell = new PdfPCell(new Phrase("Preco", fontTitle));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		
		hcell = new PdfPCell(new Phrase("Estoque", fontTitle));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		List<Produto> prod;
        if(nome.isEmpty())
        	prod = produtoRepository.findAll();
        else
        	prod = produtoRepository.buscarPorNome(nome.toUpperCase());
        for (Produto produto : prod) {
        	
        	PdfPCell cell;

			cell = new PdfPCell(new Phrase(String.valueOf(produto.getId())));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(produto.getNome()));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(produto.getPreco())));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(produto.getQuantidadeEstoque())));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);
		} 
        PdfWriter.getInstance(document, out);
		document.open();
		Paragraph paragraph5 = new Paragraph("Pagina  "+document.getPageNumber()+1 + "                                                                                                      "+dtf4.format(LocalDateTime.now()));
	    paragraph5.setAlignment(Paragraph.ALIGN_LEFT);	
	    document.add(table);
	    document.add(paragraph5);
		

		document.close();
    }
}