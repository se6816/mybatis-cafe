package com.test.Contoller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.Service.UserService;
import com.test.config.auto.PrincipalDetails;
import com.test.domain.BoardType;
import com.test.domain.UserVO;


@Controller
public class UserController {
	
	@Autowired
	UserService Usvc;
	@ModelAttribute("UserCategory")
	public List<Entry<String,String>> UserCategory(){
		List<Entry<String,String>> list= new ArrayList<>();
		list.add(new AbstractMap.SimpleEntry<String,String>("회원정보보기","userInfo"));
		list.add(new AbstractMap.SimpleEntry<String,String>("비밀번호변경","changepw"));
		return list;
	}
	
	@GetMapping("/err/accessDenied")
	public String errPage(){
		return "error/403";
	}
	@GetMapping("/user/changepw")
	public String changepwForm(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
		BoardType boardType= BoardType.ARCTURUS;
		model.addAttribute("boardType", boardType);
		return "user/change_pw";
	}
	
	@GetMapping("/user/userInfo")
	public String userInfo(Model model) {
		BoardType boardType= BoardType.ARCTURUS;
		model.addAttribute("boardType", boardType);
		return "user/userInfo";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/loginForm/fail")
	public String loginFormFail(RedirectAttributes reAttribute) {
		reAttribute.addFlashAttribute("ERR", "아이디나 비밀번호가 틀렸습니다.");
		return "redirect:/loginForm";
	}
	@GetMapping("/join")
	public String joinForm() {
		return "user/joinForm";
	}
}
