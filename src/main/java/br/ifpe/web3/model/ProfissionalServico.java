package br.ifpe.web3.model;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class ProfissionalServico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	@JoinColumn
	@OneToOne (cascade = CascadeType.ALL)
	private Profissional profissional;
	@JoinColumn
	@ManyToOne
	private ServicoLoja servico;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ServicoLoja getServico() {
		return servico;
	}
	public void setServico(ServicoLoja servico) {
		this.servico = servico;
	}
	
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
}
