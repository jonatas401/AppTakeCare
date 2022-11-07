package br.ifpe.web3.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaDAO extends JpaRepository<CadastroEmpresa, Integer> {

	public CadastroEmpresa findByEmailAndSenha(String email, String senha);
	public CadastroEmpresa findByNomeEmpresaAndId(String nomeEmpresa, Integer id);

}
