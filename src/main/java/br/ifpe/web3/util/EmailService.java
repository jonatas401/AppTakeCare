package br.ifpe.web3.util;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void enviarEmail(String emailTo, String subject) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailTo);
			helper.setSubject("Codigo de recuperação");
			helper.setText("<p>troca de senha seu codigo:</p>"+ subject
					,true);
			mailSender.send(mail);
			System.out.println("email enviado com sucesso");
			
	

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviarNotificacao(String emailFrom,String emailTo, String subject, String texto) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(subject);
			helper.setText(texto
					,true);
			mailSender.send(mail);
			System.out.println("email enviado com sucesso");
			
	

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
