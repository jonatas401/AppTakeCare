package br.ifpe.web3.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/*")
public class FiltroUsuario implements Filter {

	private String[] pathLiberados = {"/", "/cadastro", "/login", "/opcaoCadastro", "/esqueciSenha", "/efetuarLogin"
									,"/UsuarioCliente" ,"/UsuarioEmpresa","/h2-console(.*)", "/css/(.*)", "/img/(.*)", "/logoff"
									, "/fazerAgendamento", "/removerAgendamentoCliente", "/salvarUsuarioEmpresa"
									, "/salvarUsuarioCliente", "/imagem/(.*)"};
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		String path = req.getRequestURI();
		
		for(String livre : pathLiberados) {
			if(path.matches(livre)) {
				chain.doFilter(request, response);
				return;
			}
			
		}
		
		
		if(session != null && session.getAttribute("usuarioLogado") != null ) {
			System.out.println("URL " + path);
			System.out.println("fora");
			
			if(path.contains("Cliente")  && session.getAttribute("tipo").equals("Cliente")) {
				chain.doFilter(request, response);
				System.out.println("cliente");
				return;
			}
			if(path.contains("Empresa") && session.getAttribute("tipo").equals("Empresa")) {
				System.out.println("empresa");
				chain.doFilter(req, res);
				System.out.println("empresa");
				return;
			}
			
		}
		else {
			res.sendRedirect("/login");
		}
		
	}

	
	
}
