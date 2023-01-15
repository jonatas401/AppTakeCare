package br.ifpe.web3.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.UsuarioEmpresa;

public interface EmpresaDAO extends JpaRepository<UsuarioEmpresa, Integer> {

	public UsuarioEmpresa findByEmailAndSenha(String email, String senha);
	//public UsuarioEmpresa findByNomeEmpresaAndId(String nomeEmpresa, Integer id);

	public List<UsuarioEmpresa> findByNomeEmpresaContaining(String pesquisa);

	public UsuarioEmpresa findByEmail(String email);

	public boolean existsByEmailAndCodigoRecSenha(String email, String codigo);

	public List<UsuarioEmpresa> findByTipoEmpresaId(Integer id);
	
	
	
}
