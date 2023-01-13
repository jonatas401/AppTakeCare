package br.ifpe.web3.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ifpe.web3.model.ProfissionalServico;

public interface ProfissionalServicoDAO extends JpaRepository<ProfissionalServico, Integer> {
	
	@Query("SELECT p from ProfissionalServico p where p.profissional.empresa.id like :id ")
	public List<ProfissionalServico> listaProfissional(Integer id);

	
	@Query("SELECT p from ProfissionalServico p where p.servico.id = :id")
	public List<ProfissionalServico> buscaPorServico(Integer id);
	
	public List<ProfissionalServico> findByProfissionalId(Integer id);
	
}
