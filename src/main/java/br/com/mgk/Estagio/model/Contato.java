package br.com.mgk.Estagio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@SequenceGenerator(name = "seq_contato", sequenceName = "seq_contato", allocationSize = 1, initialValue = 1)

@Entity
public class Contato {
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contato")
	private long id;
	private String contato;
	private String descricao;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
