package br.ifpe.web3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifpe.web3.DAO.ClienteDAO;
import br.ifpe.web3.DAO.EmpresaDAO;
import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.UsuarioEmpresa;


@Service
public class UsuarioEmail {
	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	@Autowired
	EmailService emailService;
	
	public void enviarEmailEsqueceuSenha(String email, String codigo) throws Exception {
		UsuarioEmpresa usuario = this.empresaDao.findByEmail(email);
		UsuarioCliente usuario1 = this.clienteDao.findByEmail(email);
		if (usuario != null) {
			this.emailService.enviarEmail(usuario.getEmail(), codigo);
		} 
		else if(usuario1 != null) {
			this.emailService.enviarEmail(usuario1.getEmail(), codigo);
		} 
		else {
			throw new Exception("Usuário inexistente");
		}
	}
	
	public void enviarEmailNotificacao(String emailFrom,String email, String subject, String texto) throws Exception {
		UsuarioEmpresa usuario = this.empresaDao.findByEmail(email);
		UsuarioCliente usuario1 = this.clienteDao.findByEmail(email);
		 if(usuario != null) {
			this.emailService.enviarNotificacao(email,usuario.getEmail(), subject, texto);
		} 
		
		else if(usuario1 != null) {
			this.emailService.enviarNotificacao(emailFrom,usuario1.getEmail(), subject, texto);
		} 
		else {
			throw new Exception("Usuário inexistente");
		}
	}
	
}
