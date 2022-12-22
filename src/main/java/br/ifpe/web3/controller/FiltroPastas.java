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

@WebFilter("/cliente")
public class FiltroPastas implements Filter  {
	
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)	response;
		
		HttpSession session = req.getSession();
		String path = req.getRequestURI();
		System.out.println(path);
		if(session.getAttribute("tipo").equals("Cliente")) {
			chain.doFilter(request, response);
		}else {
			res.sendRedirect(path);
		}
				
	}

}
