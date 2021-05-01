package com.test.Utils;

import java.util.UUID;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.test.domain.UserVO;

public class EmailUtil {
	private final JavaMailSender sender;
	private final String domain="https://localhost:8443/spring/user/change/pw/";
	public EmailUtil(JavaMailSender sender) {
		this.sender = sender;
	}
	public void sendFindPw(UserVO user,HttpSession session) {
		String auth=getAuth(session);
		if(session.getAttribute(auth)==null) {
			session.setAttribute(auth, user.getId());
		}
		MimeMessagePreparator preparator= new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				mimeMessage.setSubject("비밀번호 변경메일입니다","UTF-8");
				mimeMessage.setText("비밀번호 변경을 위해서 아래 링크를 들어가주세요\n"
					+domain+auth,"UTF-8");
			}
		};
		
		try {
			this.sender.send(preparator);
		}
		catch(MailException mex){
			System.out.println(mex.getMessage());
		}
	}
	public void sendID(UserVO user) {
		MimeMessagePreparator preparator= new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				mimeMessage.setSubject("아이디 찾기 메일입니다","UTF-8");
				mimeMessage.setText("해당 아이디는"+
								user.getId()+"입니다","UTF-8");
			}
		};
		
		try {
			this.sender.send(preparator);
		}
		catch(MailException mex){
			System.out.println(mex.getMessage());
		}
	}
	
	public String getAuth(HttpSession session) {
		String Auth="";
		if(session.getAttribute("Auth")!=null) {
			Auth=(String)session.getAttribute("Auth");
		}
		else {
			Auth=UUID.randomUUID().toString();
			session.setAttribute("Auth", Auth);
		}
		return Auth;
	}

	
	
}
