package br.com.mgk.Estagio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.mgk.Estagio.Repository.ExameRepository;
import br.com.mgk.Estagio.Repository.PacienteRepository;
import br.com.mgk.Estagio.model.Exame;
import br.com.mgk.Estagio.model.Paciente;

public class ControllerPaciente {

	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private ExameRepository exameRepository;
	@GetMapping(value = "paciente/cadastro/buscapacienteid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Paciente> buscapacienteid(@RequestParam(name = "idpaciente") Long idpaciente) { /* Recebe os dados para consultar */
		Paciente paciente = pacienteRepository.findById(idpaciente).get();
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
	}

	@GetMapping(value = "paciente/cadastro/buscaExameid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Exame>> buscaExameid(@RequestParam(name = "idpaciente") Long idpaciente) { /* Recebe os dados para consultar */
		List<Exame> exame = exameRepository.findAll();/* consulta to */
		List<Exame> novalista = new ArrayList<>();
		for (Exame item : exame) {
			if(item.getPaciente().getId()==idpaciente)// adiciona todos os exames de determinado cliente 
				novalista.add(item);
		}
		return new ResponseEntity<List<Exame>>(novalista, HttpStatus.OK);
	}

	
}
