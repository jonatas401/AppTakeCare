package br.ifpe.web3.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifpe.web3.exceptions.LoginExceptions;
import br.ifpe.web3.model.UsuarioEmpresa;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.UsuarioCliente;

@Controller
public class LoginController {
	

	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	
	
	
	
	@RequestMapping("/efetuarLogin")
	public String EfetuarLogin( String email, String senha, HttpSession session) throws LoginExceptions {	
		
	try {
		 UsuarioCliente  usuarioCliente = clienteDao.findByEmailAndSenha(email, senha);
		 UsuarioEmpresa usuarioEmpresa = empresaDao.findByEmailAndSenha(email, senha);
		
		 if(email == null) {
		 throw new LoginExceptions("email nullo");
		
		 
		 }
		 else if(email == "") {
			 throw new LoginExceptions("email vazio");
		
			 }
		 else if(usuarioEmpresa != null) {
		
		          session.setAttribute("usuarioLogado", usuarioEmpresa);
		          session.setAttribute("tipo", "Empresa");
		          return "redirect:agendarEmpresa";
		      } 
		 else if(usuarioCliente != null) {
				
				 session.setAttribute("usuarioLogado", usuarioCliente);
				 session.setAttribute("tipo", "Cliente");
				 return "redirect:listEstabelecimentos";
		      }
		 
		 
			 System.out.println("nao encontrado");
			 
		 }catch( LoginExceptions e ) {
			System.out.println(e);
			return "redirect:/login";
			
			
			
		 }
	 
	
		return "redirect:/login";
	}
	

	@GetMapping("/logoff")
	public String logoff(HttpSession session) {
			session.invalidate();
		
		return "redirect:/login";
		
	}

}
