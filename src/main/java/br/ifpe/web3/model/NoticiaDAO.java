package br.ifpe.web3.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaDAO extends JpaRepository<Noticia, Integer> {

	public List<Noticia> findByEmpresaId(Integer id);

}
