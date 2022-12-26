package br.ifpe.web3.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CadastroClienteDAO extends JpaRepository<CadastroCliente, Integer> {

		@Query("SELECT c from CadastroCliente c where c.empresa.id like :id")
		public List<CadastroCliente> listaClientesCadastrados(Integer id);
		
}
