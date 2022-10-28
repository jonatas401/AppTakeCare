package br.ifpe.web3.controller;

import java.util.List;

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
	public String EfetuarLogin( String email, HttpSession session) {	
		List<CadastroEmpresa> usuario = empresaDao.findAll();
		System.out.println(usuario);
		for(CadastroEmpresa e : usuario) {
			if(e.getEmail().equals(email)) {
				System.out.println(e.getEmail());
		          session.setAttribute("usuarioLogado", e);
		          return "contaUsuario";
		      } 
			else {
		          System.out.println("nao encontrado");
		      }
		}
	 
		
	
		return "redirect:/login";
	}

}
