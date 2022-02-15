package br.com.mgk.Estagio.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



@Entity
@SequenceGenerator(name = "seq_funcionario", sequenceName = "seq_funcionario", allocationSize = 1, initialValue = 1)
public class Funcionario {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcionario")
	private long id;
	private String cargo;
	@Temporal(TemporalType.DATE)
	private Date dtnasc;
	private String cpf;
	private String nome;
	private String login;
	private String senha;
	
	public Date getDtnasc() {
		return dtnasc;
	}
	public void setDtnasc(Date dtnasc) {
		this.dtnasc = dtnasc;
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
	
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

}
