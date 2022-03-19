package br.com.mgk.Estagio.model;

	import java.util.HashMap;
	import java.util.Map;

	public class FretenatorResult {
	    private Map<Integer, FretenatorResultItem> servicos = new HashMap<Integer, FretenatorResultItem>();

	    public void addServico(FretenatorResultItem item) {
	        servicos.put(item.getCodigo(), item);
	    }

	    public FretenatorResultItem getServico(Integer codigo) {
	        return servicos.get(codigo);
	    }
	}

