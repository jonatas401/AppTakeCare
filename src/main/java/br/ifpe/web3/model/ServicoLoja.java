package br.ifpe.web3.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class ServicoLoja {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private float valor;
	@Lob
	private byte[] foto;
	private int duracao;
	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL)
	private Servico fkservico;
	@JoinColumn
	@OneToOne()
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

	public Servico getFkservico() {
		return fkservico;
	}

	public void setFkservico(Servico fkservico) {
		this.fkservico = fkservico;
	}

	public UsuarioEmpresa getFk_estabelecimento() {
		return fk_Empresa;
	}

	public void setFk_estabelecimento(UsuarioEmpresa fk_Empresa) {
		this.fk_Empresa = fk_Empresa;
	}

	

}
