package br.ifpe.web3.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Noticia;

public interface NoticiaDAO extends JpaRepository<Noticia, Integer> {

	public List<Noticia> findByEmpresaId(Integer id);

}
