package br.ifpe.web3.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDAO extends JpaRepository<CadastroCliente, Integer> {

	public CadastroCliente findByEmailAndSenha(String email, String senha);
	void save(Endereco endereco);

}
