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
import br.ifpe.web3.model.ServicoLoja;
import br.ifpe.web3.model.ServicoLojaDAO;
import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.UsuarioEmpresa;

@Controller
public class ControllerEmpresa {
	
	@Autowired
	private EmpresaDAO empresaDao;
	@Autowired
	private ClienteDAO clienteDao;
	@Autowired
	private AgendamentoDAO agendaDao;
	@Autowired
	private ServicoLojaDAO servicoLojaDao;

	
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
	
	@GetMapping("/servicoEmpresa")
	public String servicoEmpresa(ServicoLoja servico, Model model, HttpSession session) throws LoginExceptions {
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<ServicoLoja> lista = servicoLojaDao.listaServico(chaveId.getId());
		
		model.addAttribute("listaServicos", lista);
		model.addAttribute("servico", servico);
		
		return "empresa/servicoEmpresa";
	}
	@PostMapping("/servicoEmpresa")
	public String Salvarservico(ServicoLoja servico, Model model, HttpSession session) throws LoginExceptions {
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		servico.setEmpresa(chaveId);
		servico.getEmpresa().setId(chaveId.getId());
		servicoLojaDao.save(servico);
		return "redirect:servicoEmpresa";
	}
	@GetMapping("/removerServico")
	public String removerServico(ServicoLoja servico, Integer codigo) throws LoginExceptions {
		servicoLojaDao.deleteById(codigo);
		return "redirect:servicoEmpresa";
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
		return "redirect:dadosEmpresa";
	}
	
	@GetMapping("/agendarEmpresa")
	public String agendarEmpresa(Agendamento agendamento, Model model, HttpSession session) {
		//UsuarioCliente cliente = clienteDao.findById(1).orElse(null); 
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosEmpresa(chaveId.getId());
		List<ServicoLoja> listaServico = servicoLojaDao.listaServico(chaveId.getId());
		agendamento.setEmpresa(chaveId);
		agendamento.getEmpresa().setId(chaveId.getId());
		
		model.addAttribute("agendamento", agendamento);
		model.addAttribute("listaServico", listaServico);
		model.addAttribute("listaAgendamento", listaAgendamento);
		
		return "empresa/agendarEmpresa"; 
	}
	
	@PostMapping("/fazerAgendamento")
	public String fazerAgendamento(Agendamento agendamento, Integer id, Model model, HttpSession session) {
		
		if(agendamento == null) {
			System.out.println("nulo");
			return"redirect:agendarEmpresa";
			
		}
		
		else if(session.getAttribute("tipo").equals("Empresa")) {
			UsuarioEmpresa chave =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
			agendamento.setEmpresa(chave);
			agendaDao.save(agendamento);
			
		}
		
		else if(session.getAttribute("tipo").equals("Cliente")) {
			UsuarioEmpresa empresa =  empresaDao.findById(id).orElse(null);
			System.out.println(empresa.getId());
			UsuarioCliente chave =(UsuarioCliente) session.getAttribute("usuarioLogado");
			System.out.println(chave.getNome());
			agendamento.setEmpresa(empresa);
			agendamento.getEmpresa().setId(empresa.getId());
			agendamento.setCliente(chave);
			agendamento.getCliente().setId(chave.getId());
			agendaDao.save(agendamento);
			return"redirect:agendamentosCliente";
			
		}
		else if(agendamento.getCliente().getNome() == "") {
			System.out.println("nome vazio");
			return"redirect:agendarEmpresa";
		}
		
			
			System.out.println("confirmado!");				
			return"redirect:agendarEmpresa";
		
	}
	@GetMapping("/removerAgendamentoEmpresa")
	public String removerAgendamento(Integer codigo,Model model) {
		agendaDao.deleteById(codigo);
		return"redirect:agendarEmpresa";
	}
	@GetMapping("/confirmarAgendamento")
	public String confirmarAgendamento(Integer codigo,Model model) {
		System.out.println(codigo);
		Agendamento agenda = agendaDao.findById(codigo).orElse(null);
		agenda.setStatus(true);
		agendaDao.save(agenda);
		return"redirect:agendarEmpresa";
	}
	
	@GetMapping("/removerloginEmpresa")
	public String removerEmpresa(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/login";
	}

	
	




}
