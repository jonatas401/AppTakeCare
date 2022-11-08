package br.ifpe.web3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class CadastroEmpresa {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String sobreNome;
	private String email;
	private String senha;
	private String nomeEmpresa;
	private TipoEmpresa tipoEmpresa;
	private Endereco endereco;
	private byte[] fotoPortifolio;
	
	
	
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
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
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
	
	public byte[] getFotoPortifolio() {
		return fotoPortifolio;
	}
	public void setFotoPortifolio(byte[] fotoPortifolio) {
		this.fotoPortifolio = fotoPortifolio;
	}
	
	
}
	