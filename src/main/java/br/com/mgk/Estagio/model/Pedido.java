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
	private String dataCompra;
	private Double frete;
	private Double valorTotal=0.0;
	private String numerocartao;
	private String status;
	private String motivo;
	private Double desconto = 0.0;
	private Double valorFinal=0.0;
	private String txtdesconto="0.0";
	private String txtvalorfinal="0.0";
	private String txtvalortotal="0.0";
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Double getValorFinal() {
		return valorFinal;
	}
	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNumerocartao() {
		return numerocartao;
	}
	public void setNumerocartao(String numerocartao) {
		this.numerocartao = numerocartao;
	}
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
	public String getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(String dataCompra) {
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
	public String getTxtdesconto() {
		return txtdesconto;
	}
	public void setTxtdesconto(String txtdesconto) {
		this.txtdesconto = txtdesconto;
	}
	public String getTxtvalorfinal() {
		return txtvalorfinal;
	}
	public void setTxtvalorfinal(String txtvalorFinal) {
		this.txtvalorfinal = txtvalorFinal;
	}
	public String getTxtvalortotal() {
		return txtvalortotal;
	}
	public void setTxtvalortotal(String txtvalorTotal) {
		this.txtvalortotal = txtvalorTotal;
	}
	
	
}
