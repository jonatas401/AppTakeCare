package br.ifpe.web3.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.ifpe.web3.exceptions.LoginExceptions;

public class LoginControllerTest {

	
		@Test
		public void login() {
			String email = "george@teste.com";
			String senha = "4444";
			LoginController login = new LoginController();
			
			try {
				login.EfetuarLogin(email, senha, null,null);
				Assertions.fail("deveria acontecer uma exceção");
				
			}catch(LoginExceptions e) {
				Assertions.assertEquals("email vazio", e);
				
			}
			
		}

	}


