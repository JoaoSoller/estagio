package br.com.mgk.Estagio.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "seq_pedido", sequenceName = "seq_pedido", allocationSize = 1, initialValue = 1)

public class Pedido {
	
private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
	private long id;
	@ManyToOne
	private Cliente cliente;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCompra = new Date();
	private Double frete;
	private Double valorTotal=0.;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	public Double getFrete() {
		return frete;
	}
	public void setFrete(Double frete) {
		this.frete = frete;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
