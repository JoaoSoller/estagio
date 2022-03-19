package br.com.mgk.Estagio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mgk.Estagio.model.PromocaoItens;

@Repository
public interface PromocaoItensRepository extends JpaRepository<PromocaoItens, Long> {

}
