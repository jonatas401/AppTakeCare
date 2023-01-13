package br.ifpe.web3.DAO;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.ifpe.web3.model.ClienteAgendado;
import br.ifpe.web3.model.UsuarioCliente;



public interface ClienteAgendadoDAO extends JpaRepository<ClienteAgendado, Integer> {
	
	@Modifying
	@Query(value = "insert into ClienteAgendado (id,nome,numero) values (:id,:nome, :numero)", nativeQuery = true)
	void Cadastrar(Integer id,String nome,String numero);

	

	
	
	
}
