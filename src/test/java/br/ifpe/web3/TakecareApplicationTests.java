package br.ifpe.web3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.ifpe.web3.controller.LoginController;
import br.ifpe.web3.exceptions.LoginExceptions;

@SpringBootTest
class TakecareApplicationTests {

	@Test
	public void login() {
		String email = "george@teste.com";
		String senha = "4444";
		LoginController login = new LoginController();
		
		try {
			login.EfetuarLogin(email, senha, null);
			Assertions.fail("deveria acontecer uma exceção");
			
		}catch(LoginExceptions e) {
			Assertions.assertEquals("email vazio", e);
			
		}
		
	}

}
