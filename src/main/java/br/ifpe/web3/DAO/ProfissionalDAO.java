package br.ifpe.web3.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ifpe.web3.model.Profissional;

public interface ProfissionalDAO extends JpaRepository<Profissional, Integer>{
	@Query("SELECT p from Profissional p where p.empresa.id like :id ")
	public List<Profissional> listaProfissional(Integer id);

	@Query("SELECT p from Profissional p where p.numero like :numero and p.empresa.id like :id")
	public Profissional pesquisaProfissional(String numero, Integer id);

	

}
