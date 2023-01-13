package br.ifpe.web3.DAO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ifpe.web3.model.ServicoLoja;

public interface ServicoLojaDAO extends JpaRepository<ServicoLoja,Integer> {
    
	@Query("SELECT s from ServicoLoja s where s.empresa.id like :id ")
	public List<ServicoLoja> listaServico(Integer id);
	
	
	
}
