package br.com.mgk.Estagio.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

@SequenceGenerator(name = "seq_compra", sequenceName = "seq_compra", allocationSize = 1, initialValue = 1)

@Entity
public class Compra {
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compra")
	private long id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	private Double total;
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
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
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
