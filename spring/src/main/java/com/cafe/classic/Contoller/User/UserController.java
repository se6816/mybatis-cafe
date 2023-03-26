package com.cafe.classic.Contoller.User;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe.classic.Service.email.EmailCertificationService;
import com.cafe.classic.Service.user.UserService;
import com.cafe.classic.Utils.EmailCertificationUtil;
import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.UserCategory;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.exception.PageNotFoundException;
/**
 * 유저 컨트롤러
 * @author user
 *
 */

@Controller
public class UserController {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private final UserService usvc;
	private final EmailCertificationService emailCertificationService;
	private final ValueOperations<String,String> ValueOperations;
	public UserController(UserService usvc, 
			EmailCertificationService emailCertificationService, StringRedisTemplate redisTemplate) {
		this.usvc = usvc;
		this.emailCertificationService = emailCertificationService;
		this.ValueOperations = redisTemplate.opsForValue();
	}
	/**
	 * 이메일 증명을 완료한다.
	 * @param auth 이메일 인증을 위한 인증번호이다.   
	 * @param email_Check_Addr Session에 저장되어있던 이메일 인증 번호이다.
	 * @param session 세션에 이메일 증명 완료를 입력한다.
	 * @return 인증완료 페이지로 간다.
	 * 
	 */
	@GetMapping("/user/email/{auth}")
	public String emailCheck(@PathVariable("auth") String auth) {
		// redis에 있는 이메일 인증 키와 url 상의 인증키 비교
		emailCertificationService.verifyEmail(auth);
		
		return "user/auth_Success";
		
	}
	
	/**
	 * 비밀번호 변경을 위한 view
	 * @param auth 비번 변경을 위한 인증 키
	 * @param Auth 세션 상의 인증 키
	 * @return 비밀번호 변경을 하는 view
	 * @throws Exception
	 */
	@GetMapping("/user/change/pw/{auth}")
	public String changepwForm(@PathVariable("auth") String auth){
		emailCertificationService.verifyEmail(auth);
		
		return "user/change_pw";
	}
	/**
	 * 회원설정 페이지이다.
	 * @param model
	 * @return
	 */
	@GetMapping("/user/help")
	public String userHelp(Model model) {
		BoardType boardType= BoardType.arcturus;
		model.addAttribute("boardType", boardType);
		return "user/userHelp";
	}
	
	/**
	 * 로그인 페이지
	 * @param principal
	 * @param redirectAttr
	 * @return
	 */
	@GetMapping("/loginForm")
	public String loginForm(@AuthenticationPrincipal PrincipalDetails principal
			,RedirectAttributes redirectAttr) {
		if(principal!=null) {
			redirectAttr.addFlashAttribute("err", "이미 로그인 중입니다");
			return "redirect:/main";
		}
		return "user/loginForm";
	}
	
	/**
	 * 로그인 실패시 보냄
	 * @param id  실패한 아이디
	 * @param reAttribute
	 * @return
	 */
	@GetMapping("/loginForm/fail")
	public String loginFail(@RequestParam(defaultValue="") String id,RedirectAttributes reAttribute) {
		reAttribute.addFlashAttribute("ERR", "아이디나 비밀번호가 틀렸습니다.");
		reAttribute.addFlashAttribute("id", id);
		return "redirect:/loginForm";
	}
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String joinForm() {
		return "user/joinForm";
	}
	/**
	 * 아이디 비밀번호 찾기 페이지
	 * @return
	 */
	@GetMapping("/find_id_pw")
	public String findForm() {
		return "user/find_id_pw";
	}
}
