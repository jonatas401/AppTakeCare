package br.ifpe.web3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;



@Entity
public class UsuarioEmpresa {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String numero;
	@NotBlank(message ="deve ser preenchido")
	private String email;
	@NotBlank(message ="deve ser preenchido")
	private String senha;
	@NotBlank(message ="deve ser preenchido")
	private String nomeEmpresa;
	@JoinColumn
	@OneToOne
	private TipoEmpresa tipoEmpresa;
	private Endereco endereco;
	@JoinColumn
	@OneToOne
	private Planos plano;
	@Lob
	@Column(columnDefinition="mediumblob")
	private byte[] fotoPerfil;
	
	
	
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}
	
	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}
	
	public Planos getPlano() {
		return plano;
	}
	public void setPlano(Planos plano) {
		this.plano = plano;
	}
	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	
}
	