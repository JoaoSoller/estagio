package br.com.mgk.Estagio.model;

import java.time.LocalDate;
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
	@SequenceGenerator(name = "seq_promocao", sequenceName = "seq_promocao", allocationSize = 1, initialValue = 1)
	public class Promocao {
		
		@Id 
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_promocao")
		private long id;
		private String dtini;
		private String dtfim;
		private String nome;
		private double porcentagem;
		@ManyToOne
		private Funcionario funcionario;
		private long posfun =0;
		private String categoria;
		
		
		public long getPosfun() {
			return posfun;
		}
		public void setPosfun(long posfun) {
			this.posfun = posfun;
		}
		public long getId() {
			return id;
		}
		public double getPorcentagem() {
			return porcentagem;
		}
		public void setPorcentagem(double porcentagem) {
			this.porcentagem = porcentagem;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		public String getDtini() {
			return dtini;
		}
		public void setDtini(String dtini) {
			this.dtini = dtini;
		}
		public String getDtfim() {
			return dtfim;
		}
		public void setDtfim(String dtfim) {
			this.dtfim = dtfim;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public Funcionario getFuncionario() {
			return funcionario;
		}
		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
		}
		public String getCategoria() {
			return categoria;
		}
		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}
		
}
