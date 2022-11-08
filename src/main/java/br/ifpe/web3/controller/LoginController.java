package br.ifpe.web3.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String EfetuarLogin( String email, String senha, HttpSession session) {	
		 CadastroEmpresa usuarioEmpresa = empresaDao.findByEmailAndSenha(email, senha);
		 CadastroCliente  usuarioCliente = clienteDao.findByEmailAndSenha(email, senha);
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
		 else{
			 System.out.println("nao encontrado");
		 }
	 
	
		return "redirect:/login";
	}

}
