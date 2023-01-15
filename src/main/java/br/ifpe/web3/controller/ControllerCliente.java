package br.ifpe.web3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.web3.DAO.AgendamentoDAO;
import br.ifpe.web3.DAO.ClienteDAO;
import br.ifpe.web3.DAO.EmpresaDAO;
import br.ifpe.web3.DAO.NoticiaDAO;
import br.ifpe.web3.DAO.ProfissionalDAO;
import br.ifpe.web3.DAO.ServicoLojaDAO;
import br.ifpe.web3.DAO.TipoEmpresaDAO;
import br.ifpe.web3.exceptions.LoginExceptions;
import br.ifpe.web3.model.Agendamento;
import br.ifpe.web3.model.Noticia;
import br.ifpe.web3.model.Profissional;
import br.ifpe.web3.model.ServicoLoja;
import br.ifpe.web3.model.TipoEmpresa;
import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.UsuarioEmpresa;

@Controller
public class ControllerCliente {
	
	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	@Autowired
	private AgendamentoDAO agendaDao;
	@Autowired
	private ServicoLojaDAO servicoLojaDao;
	@Autowired
	private ProfissionalDAO profissionalDao;
	@Autowired
	private NoticiaDAO noticiaDao;
	@Autowired
	private TipoEmpresaDAO tipoEmpresaDao;


	
	
	@GetMapping("/dadosCliente")
	public String meusDados(HttpSession session) throws LoginExceptions {
		UsuarioCliente chave =(UsuarioCliente) session.getAttribute("usuarioLogado");
		System.out.println(chave.getFotoPerfil());
		return "cliente/dadosCliente";
	}
	
	@GetMapping("/agendamentosCliente")
	public String agendamentos(HttpSession session, Model model) {
		UsuarioCliente chave =(UsuarioCliente) session.getAttribute("usuarioLogado");
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentos(chave.getId());
		for(Agendamento a : listaAgendamento) {
			System.out.println(a.getId());
		}
		model.addAttribute("listaAgendamento", listaAgendamento);
		return "cliente/agendamentosCliente";
	}
	
	@GetMapping("/agendarCliente")
	public String agendarCliente(Agendamento agendamento, Model model) {
		model.addAttribute("listaAgenda", agendaDao.findAll());
		model.addAttribute("agendamento", agendamento);
		return "cliente/agendarCliente";
	}
	
	
	@GetMapping("/configuracaoCliente")
	public String configuracao() {
		
		return "cliente/configuracaoCliente";
	}

	@PostMapping("pesquisaEstabelecimentoCliente")
	public String pesquisaEstabelecimento(String pesquisa, Model model , Integer id) {
		
		List<UsuarioEmpresa>nomeEmpresas = empresaDao.findByNomeEmpresaContaining(pesquisa);
		List<UsuarioEmpresa> tipoEmpresa = empresaDao.findByTipoEmpresaId(id);
		System.out.println(id);
		
		model.addAttribute("listarLojas", nomeEmpresas);
		if(id != null) {
			model.addAttribute("listarLojas", tipoEmpresa); 
		}
		model.addAttribute("tipoEmpresa", tipoEmpresaDao.findAll());
		
		return "cliente/listarEstabelecimentos";
	}

	@GetMapping("/listarEstabelecimentosCliente")
	public String listEstabelecimentos(UsuarioEmpresa empresa, Model model,String pesquisa, HttpSession session, Integer id)  {

		model.addAttribute("tipoEmpresa", tipoEmpresaDao.findAll());
		model.addAttribute("listarLojas", empresaDao.findAll());
		
		return "cliente/listarEstabelecimentos";
	}

	

	@GetMapping("/estabelecimentoCliente")

	public String estabelecimento(Agendamento agendamento, Integer id, Model model, HttpSession session ) {
		UsuarioEmpresa empresa =  empresaDao.findById(id).orElse(null);	
		UsuarioCliente cliente = (UsuarioCliente) session.getAttribute("usuarioLogado");

		List<ServicoLoja> servico = servicoLojaDao.listaServico(empresa.getId());
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosCliente(cliente.getId(), empresa.getId());
		List<Profissional> profissional = profissionalDao.listaProfissional(empresa.getId());
		List<Noticia> listaNoticia = noticiaDao.findByEmpresaId(empresa.getId());
		
		
		for(Noticia list : listaNoticia) {
			if(list.isUrgente()) {
				model.addAttribute("listaNoticia", list);
			}
		}
		
		model.addAttribute("listaAgendamento", listaAgendamento);	
		model.addAttribute( "loja",empresa);
		model.addAttribute("agendamento", agendamento);
		model.addAttribute("profissionalServico", profissional);
		model.addAttribute("todasNoticias",listaNoticia);
		model.addAttribute("Servico", servico);

		return "cliente/estabelecimento";
	}
	
	

	@PostMapping("/UsuarioClienteEditado")
	public String UsuarioClienteEditado(UsuarioCliente cliente,@RequestParam("fileImage") MultipartFile file, HttpSession session) {
		byte[] arquivo = this.exibirFotoPerfilCliente(cliente.getId());
		try {
			cliente.setFotoPerfil(file.getBytes());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		if(file.isEmpty()) {
			cliente.setFotoPerfil(arquivo);
		}
		clienteDao.save(cliente);	
		session.setAttribute("usuarioLogado", cliente);
		return "cliente/dadosCliente";
	}
	
	@GetMapping("/fotoPerfilCliente/{idFoto}")
	@ResponseBody
	public byte[] exibirFotoPerfilCliente(@PathVariable("idFoto") Integer idFoto) {
		System.out.println(idFoto);
		System.out.println("imagem");
		UsuarioCliente cliente = clienteDao.findById(idFoto).orElse(null);
		return cliente.getFotoPerfil();
	}
	
	@GetMapping("/removerloginCliente")
	public String removerCliente(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/";
	}
	
	@GetMapping("/removerAgendamentoCliente")
	public String removerAgendamentoCliente(Integer codigo,Model model) {
		agendaDao.deleteById(codigo);
		System.out.println("removido!");
		return"redirect:agendamentosCliente";
	}

	@GetMapping("/editarUsuarioCliente")
	public String editarCliente(Integer Id, Model model) {
		
		UsuarioCliente cliente = clienteDao.findById(Id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "cliente/editarUsuarioCliente";
	}
	
	@GetMapping("/deletarUsuarioCliente")
	public String deletarCliente(Integer Id, Model model, RedirectAttributes ra) {
		Agendamento agenda = agendaDao.buscaPorPessoa(Id);
 		if(agenda == null) {
 			clienteDao.deleteById(Id);
 			ra.addFlashAttribute("msg" , "Sua conta foi apagada com sucesso!");
 			return "redirect:login";
		}
		ra.addFlashAttribute("msg" , "verifique se você não tem nenhum agendamento!");
		
		return "redirect:dadosCliente";
	}
	
	
	
	

}
