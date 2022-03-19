package br.com.mgk.Estagio.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@SequenceGenerator(name = "seq_compra", sequenceName = "seq_compra", allocationSize = 1, initialValue = 1)

@Entity
public class Compra {
	private static final long serialVersionUID = 1L;
	@Id 

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compra")
	private long id;
	@Temporal(TemporalType.DATE)
	private Date data;
	private Double total = 0.0;
	@ManyToOne
	private Funcionario funcionario;
	@ManyToOne
	private Fornecedor fornecedor;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
