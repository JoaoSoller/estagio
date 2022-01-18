package br.com.mgk.Estagio.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import lombok.Builder;

@Entity
@SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1, initialValue = 1)
public class Cliente {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
	private long id;
	private long nivel;
	private String cpf;
	private String nome;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtnasc;
	
	

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
	public Date getDtnascimento() {
		return dtnasc;
	}
	public void setDtnascimento(Date dtnascimento) {
		this.dtnasc = dtnascimento;
	}
	public long getNivel() {
		return nivel;
	}
	public void setNivel(long nivel) {
		this.nivel = nivel;
	}
	
}
