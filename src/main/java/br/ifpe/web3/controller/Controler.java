package br.ifpe.web3.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.web3.DAO.ClienteDAO;
import br.ifpe.web3.DAO.EmpresaDAO;
import br.ifpe.web3.DAO.PlanosDAO;
import br.ifpe.web3.DAO.TipoEmpresaDAO;
import br.ifpe.web3.model.Endereco;
import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.UsuarioEmpresa;
import br.ifpe.web3.util.UsuarioEmail;

@Controller
public class Controler {
	
	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	@Autowired
	private TipoEmpresaDAO tipoEmpresaDao;
	@Autowired
	private PlanosDAO planosDao;
	@Autowired
	private UsuarioEmail usuarioEmail;
	@Autowired
	UsuarioEmail usuarioemail;
	
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
		
		model.addAttribute("planos", planosDao.findAll());
		model.addAttribute("empresa", empresa);
		model.addAttribute("tipoEmpresa", tipoEmpresaDao.findAll());
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
	
	@GetMapping("/pinConfirmacao")
	public String pinConfirmacao(String email,RedirectAttributes ra, Model model) {
		
		List<String> lista = Arrays.asList("1","2","3","4","5","6");
		Collections.shuffle(lista);
		String codigo = String.join("", lista);
		
		try {
			System.out.println("confirmacao codigo");
			this.usuarioEmail.enviarEmailEsqueceuSenha(email, codigo);
			UsuarioCliente usuarioCliente = this.clienteDao.findByEmail(email);
			UsuarioEmpresa usuarioEmpresa = this.empresaDao.findByEmail(email);
			
			System.out.println(codigo);
			if(usuarioCliente != null) {
				model.addAttribute("usuario", usuarioCliente);
				usuarioCliente.setCodigoRecSenha(codigo);
				this.clienteDao.save(usuarioCliente);
			}
			if(usuarioEmpresa != null) {
				model.addAttribute("usuario", usuarioEmpresa);
				usuarioEmpresa.setCodigoRecSenha(codigo);
				this.empresaDao.save(usuarioEmpresa);
			}
			
			return "recuperarSenha";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ra.addFlashAttribute("msg", "Usuario não encontrado");
			return "redirect:/login";
		}
	}
	@PostMapping("/confirmarCod")
	public String confirmarPin(String email,String codigo,Model model, UsuarioEmpresa usuarioEmpresa,UsuarioCliente usuarioCliente, RedirectAttributes ra,HttpSession sessao) {
		if(this.empresaDao.existsByEmailAndCodigoRecSenha(email, codigo)) {
			UsuarioEmpresa user = this.empresaDao.findByEmail(email);
			model.addAttribute("usuario", user);
			return "acesso";
			
		}
		else if(usuarioEmpresa.getEmail()=="" || usuarioEmpresa.getSenha()=="") {
			ra.addFlashAttribute("msg", "Preencha todos os campos");
			return "redirect:/recuperarSenha";
		}
		
		if(this.clienteDao.existsByEmailAndCodigoRecSenha(email, codigo)) {
			UsuarioCliente user = this.clienteDao.findByEmail(email);
			model.addAttribute("usuario", user);
			return "acesso";
			
		}
		else if(usuarioCliente.getEmail()=="" || usuarioCliente.getSenha()=="") {
			ra.addFlashAttribute("msg", "Preencha todos os campos");
			return "redirect:/recuperarSenha";
		}
		
		
		else {
			ra.addFlashAttribute("msg", "Codigo invalido, tente novamente");
			return "redirect:/recuperarSenha";
		
		}
	}
	
	@PostMapping("/alterarSenha")
	public String alterarSenha(@Validated UsuarioEmpresa usuarioEmpresa,@Validated  UsuarioCliente usuarioCliente, String email,String senha, RedirectAttributes ra) {
		System.out.println(email);
		System.out.println(senha);
		
		usuarioCliente = this.clienteDao.findByEmail(email);
		usuarioEmpresa = this.empresaDao.findByEmail(email);
		
		if(usuarioCliente != null) {
			usuarioCliente.setSenha(senha);
			usuarioCliente.setCodigoRecSenha(null);
			this.clienteDao.save(usuarioCliente);
			
		}
		
		if(usuarioEmpresa != null) {
			usuarioEmpresa.setSenha(senha);
			usuarioEmpresa.setCodigoRecSenha(null);
			this.empresaDao.save(usuarioEmpresa);
			
		}
		
		ra.addFlashAttribute("msg", "Senha alterada");
		return "redirect:/login";
	}
	
	
	
	
	
	
	
	
	
	@PostMapping("/salvarUsuarioCliente")
	public String salvarUsuarioCliente(UsuarioCliente cliente,@RequestParam("fileImage") MultipartFile file, RedirectAttributes ra) {
		System.out.println("cliente salvo!");
		try {
			cliente.setFotoPerfil(file.getBytes());
		} catch (IOException e) {
			ra.addFlashAttribute("msg", "Selecione uma foto Com até 1Mb");
			return "redirect:/UsuarioEmpresa";
			
		}	
		clienteDao.save(cliente);	
		return "redirect:/login";
	}
	
	@PostMapping("/salvarUsuarioEmpresa")
	public String salvarCadastroEmpresa(UsuarioEmpresa empresa,@RequestParam("fileImage") MultipartFile file, RedirectAttributes ra) {
		System.out.println(file.getSize());
		
		try {
			
			empresa.setFotoPerfil(file.getBytes());
		} catch (IOException e) {
			ra.addFlashAttribute("msg", "Selecione uma foto Com até 1Mb");
			return "redirect:/UsuarioEmpresa";
			
		}	
		empresaDao.save(empresa);	
			System.out.println("empresa salvo!");
		return "redirect:/login";
	}
	
	
	
	

}


	