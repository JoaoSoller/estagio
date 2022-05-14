package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.PedidoItens;


@Repository
public interface PedidoItensRepository  extends JpaRepository<PedidoItens, Long> {
	
	@Query(value = "select u from pedido_itens u where pedido_id=:id")
	List<PedidoItens>buscarPorPedido(long id);	
}
