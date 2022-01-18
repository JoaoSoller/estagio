package br.com.mgk.Estagio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import br.com.mgk.Estagio.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%")
	List<Usuario>buscarPorNome(String name);
	
	
	@Query("Select u from Usuario u WHERE u.login=:login")
	Usuario buscarPorLogin(String login);
	
}
