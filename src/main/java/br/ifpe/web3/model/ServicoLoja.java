package br.ifpe.web3.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;


@Entity
public class ServicoLoja {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private float valor;
	private int duracao;
	private String descricao;
	@JoinColumn
	@ManyToOne
	private UsuarioEmpresa empresa;
	@Lob
	private byte[] foto;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UsuarioEmpresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(UsuarioEmpresa empresa) {
		this.empresa = empresa;
	}

	

}
