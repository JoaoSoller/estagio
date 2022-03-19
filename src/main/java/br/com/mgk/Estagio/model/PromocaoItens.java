package br.com.mgk.Estagio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PromocaoItens {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compraitens")
	private long id;
	@ManyToOne
	private Produto produto;
	@ManyToOne
	private Promocao promocao;
	private double precoantigo;
	private double preconovo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Promocao getPromocao() {
		return promocao;
	}
	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}
	public double getPrecoantigo() {
		return precoantigo;
	}
	public void setPrecoantigo(double precoatual) {
		this.precoantigo = precoatual;
	}
	public double getPreconovo() {
		return preconovo;
	}
	public void setPreconovo(double preconovo) {
		this.preconovo = preconovo;
	}
	
	
}
