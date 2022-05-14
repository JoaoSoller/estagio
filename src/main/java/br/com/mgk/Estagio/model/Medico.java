package br.com.mgk.Estagio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_medico", sequenceName = "seq_medico", allocationSize = 1, initialValue = 1)
public class Medico {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_medico")
	private int id;
	private String RG;
	private String CRM;
	public String getRG() {
		return RG;
	}
	public void setRG(String RG) {
		RG = RG;
	}
	public String getCRM() {
		return CRM;
	}
	public void setCRM(String CRM) {
		CRM = CRM;
	}
	
}
