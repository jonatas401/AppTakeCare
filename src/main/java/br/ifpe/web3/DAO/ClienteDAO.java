package br.ifpe.web3.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ifpe.web3.model.UsuarioCliente;

public interface ClienteDAO extends JpaRepository<UsuarioCliente, Integer> {

	public UsuarioCliente findByEmailAndSenha(String email, String senha);
	
	@Query("SELECT c from UsuarioCliente c where c.empresa.id like :id")
	public List<UsuarioCliente> listaClientesCadastrados(Integer id);

	public UsuarioCliente findByEmail(String email);

	public boolean existsByEmailAndCodigoRecSenha(String email, String codigo);
	

}
