package br.com.mgk.Estagio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@SequenceGenerator(name = "seq_compraitens", sequenceName = "seq_compraitens", allocationSize = 1, initialValue = 1)


@Entity(name = "compra_itens")
public class CompraItens {
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compraitens")
	private long id;
	@ManyToOne
	private Produto produto;
	@ManyToOne
	private Compra compra;
	private double quantidade = 0;
	private double valor = 0;
	private double valoritem = 0;
	private String nome;
	private Long idproduto;
	
	public Long getIdproduto() {
		return idproduto;
	}
	public void setIdproduto() {
		this.idproduto = produto.getId();
	}
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
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getValoritem() {
		return valoritem;
	}
	public void setValoritem() {
		this.valoritem = valor * quantidade;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNome() {
		return nome;
	}
	public void setNome() {
		this.nome = produto.getNome();
	}
	public void setValoritem(double valoritem) {
		this.valoritem = valoritem;
	}
	
	
}
