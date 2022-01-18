package br.com.mgk.Estagio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.Categoria;
import br.com.mgk.Estagio.model.Marca;
import br.com.mgk.Estagio.model.Produto;
import br.com.mgk.Estagio.model.Usuario;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	@Query(value = "select u from Produto u where upper(trim(u.nome)) like %?1%")
	List<Produto>buscarPorNome(String name); 
	
	@Query("Select u from Marca u WHERE u.id=:marca")
	Marca buscarPorMarca(long marca);
	
	@Query("Select u from Categoria u WHERE u.id=:categoria")
	Categoria buscarPorCategoria(long categoria);
}
