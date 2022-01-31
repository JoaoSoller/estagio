package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.Cliente;

@Repository
public interface ClienteRepository extends  JpaRepository<Cliente,Long> {
	@Query(value = "select u from Cliente u where upper(trim(u.nome)) like %?1%")
	List<Cliente>buscarPorNome(String name);	
	
	@Query("from Cliente where login=?1")
	public List<Cliente> buscarClienteEmail(String email);


}
