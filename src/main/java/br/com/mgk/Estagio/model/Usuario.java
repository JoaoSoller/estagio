package br.com.mgk.Estagio.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private long id;
	private String nome;
	private String login;
	private String senha;
	private String alt;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtnasc;
	
	public LocalDate getDtAdmissao() {
		return dtnasc;
	}
	public void setDtAdmissao(LocalDate dtAdmissao) {
		this.dtnasc = dtAdmissao;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
