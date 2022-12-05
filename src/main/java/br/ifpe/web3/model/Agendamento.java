package br.ifpe.web3.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Agendamento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String servico;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate data;
	private LocalTime hora;
	private boolean status;
	@JoinColumn
	@OneToOne( cascade = CascadeType.ALL)
	private UsuarioCliente fk_cliente;
	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL)
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public UsuarioCliente getFk_cliente() {
		return fk_cliente;
	}
	public void setFk_cliente(UsuarioCliente fk_cliente) {
		this.fk_cliente = fk_cliente;
	}
	public UsuarioEmpresa getFk_Empresa() {
		return fk_Empresa;
	}
	public void setFk_Empresa(UsuarioEmpresa fk_Empresa) {
		this.fk_Empresa = fk_Empresa;
	}
}
