package br.com.mgk.Estagio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@ComponentScan
@SpringBootApplication
public class EstagioApplication {


	public static void main(String[] args) {
		SpringApplication.run(EstagioApplication.class, args);;// onde roda o spring
	}

}
