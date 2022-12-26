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
import br.ifpe.web3.model.CadastroCliente;
import br.ifpe.web3.model.CadastroClienteDAO;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.Profissional;
import br.ifpe.web3.model.ProfissionalDAO;
import br.ifpe.web3.model.ProfissionalServico;
import br.ifpe.web3.model.ProfissionalServicoDAO;
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
	@Autowired
	private ProfissionalDAO profissionalDao;
	@Autowired
	private ProfissionalServicoDAO profissionalServicoDao;
	@Autowired
	private CadastroClienteDAO cadastroClienteDao;
	
	
	
	@GetMapping("/dadosEmpresa")
	public String dadosEmpresa() throws LoginExceptions {
	
		System.out.println();
		return "empresa/dadosEmpresa";
	}
	
	
	@GetMapping("/portifolioEmpresa")
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
	
	@GetMapping("/profissionalEmpresa")
	public String profissional(Profissional profissional, Model model, HttpSession session) throws LoginExceptions {

		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<ServicoLoja> lista = servicoLojaDao.listaServico(chaveId.getId());
		List<Profissional> listaProfissional =  profissionalDao.listaProfissional(chaveId.getId());
		
		
		//model.addAttribute("servico", servico);
		model.addAttribute("listaServicos", lista);
		model.addAttribute("profissionalServico", lista);
		model.addAttribute("listaProfissional",listaProfissional);
		

		return "empresa/profissionalEmpresa";
	}
	
	@PostMapping("/salvarProfissionalEmpresa")
	public String salvarProfissional(Profissional profissional,ServicoLoja servicoLoja, Model model, HttpSession session) throws LoginExceptions {	

		System.out.println(profissional.getId());	
		System.out.println(servicoLoja);
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");	
		profissional.setEmpresa(empresa);
		profissionalDao.save(profissional);

		
//		for(ServicoLoja servico : servicoLoja ) {
//		System.out.println(servico.getId());
//		ProfissionalServico profissionalServico = new ProfissionalServico();
//		profissionalServico.setProfissional(profissional);
//		profissionalServico.setServico(servico);
//		profissionalServicoDao.save(profissionalServico);
//		}
		
		
		
		return "redirect:/profissionalEmpresa";
	}
	@GetMapping("/cadastrarPessoasEmpresa")
	public String cadastrarPessoasEmpresa(CadastroCliente cadastro, Model model , HttpSession session)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		model.addAttribute("cadastro", cadastro);
		model.addAttribute("cadastroCliente", cadastroClienteDao.listaClientesCadastrados(empresa.getId()));
		
		return "empresa/cadastrarPessoasEmpresa";
	}
	@PostMapping("/salvarCadastroPessoasEmpresa")
	public String salvarCadastroPessoas(CadastroCliente cadastro, Model model , HttpSession session)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		cadastro.setEmpresa(empresa);
		cadastroClienteDao.save(cadastro);
		
		return "redirect:cadastrarPessoasEmpresa";
	}
	
	@GetMapping("/removerCadastroPessoaEmpresa")
	public String removercadastroCliente(Integer id) {
		cadastroClienteDao.deleteById(id);
		return"redirect:cadastrarPessoasEmpresa";
	}
	
	
	

	@GetMapping("/removerProfissionalEmpresa")
	public String removerProfissional(ProfissionalServico profissionalServico, Integer codigo) throws LoginExceptions {
		profissionalDao.deleteById(codigo);		
		return "redirect:profissionalEmpresa";
	}
	
	@GetMapping("/removerServicoEmpresa")
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
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosEmpresa(empresa.getId());
		List<ServicoLoja> listaServico = servicoLojaDao.listaServico(empresa.getId());
		List<Profissional> profissional = profissionalDao.listaProfissional(empresa.getId());
		
		model.addAttribute("cadastroCliente", cadastroClienteDao.listaClientesCadastrados(empresa.getId()));
		model.addAttribute("agendamento", agendamento);
		model.addAttribute("listaServico", listaServico);
		model.addAttribute("listaAgendamento", listaAgendamento);
		model.addAttribute("profissionalServico", profissional);
		
		return "empresa/agendarEmpresa"; 
	}
	
	@PostMapping("/fazerAgendamento")
	public String fazerAgendamento(Agendamento agendamento, Integer id, Model model, HttpSession session) {
		
		if(agendamento == null) {
			System.out.println("nulo");
			return"redirect:agendarEmpresa";
			
		}
		 
		
		else if(session.getAttribute("tipo").equals("Empresa")) {
			if(agendamento.getCliente() != null && agendamento.getCliente().getNome() == "") {
				System.out.println("nome vazio");
				return"redirect:agendarEmpresa";
			}
			UsuarioEmpresa chave =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
			agendamento.setEmpresa(chave);
			agendaDao.save(agendamento);
			
		}
		
		else if(session.getAttribute("tipo").equals("Cliente")) {
			if(agendamento.getProfissional() == "") {
				System.out.println("nome vazio");
				return"redirect:estabelecimentoCliente?id=" + id;
			}
			UsuarioEmpresa empresa =  empresaDao.findById(id).orElse(null);
			System.out.println(empresa.getId());
			UsuarioCliente chave =(UsuarioCliente) session.getAttribute("usuarioLogado");
			System.out.println(chave.getNome());
			agendamento.setEmpresa(empresa);
			agendamento.setCliente(chave);
			agendaDao.save(agendamento);
			return"redirect:agendamentosCliente";
			
		}
		
		
			
			System.out.println("confirmado!");				
			return"redirect:agendarEmpresa";
		
	}
	
	@GetMapping("/removerAgendamentoEmpresa")
	public String removerAgendamento(Integer codigo,Model model) {
		agendaDao.deleteById(codigo);
		return"redirect:agendarEmpresa";
	}
	
	@GetMapping("/confirmarAgendamentoEmpresa")
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

	@GetMapping("/planosEmpresa")
	public String contaUsuario() {	
		return "empresa/planos";
	}
	




}
