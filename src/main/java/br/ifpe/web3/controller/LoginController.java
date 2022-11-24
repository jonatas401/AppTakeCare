package br.ifpe.web3.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifpe.web3.exceptions.LoginExceptions;
import br.ifpe.web3.model.CadastroCliente;
import br.ifpe.web3.model.CadastroEmpresa;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;

@Controller
public class LoginController {
	

	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	
	@GetMapping("/contaUsuario")
	public String contaUsuario(CadastroEmpresa empresa, Model model) {	
		return "contaUsuario";
	}
	
	
	@RequestMapping("/efetuarLogin")
	public String EfetuarLogin( String email, String senha, HttpSession session) throws LoginExceptions {	
		
	try {
		 CadastroCliente  usuarioCliente = clienteDao.findByEmailAndSenha(email, senha);
		 CadastroEmpresa usuarioEmpresa = empresaDao.findByEmailAndSenha(email, senha);
		
		 if(email == null) {
		 throw new LoginExceptions("email nullo");
		 
		 }
		 if(usuarioEmpresa != null) {
		
		          session.setAttribute("usuarioLogado", usuarioEmpresa);
		          session.setAttribute("tipo", "Empresa");
		          return "redirect:/contaUsuario";
		      } 
		else if(usuarioCliente != null) {
				
				 session.setAttribute("usuarioLogado", usuarioCliente);
				  session.setAttribute("tipo", "Cliente");
				 return "redirect:/contaUsuario";
		      }
		 
		 
			 System.out.println("nao encontrado");
			 
		 }catch( LoginExceptions e ) {
			System.out.println(e);
			throw new LoginExceptions("não encontrado");
			 
			
		 }
	 
	
		return "redirect:/login";
	}

}
