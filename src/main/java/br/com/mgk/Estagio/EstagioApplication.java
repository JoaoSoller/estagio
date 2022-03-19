package br.com.mgk.Estagio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.mgk.Estagio.model.Fretenator;
import br.com.mgk.Estagio.model.FretenatorResult;
import br.com.mgk.Estagio.model.FretenatorResultItem;
@ComponentScan
@SpringBootApplication
public class EstagioApplication {


	public static void main(String[] args) {
		SpringApplication.run(EstagioApplication.class, args);;// onde roda o spring
		
		Fretenator fretenator = new Fretenator();
		fretenator.codServico("41106");
		fretenator.codFormato(1);
		fretenator.altura(11d);
		fretenator.largura(12d);
		fretenator.comprimento(16d);
		fretenator.peso("0,450");
		fretenator.cepOrigem("19050000");
		fretenator.cepDestino("06385125");
		FretenatorResult result = fretenator.result();
		FretenatorResultItem servico = result.getServico(41106);
		
		System.out.println(servico.getErro());
		System.out.println(servico.getMensagemDeErro());
		System.out.println(servico.getPrazo());
		System.out.println(servico.getValor());
		System.out.println(servico.getEntregaDomiciliar());
		System.out.println(servico.getEntregaSabado());
	}

}
