package br.ifpe.web3.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import br.ifpe.web3.DAO.PlanosDAO;
import br.ifpe.web3.DAO.ProfissionalDAO;
import br.ifpe.web3.DAO.ProfissionalServicoDAO;
import br.ifpe.web3.DAO.ServicoLojaDAO;
import br.ifpe.web3.DAO.TipoEmpresaDAO;
import br.ifpe.web3.exceptions.LoginExceptions;
import br.ifpe.web3.model.Agendamento;
import br.ifpe.web3.model.Noticia;
import br.ifpe.web3.model.Planos;
import br.ifpe.web3.model.Profissional;
import br.ifpe.web3.model.ProfissionalServico;
import br.ifpe.web3.model.ServicoLoja;
import br.ifpe.web3.model.UsuarioCliente;
import br.ifpe.web3.model.UsuarioEmpresa;
import br.ifpe.web3.util.UsuarioEmail;

@Controller
public class ControllerEmpresa {
	
	
	@Autowired
	private ClienteDAO clienteDao;
	@Autowired
	private EmpresaDAO empresaDao;
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
	@Autowired
	private NoticiaDAO noticiaDao;
	@Autowired
	UsuarioEmail usuarioemail;


	
	
	@GetMapping("/dadosEmpresa")
	public String dadosEmpresa() {
		System.out.println();
		return "empresa/dadosEmpresa";
	}
	
	@GetMapping("/configuracaoEmpresa")
	public String configuracaoEmpresa() {
		
		return "empresa/configuracaoEmpresa";
	}
	
	
	@GetMapping("/portifolioEmpresa")
	public String portifolio(Model model, HttpSession session) {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		model.addAttribute("servicoLoja",servicoLojaDao.listaServico(empresa.getId()));
		System.out.println();
		return "empresa/addPortifolio";
	}
	
	@GetMapping("/noticiaEmpresa")
	public String noticia(Noticia noticia,Model model, HttpSession session)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<Noticia> listaNoticia = noticiaDao.findByEmpresaId(empresa.getId());
		model.addAttribute("listaNoticia",listaNoticia);
		System.out.println();
		return "empresa/noticiaEmpresa";
	}
	
	@PostMapping("/salvarNoticiaEmpresa")
	public String salvarNoticia(Noticia noticia,Model model, HttpSession session)  {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		noticia.setData(LocalDate.now());
		noticia.setHora(LocalTime.now());
		noticia.setEmpresa(empresa);
			
		noticiaDao.save(noticia);
		return "redirect:noticiaEmpresa";
	}
	
	@GetMapping("/noticiaUrgenteEmpresa")
	public String noticiaUrgente(Model model, Integer codigo,HttpSession session, RedirectAttributes ra)  {
		Noticia noticia =  noticiaDao.findById(codigo).orElse(null);
		noticia.setUrgente(true);
		noticiaDao.save(noticia);	
		ra.addFlashAttribute("msg", "Noticia Urgente Notificada!");
		return "redirect:noticiaEmpresa";
	}
	
	@GetMapping("/removerNoticiaEmpresa")
	public String removerNoticia(Integer codigo,Model model, HttpSession session, RedirectAttributes ra) {
		noticiaDao.deleteById(codigo);	
		ra.addFlashAttribute("msg", "Noticia removido com Sucesso!");
		return "redirect:noticiaEmpresa";
	}
	
	@GetMapping("/editarNoticiaEmpresa")
	public String editarNoticia(Model model,Integer codigo, RedirectAttributes ra)  {
		Noticia noticia = noticiaDao.findById(codigo).orElse(null);
		model.addAttribute("noticia",noticia);
	
		//ra.addFlashAttribute("msg", "Noticia editada com Sucesso!");
		return "empresa/noticiaEmpresa";
	}
	
	
	@GetMapping("/servicoEmpresa")
	public String servicoEmpresa(ServicoLoja servico, Model model, HttpSession session) {
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<ServicoLoja> lista = servicoLojaDao.listaServico(chaveId.getId());
		
		model.addAttribute("listaServicos", lista);
		model.addAttribute("servico", servico);
		return "empresa/servicoEmpresa";
	}
	
	@PostMapping("/servicoEmpresa")
	public String salvarServico(ServicoLoja servico, Model model, HttpSession session, @RequestParam("fileImage") MultipartFile file, RedirectAttributes ra) {
		UsuarioEmpresa chaveId =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		byte[] arquivo = null;
		servico.setEmpresa(chaveId);
		if(servico.getId() != null) {
		arquivo = this.exibirImagem(servico.getId());
		}
		try {
			servico.setFoto(file.getBytes());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		if(file.isEmpty()) {
			servico.setFoto(arquivo);
		}
		servicoLojaDao.save(servico);
		ra.addFlashAttribute("msg", "Servico salvo com Sucesso!");
		return "redirect:servicoEmpresa";
	}
	
	@GetMapping("/editarServicoEmpresa")
	public String editarServicoEmpresa(Integer codigo, Model model, HttpSession session)  {
		ServicoLoja servico = servicoLojaDao.findById(codigo).orElse(null);
		
		model.addAttribute("servico", servico);
		
		return "empresa/servicoEmpresa";
	}
	
	@GetMapping("/removerServicoEmpresa")
	public String removerServico(ServicoLoja servico, Integer codigo, RedirectAttributes ra) {
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
	

	@GetMapping("/removerProfissionalServicoEmpresa")
	public String removerServicoProfissional(Profissional profissional, Integer codigo, RedirectAttributes ra) throws LoginExceptions {	
	
		
		profissionalServicoDao.deleteById(codigo);	
		ra.addFlashAttribute("msg", "Servico desalocado do profissional com Sucesso!");
		return "redirect:profissionalEmpresa";
	}
	
	@GetMapping("/removerProfissionalEmpresa")
	public String removerProfissional(Profissional profissional, Integer codigo, RedirectAttributes ra) throws LoginExceptions {	
		List<ProfissionalServico> listaServ = profissionalServicoDao.findByProfissionalId(codigo);
		List<Agendamento> listAgenda = agendaDao.findByProfissionalId(codigo);
		
		
		if(listaServ.isEmpty() && listAgenda.isEmpty()) {
			profissionalDao.deleteById(codigo);	
			ra.addFlashAttribute("msg", " profissional removido com Sucesso!");
			return "redirect:profissionalEmpresa";
		}
		
		
		ra.addFlashAttribute("msg", "Verifique se o profissional não está sendo usado!");
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
	public String editarPessoasEmpresa(Integer codigo, Model model , HttpSession session)  {
		UsuarioCliente cliente = clienteDao.findById(codigo).orElse(null);
		model.addAttribute("cadastro", cliente);
		return "empresa/cadastrarPessoasEmpresa";
	}
	
	
	@GetMapping("/agendarEmpresa")
	public String agendarEmpresa(Agendamento agendamento, Model model, HttpSession session) {
		
		//UsuarioCliente cliente = clienteDao.findById(1).orElse(null); 
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		List<Agendamento> listaAgendamento = agendaDao.listarAgendamentosEmpresa(empresa.getId());
		List<UsuarioCliente> listaClienteEmpresa = clienteDao.listaClientesCadastrados(empresa.getId());
		List<ServicoLoja> listaServico = servicoLojaDao.listaServico(empresa.getId());
		List<Profissional> profissional = profissionalDao.listaProfissional(empresa.getId());
		List<Agendamento> list = new ArrayList<Agendamento>();
		Integer pr = 0;
		
		for(Agendamento i : listaAgendamento) {
			int pri = i.getCliente().getId();
			int a = i.getCliente().getId().compareTo(pr);
			pr = pri;
			if(a != 0) {
				list.add(i);
			}
			for(UsuarioCliente cli : listaClienteEmpresa) {
				if(cli.getId().compareTo(pr) == 0) {
					list.remove(i);
				}
			}
			
			
		}
		model.addAttribute("listaClientes", list);
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
			if(agendamento.getProfissional() ==  null) {
				System.out.println("nome vazio");
				return"redirect:estabelecimentoCliente?id=" + id;
			}
			UsuarioEmpresa empresa =  empresaDao.findById(id).orElse(null);
			System.out.println(empresa.getId());
			UsuarioCliente chave =(UsuarioCliente) session.getAttribute("usuarioLogado");
			System.out.println(chave.getNome());
			agendamento.setEmpresa(empresa);
			agendamento.setCliente(chave);
			//clienteAgendadoDao.save(chave);
			agendaDao.save(agendamento);
			return"redirect:agendamentosCliente";
			
		}
			
			System.out.println("confirmado!");		
			ra.addFlashAttribute("msg", "Agendamento feito com Sucesso!");
			return"redirect:agendarEmpresa";
		
	}
	
	@GetMapping("/removerAgendamentoEmpresa")
	public String removerAgendamento(Integer codigo, String email,Model model, RedirectAttributes ra, HttpSession session) {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		try {
			usuarioemail.enviarEmailNotificacao(empresa.getEmail(),email, "Agendamento Cancelado", "O Estabelecimento "+ empresa.getNomeEmpresa()+" cancelou seu agendamento"
																														+ "para mais Informações contate o estabelecimento: "+empresa.getNumero());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		agendaDao.deleteById(codigo);
		ra.addFlashAttribute("msg", "agendamento removido com Sucesso!");
		
		return"redirect:agendarEmpresa";
	}
	
	@GetMapping("/confirmarAgendamentoEmpresa")
	public String confirmarAgendamento(Integer codigo,String email,Model model,HttpSession session) {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		try {
			usuarioemail.enviarEmailNotificacao(empresa.getEmail(),email, "Agendamento confirmado", "O Estabelecimento "+ empresa.getNomeEmpresa()+" confirmou seu agendamento,"
																														+ " para mais Informações contate o estabelecimento: "+empresa.getNumero());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(codigo);
		Agendamento agenda = agendaDao.findById(codigo).orElse(null);
		agenda.setStatus(true);
		agendaDao.save(agenda);
		return"redirect:agendarEmpresa";
	}
	
	@GetMapping("/editarAgendamentoEmpresa")
	public String editarAgendamento(Integer codigo,String email,Model model , HttpSession session) {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		
		try {
			usuarioemail.enviarEmailNotificacao(empresa.getEmail(),email, "Agendamento editado", "O Estabelecimento "+ empresa.getNomeEmpresa()+" editou seu agendamento, entre no site para verificar o agendamento "
																														+ " para mais Informações contate o estabelecimento: "+empresa.getNumero());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<ServicoLoja> listaServico = servicoLojaDao.listaServico(empresa.getId());
		List<Profissional> profissional = profissionalDao.listaProfissional(empresa.getId());
		System.out.println(codigo);
		Agendamento agenda = agendaDao.findById(codigo).orElse(null);

		model.addAttribute("listaServico", listaServico);
		model.addAttribute("profissionalServico", profissional);
		model.addAttribute("agendamento", agenda);
		model.addAttribute("listaAgendamento", agenda);
		
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
		byte[] arq = this.exibirFotoPerfil(empresa.getId());
		try {
			empresa.setFotoPerfil(file.getBytes());
		} catch (IOException e) {
			ra.addFlashAttribute("msg", "Selecione uma foto Com até 1Mb");
			return "redirect:editarUsuarioEmpresa";
		}
		if(file.isEmpty()) {
			empresa.setFotoPerfil(arq);
		}
		
		empresaDao.save(empresa);	
		session.setAttribute("usuarioLogado", empresa);
		ra.addFlashAttribute("msg", "Usuario editado com Sucesso!");
		return "redirect:dadosEmpresa";
	}
	
	@GetMapping("/deletarUsuarioEmpresa")
	public String deletarEmpresa(Integer Id, Model model, RedirectAttributes ra, HttpSession session) throws LoginExceptions {
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		if(empresa.getId() == Id) {
		List<Agendamento> agenda = agendaDao.listarAgendamentosEmpresa(Id);
		List<ServicoLoja> servico = servicoLojaDao.listaServico(Id);	
		List<UsuarioCliente> cliente = clienteDao.listaClientesCadastrados(Id);
		List<Profissional> profissional = profissionalDao.listaProfissional(Id);
		List<Noticia> noticia = noticiaDao.findByEmpresaId(Id);
		
		if(agenda.isEmpty() != true) {
 			System.out.println("contem agendamento");
 			ra.addFlashAttribute("msg" , "verifique se você não tem nenhum agendamento!");
 			return "redirect:dadosEmpresa";
		}
		
		for(Profissional prof :profissional) {
			System.out.println(prof.getNome());
			List<ProfissionalServico> profServ = profissionalServicoDao.findByProfissionalId(prof.getId());
			System.out.println("listar profissional");
			System.out.println(profServ.isEmpty());
				for(ProfissionalServico serv : profServ) {
					System.out.println("listar servicoprofissional");
					System.out.println(serv.getId());
					ProfissionalServico se =  profissionalServicoDao.findById(serv.getId()).orElse(null);
					System.out.println(se.getId());
					profissionalServicoDao.deleteById(se.getId());
				}
		
			
			profissionalDao.deleteById(prof.getId());
			
		}
		for(ServicoLoja serv: servico) {
			servicoLojaDao.deleteById(serv.getId());
		}
		for(UsuarioCliente cli : cliente) {
			clienteDao.deleteById(cli.getId());
		}
		for(Noticia not : noticia) {
			noticiaDao.deleteById(not.getId());
		}
	 		
		
		empresaDao.deleteById(Id);
		
		}
		ra.addFlashAttribute("msg" , "Sua conta foi apagada com sucesso!");
		return "redirect:login";
	
	
	}
	

	
	@GetMapping("/fotoPerfil/{idFoto}")
	@ResponseBody
	public byte[] exibirFotoPerfil(@PathVariable("idFoto") Integer idFoto) {
		System.out.println(idFoto);
		UsuarioEmpresa empresa = empresaDao.findById(idFoto).orElse(null);
		return empresa.getFotoPerfil();
	}

	@GetMapping("/planosEmpresa")
	public String planosEmpresa(Model model) {
		List<Planos> planos =  planosDao.findAll();
		
		model.addAttribute("planos",planos);
		return "empresa/planos";
	}
	
	@GetMapping("/salvarPlanoEmpresa")
	public String salvarPlanoEmpresa(Model model, Integer codigo,HttpSession session) {
		Planos plano = planosDao.findById(codigo).orElse(null);
		System.out.println(plano.getNome());
		UsuarioEmpresa empresa =(UsuarioEmpresa) session.getAttribute("usuarioLogado");
		empresa.setPlano(plano);
		empresaDao.save(empresa);
		return "redirect:planosEmpresa";
	}
	
	
	

}
