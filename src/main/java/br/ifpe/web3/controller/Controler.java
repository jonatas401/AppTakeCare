package br.ifpe.web3.controller;



import java.util.List;

import javax.servlet.http.HttpSession;
import br.ifpe.web3.exceptions.LoginExceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.CadastroEmpresa;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.Endereco;

@Controller
public class Controler {
	
	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	
	//*******Rotas fora do acesso do usuario***********
	
	
	
	@GetMapping("/login")
	public String login(CadastroEmpresa empresa, UsuarioCliente cliente,Model model) {
		model.addAttribute("cliente", cliente);
		model.addAttribute("empresa", empresa);
		
		return "login";
	}
	
	
	
	@GetMapping("/UsuarioCliente")
	public String UsuarioCliente(UsuarioCliente cliente,  Model model) {
		model.addAttribute("cliente", cliente);
		return "cadastroCliente";
	}
	
	@GetMapping("/cadastroEmpresa")
	public String cadastroEmpresa(CadastroEmpresa empresa, Model model) {
		model.addAttribute("empresa", empresa);
		return "cadastroEmpresa";
	}
	
	
	@GetMapping("/opcaoCadastro")
	public String opcaoCadastro() {
		return "opcaoCadastro";
	}
	
	
	@GetMapping("/esqueciSenha")
	public String esqueciSenha(Model model) {
		return "esqueciSenha";
	}
	
	@PostMapping("/salvarUsuarioCliente")
	public String salvarUsuarioCliente(UsuarioCliente cliente, Endereco endereco) {
		clienteDao.save(cliente);	
		return "redirect:/login";
	}
	
	@PostMapping("/salvarCadastroEmpresa")
	public String salvarCadastroEmpresa(CadastroEmpresa empresa) {
			empresaDao.save(empresa);	
		return "redirect:/login";
	}
	

	
	
	//*******************Rotas de acesso a paginas de usuarios logados*************************
	
	
	@GetMapping("/meusDados")
	public String meusDados() throws LoginExceptions {
	
		System.out.println();
		return "meusDados";
	}
	
	@GetMapping("/listEstabelecimentos")
	public String listLojas(CadastroEmpresa empresa, Model model, HttpSession session)  {
//		if(session.) {
//			
//		}
	System.out.println();
	List <CadastroEmpresa> nomeEmpresa = empresaDao.findAll();
	
		
	model.addAttribute("listarLojas", nomeEmpresa);
		return "listarEstabelecimentos";
	}
	
	
	@GetMapping("/estabelecimento")
	public String loja(Integer id, Model model) {
		CadastroEmpresa empresa =  empresaDao.findById(id).orElse(null);
		
		model.addAttribute( "loja",empresa);
	
		
		return "estabelecimento";
	}
	
	
	
	@GetMapping("/agendamentos")
	public String agendamentos() {
		
		return "agendamentos";
	}
	@PostMapping("/agendar")
	public String agendars() {
		
		return agendamentos();
	}
	
	@GetMapping("/configuracao")
	public String configuracao() {
		
		return "configuracao";
	}
	
	@GetMapping("/logoff")
	public String logoff(HttpSession session) {
			session.invalidate();
		
		return "redirect:/login";
	}
	
	@PostMapping("/UsuarioClienteEditado")
	public String UsuarioClienteEditado(UsuarioCliente cliente, HttpSession session) {
		clienteDao.save(cliente);	
		session.setAttribute("usuarioLogado", cliente);
			
		return "redirect:/meusDados";
	}
	
	
	
	@PostMapping("/CadastroEmpresaEditado")
	public String CadastroEmpresaEditado(CadastroEmpresa empresa, HttpSession session) {
		
		empresaDao.save(empresa);	
		session.setAttribute("usuarioLogado", empresa);
		return "redirect:/meusDados";
	}
	
	
	
	
	
	//********** Rotas para remover e editar***********
	
	
	@GetMapping("/removerloginCliente")
	public String removerCliente(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/";
	}
	
	@GetMapping("/removerloginEmpresa")
	public String removerEmpresa(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/";
	}
	
	@GetMapping("/editarUsuarioCliente")
	public String editarCliente(Integer Id, Model model) {
		UsuarioCliente cliente = clienteDao.findById(Id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "editarUsuarioCliente";
	}
	
	@GetMapping("/editarCadastroEmpresa")
	public String editarEmpresa(Integer Id, Model model) {
		CadastroEmpresa empresa = empresaDao.findById(Id).orElse(null);
		model.addAttribute("empresa", empresa);
		return "editarCadastroEmpresa";
	}
	
	
	
}


