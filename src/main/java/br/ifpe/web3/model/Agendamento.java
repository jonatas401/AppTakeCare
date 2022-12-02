package br.ifpe.web3.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Agendamento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate data;
	private LocalTime hora;
	private boolean confirmacao;
	@JoinColumn
	@OneToOne
	private UsuarioCliente fk_cliente;
	@JoinColumn
	@OneToOne
	private UsuarioEmpresa fk_Empresa;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public boolean isConfirmacao() {
		return confirmacao;
	}
	public void setConfirmacao(boolean confirmacao) {
		this.confirmacao = confirmacao;
	}
	public UsuarioCliente getFk_cliente_id() {
		return fk_cliente;
	}
	public void setFk_cliente_id(UsuarioCliente fk_cliente) {
		this.fk_cliente= fk_cliente;
	}
	public UsuarioEmpresa getFk_Empresa_id() {
		return fk_Empresa;
	}
	public void setFk_Empresa_id(UsuarioEmpresa fk_Empresa) {
		this.fk_Empresa = fk_Empresa;
	}
}
