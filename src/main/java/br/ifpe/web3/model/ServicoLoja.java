package br.ifpe.web3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ServicoLoja {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private float valor;
	private byte foto;
	private int duracao;
	
	@JoinColumn
	@OneToOne
	private Servico fk_servico;
	
	@JoinColumn
	@OneToOne
	private UsuarioEmpresa fk_Empresa;
	
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

	public byte getFoto() {
		return foto;
	}

	public void setFoto(byte foto) {
		this.foto = foto;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public Servico getFk_servico() {
		return fk_servico;
	}

	public void setFk_servico(Servico fk_servico) {
		this.fk_servico = fk_servico;
	}

	public UsuarioEmpresa getFk_estabelecimento() {
		return fk_Empresa;
	}

	public void setFk_estabelecimento(UsuarioEmpresa fk_Empresa) {
		this.fk_Empresa = fk_Empresa;
	}


	

}
