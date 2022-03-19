package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.CompraItens;

@Repository
public interface CompraItensRepository extends JpaRepository<CompraItens, Long>{
	
	@Query(value = "select u from compra_itens u where u.compra.id=:id")
	List<CompraItens>buscarPorCompra(long id);	
}
