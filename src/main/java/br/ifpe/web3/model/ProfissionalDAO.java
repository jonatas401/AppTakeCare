package br.ifpe.web3.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfissionalDAO extends JpaRepository<Profissional, Integer>{

	
	@Query("SELECT p from Profissional p where p.empresa.id like :id ")
	public List<Profissional> listaProfissional(Integer id);
}
