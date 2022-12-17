package br.ifpe.web3.controller;

import java.util.List;

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
import br.ifpe.web3.model.ProfissionalServico;
import br.ifpe.web3.model.ProfissionalServicoDAO;
import br.ifpe.web3.model.ServicoLoja;
import br.ifpe.web3.model.ServicoLojaDAO;
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
	private ProfissionalServicoDAO profissionalServicoDao;
	
	
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
	public String agendamentos(HttpSession session, Model model) {
		UsuarioCliente chave =(UsuarioCliente) session.getAttribute("usuarioLogado");
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosCliente(chave.getId());
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
	@PostMapping("pesquisaEstabelecimento")
	public String pesquisaEstabelecimento(String pesquisa, Model model) {
		
		model.addAttribute("listarLojas", new UsuarioEmpresa());
		List<UsuarioEmpresa >nomeEmpresas = empresaDao.findByNomeEmpresaContaining(pesquisa);
		model.addAttribute("listarLojas", nomeEmpresas);
		
		return "cliente/listarEstabelecimentos";
	}

	@GetMapping("/listEstabelecimentos")
	public String listEstabelecimentos(UsuarioEmpresa empresa, Model model, HttpSession session, Integer id)  {
		/* validar sessao*/
		
		if(session.getAttribute("tipo").equals("Empresa")) {
			return "redirect:contaUsuarioEmpresa";
		}
		
		/* listar estabelecimentos*/
	List <UsuarioEmpresa> nomeEmpresa = empresaDao.findAll();
	
		
	model.addAttribute("listarLojas", nomeEmpresa);
		return "cliente/listarEstabelecimentos";
	}
	
	
	@GetMapping("/estabelecimento")
	public String estabelecimento(Agendamento agendamento, Integer id, Model model, HttpSession session ) {
		UsuarioEmpresa empresa =  empresaDao.findById(id).orElse(null);
		agendamento.setEmpresa(empresa);
				
		//List<ServicoLoja> lista = servicoLojaDao.listaServico(empresa.getId());
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosEmpresa(empresa.getId());
		List<ProfissionalServico> profissional = profissionalServicoDao.listaProfissional(empresa.getId());
		for(ProfissionalServico prof : profissional) {
			System.out.println(prof.getProfissional().getNome());
		}
		
		model.addAttribute("listaAgendamento", listaAgendamento);	
		model.addAttribute( "loja",empresa);
		model.addAttribute("agendamento", agendamento);
		model.addAttribute("profissionalServico", profissional);
		
		
		return "cliente/estabelecimento";
	}

	@PostMapping("/UsuarioClienteEditado")
	public String UsuarioClienteEditado(UsuarioCliente cliente, HttpSession session) {
		clienteDao.save(cliente);	
		session.setAttribute("usuarioLogado", cliente);
			
		return "cliente/dadosCliente";
	}
	@GetMapping("/removerloginCliente")
	public String removerCliente(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/";
	}
	@GetMapping("/removerAgendamentoCliente")
	public String removerAgendamentoCliente(Integer codigo,Model model) {
		agendaDao.deleteById(codigo);
		return"redirect:agendamentosCliente";
	}

	@GetMapping("/editarUsuarioCliente")
	public String editarCliente(Integer Id, Model model) {
		UsuarioCliente cliente = clienteDao.findById(Id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "cliente/editarUsuarioCliente";
	}
	
	
	
	

}
