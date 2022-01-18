package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long>{
	@Query(value = "select u from Marca u where upper(trim(u.nome)) like %?1%")
	List<Marca>buscarPorNome(String name);

	
}
