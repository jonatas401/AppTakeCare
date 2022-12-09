package br.ifpe.web3.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaDAO extends JpaRepository<UsuarioEmpresa, Integer> {

	public UsuarioEmpresa findByEmailAndSenha(String email, String senha);
	//public UsuarioEmpresa findByNomeEmpresaAndId(String nomeEmpresa, Integer id);
	
	
	
}
