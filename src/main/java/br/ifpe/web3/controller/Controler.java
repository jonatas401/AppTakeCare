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
import br.ifpe.web3.model.UsuarioEmpresa;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.Endereco;

@Controller
public class Controler {
	
	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	
	//*******Rotas de acesso geral***********
	
	
	
	@GetMapping("/login")
	public String login(UsuarioEmpresa empresa, UsuarioCliente cliente,Model model) {
		model.addAttribute("cliente", cliente);
		model.addAttribute("empresa", empresa);
		
		return "login";
	}
	
	
	
	@GetMapping("/UsuarioCliente")
	public String UsuarioCliente(UsuarioCliente cliente,  Model model) {
		model.addAttribute("cliente", cliente);
		return "cliente/cadastroCliente";
	}
	
	@GetMapping("/UsuarioEmpresa")
	public String cadastroEmpresa(UsuarioEmpresa empresa, Model model) {
		model.addAttribute("empresa", empresa);
		return "empresa/cadastroEmpresa";
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
	
	@PostMapping("/salvarUsuarioEmpresa")
	public String salvarCadastroEmpresa(UsuarioEmpresa empresa) {
			empresaDao.save(empresa);	
		return "redirect:/login";
	}
	
	
	//*******************Rotas de acesso a paginas de ambos usuarios*************************

	
	@GetMapping("/logoff")
	public String logoff(HttpSession session) {
			session.invalidate();
		
		return "redirect:/login";
		
	}
	
	//*******************Rotas de acesso a paginas de usuarios cliente*************************
	
	@GetMapping("/contaUsuarioCliente")
	public String contaUsuario(UsuarioCliente cliente, Model model) {	
		return "cliente/contaUsuarioCliente";
	}
	
	@GetMapping("/dadosCliente")
	public String meusDados() throws LoginExceptions {
	
		System.out.println();
		return "cliente/dadosCliente";
	}
	
	@GetMapping("/agendamentosCliente")
	public String agendamentos() {
		
		return "cliente/agendamentosCliente";
	}
	
	@GetMapping("/agendarCliente")
	public String agendar() {
		
		return "cliente/agendarCliente";
	}
	
	@GetMapping("/configuracaoCliente")
	public String configuracao() {
		
		return "cliente/configuracaoCliente";
	}
	

	@GetMapping("/listEstabelecimentos")
	public String listLojas(UsuarioEmpresa empresa, Model model, HttpSession session)  {
		/* validar sessao*/
		
		if(session.getAttribute("tipo").equals("Empresa")) {
			return "redirect:/empresa/contaUsuarioEmpresa";
		}
		
		/* listar estabelecimentos*/
	List <UsuarioEmpresa> nomeEmpresa = empresaDao.findAll();
	
		
	model.addAttribute("listarLojas", nomeEmpresa);
		return "cliente/listarEstabelecimentos";
	}
	
	
	@GetMapping("/estabelecimento")
	public String loja(Integer id, Model model, HttpSession session ) {
		
		UsuarioEmpresa empresa =  empresaDao.findById(id).orElse(null);
		
		model.addAttribute( "loja",empresa);
	
		
		return "cliente/estabelecimento";
	}

	@PostMapping("/UsuarioClienteEditado")
	public String UsuarioClienteEditado(UsuarioCliente cliente, HttpSession session) {
		clienteDao.save(cliente);	
		session.setAttribute("usuarioLogado", cliente);
			
		return "redirect:/cliente/dadosCliente";
	}
	@GetMapping("/removerloginCliente")
	public String removerCliente(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/";
	}
	

	@GetMapping("/editarUsuarioCliente")
	public String editarCliente(Integer Id, Model model) {
		UsuarioCliente cliente = clienteDao.findById(Id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "cliente/editarUsuarioCliente";
	}
	
	
	
	
	
	
	
	
	
	
	
	//*******************Rotas de acesso a paginas de usuarios empresa*************************
	
	@GetMapping("/contaUsuarioEmpresa")
	public String contaUsuario(UsuarioEmpresa empresa, Model model) {	
		return "empresa/contaUsuarioEmpresa";
	}
	
	@GetMapping("/dadosEmpresa")
	public String dadosEmpresa() throws LoginExceptions {
	
		System.out.println();
		return "empresa/dadosEmpresa";
	}
	
	
	@GetMapping("/portifolio")
	public String portifolio() throws LoginExceptions {
	
		System.out.println();
		return "empresa/addPortifolio";
	}
	
	@GetMapping("/agendamentosEmpresa")
	public String agendamentosEmpresa() {
		
		return "empresa/agendamentosEmpresa";
	}
	
	@GetMapping("/agendarEmpresa")
	public String agendarEmpresa() {
		
		return "empresa/agendarEmpresa";
	}
	
	@GetMapping("/configuracaoEmpresa")
	public String configuracaoEmpresa() {
		
		return "empresa/configuracaoEmpresa";
	}
	
	@GetMapping("/editarUsuarioEmpresa")
	public String editarEmpresa(Integer Id, Model model) {
		UsuarioEmpresa empresa = empresaDao.findById(Id).orElse(null);
		model.addAttribute("empresa", empresa);
		return "empresa/editarCadastroEmpresa";
	}
	
	@PostMapping("/UsuarioEmpresaEditado")
	public String CadastroEmpresaEditado(UsuarioEmpresa empresa, HttpSession session) {
		
		empresaDao.save(empresa);	
		session.setAttribute("usuarioLogado", empresa);
		return "redirect:/empresa/dadosEmpresa";
	}
	
	@GetMapping("/removerloginEmpresa")
	public String removerEmpresa(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/login";
	}
	
	//********** Rotas para ***********
	
	
	
	

	
	
	
}


