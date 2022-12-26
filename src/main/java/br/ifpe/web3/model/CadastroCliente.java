package br.ifpe.web3.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CadastroCliente {

		@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer Id;
		private String nome;
		private String numero;
		@JoinColumn
		@ManyToOne
		private UsuarioEmpresa empresa;
		
		
		public UsuarioEmpresa getEmpresa() {
			return empresa;
		}
		public void setEmpresa(UsuarioEmpresa empresa) {
			this.empresa = empresa;
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
		
		public String getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = numero;
		}
		
		

	
}
