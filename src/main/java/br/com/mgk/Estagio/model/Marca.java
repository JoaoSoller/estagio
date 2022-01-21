package br.com.mgk.Estagio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_marca", sequenceName = "seq_marca", allocationSize = 1, initialValue = 1)
public class Marca {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_marca")
	private long id;
	private String nome;
	private String Slogam;
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
	public String getSlogam() {
		return Slogam;
	}
	public void setSlogam(String slogam) {
		Slogam = slogam;
	}
	
	
}
