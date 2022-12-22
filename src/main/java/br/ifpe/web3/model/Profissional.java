package br.ifpe.web3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Profissional {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private String nome;
	private String numero;
	private String cpf;	
	@JoinColumn
	@ManyToOne
	private UsuarioEmpresa empresa;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public UsuarioEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(UsuarioEmpresa empresa) {
		this.empresa = empresa;
	}
	

	
}
