package com.test.Contoller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.test.Service.UserService;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.BoardType;
import com.test.domain.UserCategory;
import com.test.domain.UserVO;


@Controller
public class UserController {
	
	@Autowired
	UserService Usvc;
	@GetMapping("/user/email/{auth}")
	public String emailCheck(@PathVariable("auth") String auth,
			@SessionAttribute String email_Check_Addr,HttpSession session) throws Exception {
		if(!email_Check_Addr.equals(auth)) {
			throw new Exception();
		}
		
		session.setAttribute("email_Check_Success", true);
		
		return "user/auth_Success";
		
	}
	
	@GetMapping("/user/change/pw/{auth}")
	public String changepwForm(@PathVariable("auth") String auth
			,@SessionAttribute String Auth) throws Exception {
		if(!auth.equals(Auth)) {
			throw new Exception();
		}
		return "user/change_pw";
	}
	@GetMapping("/user/help")
	public String userHelp(Model model) {
		BoardType boardType= BoardType.ARCTURUS;
		model.addAttribute("boardType", boardType);
		return "user/userHelp";
	}
	@GetMapping("/user/info/{username}/{userCategory}")
	public String userInfo(Model model,@ModelAttribute("username") @PathVariable("username") String username
			               ,@ModelAttribute("userCategory") @PathVariable("userCategory") UserCategory userCategory) {
		BoardType boardType= BoardType.ARCTURUS;
		model.addAttribute("boardType", boardType);
		return "user/userInfo";
	}
	
	@GetMapping("/loginForm")
	public String loginForm(@AuthenticationPrincipal PrincipalDetails principal
			,RedirectAttributes redirectAttr) {
		if(principal!=null) {
			redirectAttr.addFlashAttribute("err", "이미 로그인 중입니다");
			return "redirect:/bbs/main";
		}
		return "user/loginForm";
	}
	
	
	@GetMapping("/loginForm/fail")
	public String loginFail(@RequestParam(defaultValue="") String id,RedirectAttributes reAttribute) {
		reAttribute.addFlashAttribute("ERR", "아이디나 비밀번호가 틀렸습니다.");
		reAttribute.addFlashAttribute("id", id);
		return "redirect:/loginForm";
	}
	@GetMapping("/join")
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("/find_id_pw")
	public String findForm() {
		return "user/find_id_pw";
	}
}
