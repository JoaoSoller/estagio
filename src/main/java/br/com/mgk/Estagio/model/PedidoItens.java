package br.com.mgk.Estagio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "pedido_itens")
	@SequenceGenerator(name = "seq_pedidoitens", sequenceName = "seq_pedidoitens", allocationSize = 1, initialValue = 1)

	public class PedidoItens {
		
	private static final long serialVersionUID = 1L;
		
		@Id 
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedidoitens")
		private long id;
		@ManyToOne
		private Produto produto;
		@ManyToOne
		private Pedido pedido;
		private Integer quantidade = 0;
		private Double valorUnitario=0.0;
		private String txtvalortotal=" ";
		private String txtdesconto="0.0";
		private Double desconto=0.0;
		private Double acrescimo=0.0;
		private Double ValorTotal= 0.0;
		
		public Double getValorTotal() {
			return ValorTotal;
		}
		public void setValorTotal(Double valorTotal) {
			ValorTotal = valorTotal;
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
		public Pedido getPedido() {
			return pedido;
		}
		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
		}
		public Integer getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}
		public Double getValorUnitario() {
			return valorUnitario;
		}
		public void setValorUnitario(Double valorUnitario) {
			this.valorUnitario = valorUnitario;
		}
		public Double getDesconto() {
			return desconto;
		}
		public void setDesconto(Double desconto) {
			this.desconto = desconto;
		}
		public Double getAcrescimo() {
			return acrescimo;
		}
		public void setAcrescimo(Double acrescimo) {
			this.acrescimo = acrescimo;
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
		public String getTxtvalortotal() {
			return txtvalortotal;
		}
		public void setTxtvalortotal(String txtvalortotal) {
			this.txtvalortotal = txtvalortotal;
		}
	
		
		
}
