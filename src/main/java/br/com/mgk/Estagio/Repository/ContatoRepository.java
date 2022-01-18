package br.com.mgk.Estagio.Repository; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.mgk.Estagio.model.Contato;

@Repository 
public interface ContatoRepository extends JpaRepository <Contato, Long>{
	@Query(value = "select u from Contato u where upper(trim(u.contato)) like %?1%")
	List<Contato>buscarPorNome(String name);
}
