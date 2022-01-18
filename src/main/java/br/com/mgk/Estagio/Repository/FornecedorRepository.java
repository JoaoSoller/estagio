package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{
	@Query(value = "select u from Fornecedor u where upper(trim(u.nomefantasia)) like %?1%")
	List<Fornecedor>buscarPorNome(String name);

	
}
