package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.mgk.Estagio.model.PromocaoItens;

@Repository
public interface PromocaoItensRepository extends JpaRepository<PromocaoItens, Long> {
	@Query(value = "select u from promocao_itens u where u.promocao.id=:id")
	List<PromocaoItens>buscarPorPromocao(long id);	
}
