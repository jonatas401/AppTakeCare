package br.ifpe.web3.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Planos;

public interface PlanosDAO extends JpaRepository<Planos, Integer> {

}
