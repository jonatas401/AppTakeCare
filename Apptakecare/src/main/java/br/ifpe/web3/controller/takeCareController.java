package br.ifpe.web3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.TakeCareCadastroCliente;
import br.ifpe.web3.model.TakeCareCadastroEmpresa;

@Controller
public class takeCareController {
	
	@Autowired
	private EmpresaDAO empresaDao;
	private ClienteDAO clienteDao;
	
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/cadastroCliente")
	public String cadastroCliente(TakeCareCadastroCliente cliente) {
		return "cadastroCliente";
	}
	
	@GetMapping("/cadastroEmpresa")
	public String cadastroEmpresa(TakeCareCadastroCliente empresa) {
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
	public String salvarCadastroCliente(TakeCareCadastroCliente cliente) {
			clienteDao.save(cliente);	
		return "redirect:/login";
	}
	
	@PostMapping("/salvarCadastroEmpresa")
	public String salvarCadastroEmpresa(TakeCareCadastroEmpresa empresa) {
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
		TakeCareCadastroCliente cliente = clienteDao.findById(Id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "cadastroCliente";
	}
	
	@GetMapping("/editarCadastroEmpresa")
	public String editarEmpresa(Integer Id, Model model) {
		TakeCareCadastroEmpresa empresa = empresaDao.findById(Id).orElse(null);
		model.addAttribute("empresa", empresa);
		return "cadastroEmpresa";
	}
	
}


