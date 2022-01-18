package br.com.mgk.Estagio.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;

@Entity
@SequenceGenerator(name = "seq_funcionario", sequenceName = "seq_funcionario", allocationSize = 1, initialValue = 1)
public class Funcionario {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcionario")
	private long id;
	private String cargo;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtnasc;
	private String cpf;
	private String nome;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDtAdmissao() {
		return dtnasc;
	}
	public void setDtAdmissao(LocalDate dtAdmissao) {
		this.dtnasc = dtAdmissao;
	}
	
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public LocalDate getDt() {
		return dtnasc;
	}
	public void setDt(LocalDate dtAdmissao) {
		this.dtnasc = dtAdmissao;
	}
}
