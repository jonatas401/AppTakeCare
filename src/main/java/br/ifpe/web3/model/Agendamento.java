package br.ifpe.web3.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Agendamento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JoinColumn
	@ManyToOne
	private ServicoLoja servico;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate data;
	private LocalTime hora;
	private boolean status;
	@JoinColumn
	@OneToOne
	private UsuarioCliente cliente;
	@JoinColumn
	@OneToOne
	private UsuarioEmpresa empresa;
	private String profissional;
	@JoinColumn
	@OneToOne
	
	
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
	public  LocalTime getHora() {
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
	public ServicoLoja getServico() {
		return servico;
	}
	public void setServico(ServicoLoja servico) {
		this.servico = servico;
	}
	public UsuarioCliente getCliente() {
		return cliente;
	}
	public void setCliente(UsuarioCliente cliente) {
		this.cliente = cliente;
	}
	public UsuarioEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(UsuarioEmpresa empresa) {
		this.empresa = empresa;
	}
	public String getProfissional() {
		return profissional;
	}
	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}
	
	
	
	
	
	
}
