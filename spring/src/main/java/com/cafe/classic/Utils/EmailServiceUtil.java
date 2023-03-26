package com.cafe.classic.Utils;

import java.time.Duration;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.cafe.classic.domain.RedisKey;
import com.cafe.classic.domain.UserVO;
/**
 * 이메일을 보내는 Util
 * @author user
 *
 */
@Component
public class EmailServiceUtil {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private final JavaMailSender sender;
	private final ValueOperations<String,String> ValueOperations;
	/**
	 * 비번찾기 요청 시 기본 url
	 */
	private final String pw_domain="https://localhost:8443/spring/user/change/pw/";
	/**
	 * 이메일메일 인증 요청 시 기본 url
	 */
	private final String email_domain="https://localhost:8443/spring/user/email/";
	
	/**
	 * 생성자
	 * @param sender
	 */
	public EmailServiceUtil(JavaMailSender sender,StringRedisTemplate stringRedisTemplate) {
		this.sender = sender;
		this.ValueOperations=stringRedisTemplate.opsForValue();
	}
	
	/**
	 * 비번 찾기 메일을 보낸다.
	 * @param user 유저 정보
	 * @param session 세션
	 */
	public void sendFindPw(UserVO user,HttpSession session) {
		// 비번을 변경할 수 있는 인증키를 가져온다.
		String auth=getAuth(session);
		// 세션에 인증키가 해당 아이디를 담게담게 한다
		session.setAttribute(auth, user.getId());
		
		MimeMessagePreparator preparator= new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				mimeMessage.setSubject("비밀번호 변경메일입니다","UTF-8");
				mimeMessage.setText("비밀번호 변경을 위해서 아래 링크를 들어가주세요\n"
					+pw_domain+auth,"UTF-8");
			}
		};
		
		try {
			this.sender.send(preparator);
			logger.info("비번 찾기 시도 아이디 : {}",user.getId());
		}
		catch(MailException mex){
			logger.error("{}",mex.getMessage());
		}
	}
	/**
	 * 아이디 찾기 메일을 보낸다.
	 * @param user 유저 정보
	 */
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
			logger.info("아이디 찾기 시도 : {}",user.getEmail());
		}
		catch(MailException mex){
			logger.error("{}",mex.getMessage());
		}
	}
	/**
	 * 이메일 인증을 위한  메일을 보낸다.ㅇ
	 * @param user 유저 정보
	 * @param session
	 */
	public void sendEmailCheck(UserVO user) {
		// 이메일 인증 키를 가져온다.
		String addr=getEmailCheck(user);
		MimeMessagePreparator preparator= new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				mimeMessage.setSubject("이메일 확인 메일입니다","UTF-8");
				mimeMessage.setText("해당 주소로 접속해주세요"+
				email_domain+addr,"UTF-8");
			}
		};
		try {
			this.sender.send(preparator);
			logger.info("이메일 인증 시도 : {}",user.getEmail());
		}
		catch(MailException mex){
			logger.error(mex.getMessage());
		}
	}
	/**
	 * 이메일 체크를 위한 인증키를 생성하고 세션에 저장한다.ㅇ
	 * @param session
	 * @return
	 */
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
	/**
	 * 
	 * @param session
	 * @return
	 */
	private String getEmailCheck(UserVO user) {
		String Addr=UUID.randomUUID().toString();
		ValueOperations.set(Addr, user.getEmail(),Duration.ofMinutes(3));
		return Addr;
	}

	
	
}
