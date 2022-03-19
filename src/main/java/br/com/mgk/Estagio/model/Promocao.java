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
		@Temporal(TemporalType.DATE)
		private Date dtini;
		@Temporal(TemporalType.DATE)
		private Date dtfim;
		private String nome;
		@ManyToOne
		private Funcionario funcionario;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		public Date getDtini() {
			return dtini;
		}
		public void setDtini(Date dtini) {
			this.dtini = dtini;
		}
		public Date getDtfim() {
			return dtfim;
		}
		public void setDtfim(Date dtfim) {
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
		
}
