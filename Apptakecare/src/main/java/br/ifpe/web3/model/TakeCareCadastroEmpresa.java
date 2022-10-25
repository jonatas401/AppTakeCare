package br.ifpe.web3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TakeCareCadastroEmpresa {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private String nome;
	private String sobreNome;
	private String Email;
	private String senha;
	private String nomeEmpresa;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String N;
	
	
	
	
	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
}
