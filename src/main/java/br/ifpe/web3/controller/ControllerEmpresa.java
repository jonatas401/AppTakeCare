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

import br.ifpe.web3.exceptions.LoginExceptions;
import br.ifpe.web3.model.Agendamento;
import br.ifpe.web3.model.AgendamentoDAO;
import br.ifpe.web3.model.ClienteDAO;
import br.ifpe.web3.model.EmpresaDAO;
import br.ifpe.web3.model.Planos;
import br.ifpe.web3.model.PlanosDAO;
import br.ifpe.web3.model.Profissional;
import br.ifpe.web3.model.ProfissionalDAO;
import br.ifpe.web3.model.ProfissionalServico;
import br.ifpe.web3.model.ProfissionalServicoDAO;
import br.ifpe.web3.model.ServicoLoja;
import br.ifpe.web3.model.ServicoLojaDAO;
import br.ifpe.web3.model.TipoEmpresaDAO;
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
	private PlanosDAO planosDao;
	@Autowired
	private TipoEmpresaDAO tipoEmpresaDao;
	
	
	
	@GetMapping("/dadosEmpresa")
	public String dadosEmpresa() throws LoginExceptions {
		System.out.println();
		return "empresa/dadosEmpresa";
	}
	
	@GetMapping("/configuracaoEmpresa")
	public String configuracaoEmpresa() {
		
		return "empresa/configuracaoEmpresa";
	}
	
	
	@GetMapping("/portifolioEmpresa")
	public String portifolio(Model model, HttpSession session) throws LoginExceptions {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		model.addAttribute("servicoLoja",servicoLojaDao.listaServico(empresa.getId()));
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
	public String salvarServico(ServicoLoja servico, Model model, HttpSession session, @RequestParam("fileImage") MultipartFile file, RedirectAttributes ra) throws LoginExceptions {
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		servico.setEmpresa(chaveId);
		servico.getEmpresa().setId(chaveId.getId());
		try {
			servico.setFoto(file.getBytes());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		servicoLojaDao.save(servico);
		ra.addFlashAttribute("msg", "Servico salvo com Sucesso!");
		return "redirect:servicoEmpresa";
	}
	
	@GetMapping("/editarServicoEmpresa")
	public String editarServicoEmpresa(Integer codigo, Model model, HttpSession session) throws LoginExceptions {
		ServicoLoja servico = servicoLojaDao.findById(codigo).orElse(null);
		
		model.addAttribute("servico", servico);
		
		return "empresa/servicoEmpresa";
	}
	
	@GetMapping("/removerServicoEmpresa")
	public String removerServico(ServicoLoja servico, Integer codigo, RedirectAttributes ra) throws LoginExceptions {
		List<ProfissionalServico> profissionalServico = profissionalServicoDao.buscaPorServico(codigo);
		for(ProfissionalServico serv : profissionalServico) {
			if(serv != null) {
				ra.addFlashAttribute("msg", "Verifique se esse servico não esta sendo usado");
				return "redirect:servicoEmpresa";
			}
		}
		servicoLojaDao.deleteById(codigo);
		ra.addFlashAttribute("msg", "Servico removido com Sucesso!");
		return "redirect:servicoEmpresa";
	}
	
	
	@GetMapping("/imagem/{idFoto}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("idFoto") Integer idFoto) {
		System.out.println(idFoto);
		System.out.println("imagem");
		ServicoLoja servicoLoja = servicoLojaDao.findById(idFoto).orElse(null);
		return servicoLoja.getFoto();
	}
	
	@GetMapping("/profissionalEmpresa")
	public String profissional(Profissional profissional, Model model, HttpSession session) throws LoginExceptions {

		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<ServicoLoja> lista = servicoLojaDao.listaServico(chaveId.getId());
		List<Profissional> listaProfissional =  profissionalDao.listaProfissional(chaveId.getId());
		List<ProfissionalServico> listaProfissionalServico = profissionalServicoDao.listaProfissional(chaveId.getId());
		
		model.addAttribute("listaServicos", lista);
		model.addAttribute("listaProfissional",listaProfissional);
		model.addAttribute("listaProfissionalServico",listaProfissionalServico);

		return "empresa/profissionalEmpresa";
	}
	
	@PostMapping("/salvarProfissionalEmpresa")
	public String salvarProfissional(Profissional profissional,ServicoLoja servicoLoja, Model model, HttpSession session, RedirectAttributes ra) throws LoginExceptions {	

		
		System.out.println(servicoLoja.getId()+ " id");
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");	
		profissional.setEmpresa(empresa);
		profissionalDao.save(profissional);
		ra.addFlashAttribute("msg", "Profissional salvo com Sucesso!");

		
		return "redirect:/profissionalEmpresa";
	}
	
	@PostMapping("/pesquisaProfissionalEmpresa")
	public String pesquisaProfissional(String numero, Model model, HttpSession session)  {	
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<ServicoLoja> lista = servicoLojaDao.listaServico(chaveId.getId());
		System.out.println(numero);
		System.out.println(chaveId.getId());
		Profissional profissional = profissionalDao.pesquisaProfissional(numero, chaveId.getId());
		if(profissional == null){
			 profissional =  new Profissional();
			 model.addAttribute("msg", "Profissional Não Encontrado");
		}
	
		model.addAttribute("listaServicos", lista);
		 model.addAttribute("profissional",profissional);
		return "empresa/profissionalEmpresa";
	}
	
	@PostMapping("/salvarProfissionalServicoEmpresa")
	public String salvarProfissionalServico(Profissional profissional,ServicoLoja servicoLoja, Model model, HttpSession session, RedirectAttributes ra) throws LoginExceptions {	
	
		System.out.println(profissional.getId()+ " id prof");
		ProfissionalServico profissionalServico = new ProfissionalServico();
		profissionalServico.setProfissional(profissional);
		profissionalServico.setServico(servicoLoja);
		profissionalServicoDao.save(profissionalServico);
		
		ra.addFlashAttribute("msg", "Servico Alocado com Sucesso!");
		return "redirect:/profissionalEmpresa";
	}
	
//	@GetMapping("/editarProfissionalEmpresa")
//	public String editarProfissional(Profissional profissional, Model model, Integer codigo, ServicoLoja servicoLoja) throws LoginExceptions {
//		profissional =  profissionalDao.findById(codigo).orElse(null);
//		List<ProfissionalServico> profissionalServico = profissionalServicoDao.findByProfissional(profissional);
//		 List<ServicoLoja> servico = new ArrayList<>();
//		for(ProfissionalServico prof : profissionalServico) {
//			servico.add(prof.getServico());
//			System.out.println(prof.getServico().getId());
//		}
//		
//		model.addAttribute("listaServicos", servico);
//		model.addAttribute("profissional",profissional);
//		return "empresa/profissionalEmpresa";
//	}
//	
	
	@GetMapping("/removerProfissionalEmpresa")
	public String removerProfissional(Profissional profissional, Integer codigo, RedirectAttributes ra) throws LoginExceptions {	
	
		
		profissionalServicoDao.deleteById(codigo);	
		ra.addFlashAttribute("msg", "Servico desalocado do profissional com Sucesso!");
		return "redirect:profissionalEmpresa";
	}
	
	
	@GetMapping("/cadastrarPessoasEmpresa")
	public String cadastrarPessoasEmpresa(UsuarioCliente cadastro, Model model , HttpSession session)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		model.addAttribute("cadastro", cadastro);
		model.addAttribute("cadastroCliente", clienteDao.listaClientesCadastrados(empresa.getId()));
		
		
		return "empresa/cadastrarPessoasEmpresa";
	}
	
	@PostMapping("/salvarCadastroPessoasEmpresa")
	public String salvarCadastroPessoas(UsuarioCliente cadastro, Model model , HttpSession session, RedirectAttributes ra)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		cadastro.setEmpresa(empresa);
		clienteDao.save(cadastro);
		
		ra.addFlashAttribute("msg", "Cliente salvo com Sucesso!");
		return "redirect:cadastrarPessoasEmpresa";
	}
	
	@GetMapping("/removerCadastroPessoaEmpresa")
	public String removercadastroCliente(Integer codigo, RedirectAttributes ra) {
		Agendamento pessoa = agendaDao.buscaPorPessoa(codigo);
		
		if(pessoa != null) {
			ra.addFlashAttribute("msg", "Verifique se esse cliente não esta sendo usado");
			return "redirect:cadastrarPessoasEmpresa";
		}
	
		
		clienteDao.deleteById(codigo);
		ra.addFlashAttribute("msg", "Cliente removido com Sucesso!");
		return"redirect:cadastrarPessoasEmpresa";
	}
	
	@GetMapping("/editarPessoaEmpresa")
	public String editarPessoasEmpresa( Model model , HttpSession session)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<UsuarioCliente> cliente = clienteDao.listaClientesCadastrados(empresa.getId());
		
		for(UsuarioCliente lista : cliente) {
			UsuarioCliente cadastro = clienteDao.findById(lista.getId()).orElse(null);
			model.addAttribute("cadastro", cadastro);
		}
		
	
		return "empresa/cadastrarPessoasEmpresa";
	}
	
	
	@GetMapping("/agendarEmpresa")
	public String agendarEmpresa(Agendamento agendamento, Model model, HttpSession session) {
		
		//UsuarioCliente cliente = clienteDao.findById(1).orElse(null); 
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosEmpresa(empresa.getId());
		List<UsuarioCliente> listaClienteEmpresa = agendaDao.listarAgendamentosClienteEmpresa(empresa.getId());
		List<ServicoLoja> listaServico = servicoLojaDao.listaServico(empresa.getId());
		List<Profissional> profissional = profissionalDao.listaProfissional(empresa.getId());
		
		for(UsuarioCliente agenda : listaClienteEmpresa) {
			System.out.println(agenda.getEmpresa().getNomeEmpresa());
		}
		
		model.addAttribute("agendamento", agendamento);
		model.addAttribute("listaServico", listaServico);
		model.addAttribute("listaAgendamento", listaAgendamento);
		model.addAttribute("listaClienteEmpresa", listaClienteEmpresa);
		model.addAttribute("profissionalServico", profissional);
		
		return "empresa/agendarEmpresa"; 
	}
	
	@PostMapping("/fazerAgendamento")
	public String fazerAgendamento(Agendamento agendamento, Integer id, Model model, HttpSession session, RedirectAttributes ra) {
		
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
			ra.addFlashAttribute("msg", "Agendamento feito com Sucesso!");
			return"redirect:agendarEmpresa";
		
	}
	
	@GetMapping("/removerAgendamentoEmpresa")
	public String removerAgendamento(Integer codigo,Model model, RedirectAttributes ra) {
		agendaDao.deleteById(codigo);
		ra.addFlashAttribute("msg", "agendamento removido com Sucesso!");
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
	
	@GetMapping("/editarAgendamentoEmpresa")
	public String editarAgendamento(Integer codigo,Model model , HttpSession session) {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<ServicoLoja> listaServico = servicoLojaDao.listaServico(empresa.getId());
		List<Profissional> profissional = profissionalDao.listaProfissional(empresa.getId());
		System.out.println(codigo);
		Agendamento agenda = agendaDao.findById(codigo).orElse(null);

		model.addAttribute("listaServico", listaServico);
		model.addAttribute("profissionalServico", profissional);
		model.addAttribute("agendamento", agenda);
		
		return"empresa/agendarEmpresa";
	}
	
	@GetMapping("/editarUsuarioEmpresa")
	public String editarEmpresa(Integer Id, Model model) {
		UsuarioEmpresa empresa = empresaDao.findById(Id).orElse(null);
		
		model.addAttribute("tipo", tipoEmpresaDao.findAll());
		model.addAttribute("empresa", empresa);
		return "empresa/editarCadastroEmpresa"; 
	}
	
	@PostMapping("/UsuarioEmpresaEditado")
	public String CadastroEmpresaEditado(UsuarioEmpresa empresa, @RequestParam("fileImage") MultipartFile file, HttpSession session, RedirectAttributes ra) {
		try {
			empresa.setFotoPerfil(file.getBytes());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		empresaDao.save(empresa);	
		session.setAttribute("usuarioLogado", empresa);
		ra.addFlashAttribute("msg", "Usuario editado com Sucesso!");
		return "redirect:dadosEmpresa";
	}
	
	
	@GetMapping("/removerloginEmpresa")
	public String removerEmpresa(Integer Id,Model model) {
		clienteDao.deleteById(Id);
		return "/login";
	}
	
	@GetMapping("/fotoPerfil/{idFoto}")
	@ResponseBody
	public byte[] exibirFotoPerfil(@PathVariable("idFoto") Integer idFoto) {
		System.out.println(idFoto);
		System.out.println("imagem");
		UsuarioEmpresa empresa = empresaDao.findById(idFoto).orElse(null);
		return empresa.getFotoPerfil();
	}

	@GetMapping("/planosEmpresa")
	public String planosEmpresa(Model model) {
		List<Planos> planos =  planosDao.findAll();
		
		model.addAttribute("planos",planos);
		return "empresa/planos";
	}
	
	@GetMapping("/salvarPlanosEmpresa")
	public String salvarPlanoEmpresa(Model model, Planos plano,HttpSession session) {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		empresa.setPlano(plano);
		empresaDao.save(empresa);
		return "redirect:planos";
	}
	
	




}
