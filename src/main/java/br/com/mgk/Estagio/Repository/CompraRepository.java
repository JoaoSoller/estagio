package br.com.mgk.Estagio.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.Cliente;
import br.com.mgk.Estagio.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>{
	
	@Query("from Cliente where login=?1")
	public List<Cliente> buscarClienteEmail(String email);

}
