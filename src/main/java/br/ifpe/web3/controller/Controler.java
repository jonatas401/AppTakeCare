package br.ifpe.web3.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.ifpe.web3.exceptions.LoginExceptions;
import br.ifpe.web3.model.Agendamento;
import br.ifpe.web3.model.AgendamentoDAO;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.Endereco;
import br.ifpe.web3.model.Servico;
import br.ifpe.web3.model.ServicoLoja;
import br.ifpe.web3.model.ServicoLojaDAO;
import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.UsuarioEmpresa;

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
	
	

}


	