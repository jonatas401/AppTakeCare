package br.ifpe.web3.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDAO extends JpaRepository<UsuarioCliente, Integer> {

	public UsuarioCliente findByEmailAndSenha(String email, String senha);
	void save(Endereco endereco);

}
