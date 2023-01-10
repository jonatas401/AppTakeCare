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
			helper.setSubject(subject);
			helper.setText("<p>troca de senha</p>",
					true);
			mailSender.send(mail);
			System.out.println("email enviado com sucesso");
			
			
//			SimpleMailMessage message =  new SimpleMailMessage();
//			
//			message.setFrom("takecareeeeeeeeee@gmail.com");
//			message.setTo(emailTo);
//			message.setSubject(subject);
//			
//			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
