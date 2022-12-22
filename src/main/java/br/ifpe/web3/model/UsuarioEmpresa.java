package br.ifpe.web3.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Entity
public class UsuarioEmpresa {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String numero;
	private String email;
	private String senha;
	@NotBlank(message ="deve ser preenchido")
	private String nomeEmpresa;
	@NotNull(message = "tipo deve ser selecionado")
	private TipoEmpresa tipoEmpresa;
	private Endereco endereco;
	@Lob
	@Column(columnDefinition="mediumblob")
	private Blob fotoPerfil;
	//private UsuarioCliente[] fk_cliente;
	
	
	
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
	
	public Blob getFotoPortifolio() {
		return fotoPerfil;
	}
	public void setFotoPortifolio(Blob fotoPerfil) {
		
		
		this.fotoPerfil = fotoPerfil;
	}
	
	
}
	