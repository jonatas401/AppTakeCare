package br.ifpe.web3.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoDAO extends JpaRepository<Agendamento, Integer> {
		
		public List<Agendamento>  findByEmpresaId(Integer id);
}
