package br.ifpe.web3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.ifpe.web3.model.CadastroCliente;
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
	
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/cadastroCliente")
	public String cadastroCliente(CadastroCliente cliente, Endereco endereco, Model model) {
		model.addAttribute("cliente", cliente);
		model.addAttribute("endereco", endereco);
		return "cadastroCliente";
	}
	
	@GetMapping("/cadastroEmpresa")
	public String cadastroEmpresa(CadastroEmpresa empresa, Endereco endereco, Model model) {
		model.addAttribute("empresa", empresa);
		model.addAttribute("endereco", endereco);
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
	
	@PostMapping("/salvarCadastroCliente")
	public String salvarCadastroCliente(CadastroCliente cliente, Endereco endereco) {
		clienteDao.save(cliente);	
		return "redirect:/login";
	}
	
	@PostMapping("/salvarCadastroEmpresa")
	public String salvarCadastroEmpresa(CadastroEmpresa empresa) {
			empresaDao.save(empresa);	
		return "redirect:/login";
	}
	

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
	
	@GetMapping("/editarCadastroCliente")
	public String editarCliente(Integer Id, Model model) {
		CadastroCliente cliente = clienteDao.findById(Id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "cadastroCliente";
	}
	
	@GetMapping("/editarCadastroEmpresa")
	public String editarEmpresa(Integer Id, Model model) {
		CadastroEmpresa empresa = empresaDao.findById(Id).orElse(null);
		model.addAttribute("empresa", empresa);
		return "cadastroEmpresa";
	}
	
}


