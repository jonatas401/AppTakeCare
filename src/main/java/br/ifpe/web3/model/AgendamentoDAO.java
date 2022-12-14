package br.ifpe.web3.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgendamentoDAO extends JpaRepository<Agendamento, Integer> {
		
		public List<Agendamento>  findByEmpresaId(Integer id);
		
		@Query("SELECT a FROM Agendamento a where a.empresa.id like :id")
		public List<Agendamento> listarAgendamentosEmpresa(Integer id);
		
		@Query("SELECT a FROM Agendamento a where a.cliente.id like :id")
		public List<Agendamento> listarAgendamentosCliente(Integer id);
}
