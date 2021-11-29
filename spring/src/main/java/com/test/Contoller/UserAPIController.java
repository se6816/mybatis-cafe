package com.test.Contoller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.test.Service.UserService;
import com.test.Utils.AES256Util;
import com.test.Utils.EmailUtil;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.MESSAGE_CODE;
import com.test.domain.RoleType;
import com.test.domain.UserVO;
import com.test.domain.reportVO;
import com.test.dto.EmailRequest;
import com.test.dto.PWRequest;


@RequestMapping("/api")
@RestController
public class UserAPIController {
	
	@Autowired
	UserService usvc;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager AuthManager;
	
	@Autowired
	JavaMailSender sender;
	
	
	
	@PostMapping(value="/email/check",produces="application/text; charset=utf8")
	public ResponseEntity<String> emailCheck(@Validated(UserVO.ValidateEmail.class)@RequestBody UserVO user,HttpSession session,
			BindingResult BindingResult) {
		ResponseEntity<String> resEntity=null;
		EmailUtil emailUtil=new EmailUtil(sender);
		
		if(!BindingResult.hasErrors()) {
			if(usvc.isEmail(user.getEmail())>0) {
				return new ResponseEntity<String>(MESSAGE_CODE.EMAIL_ALREADY_EXISTS.getMessage(),HttpStatus.BAD_REQUEST);
			}
			emailUtil.sendEmailCheck(user,session);
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.EMAIL_SEND_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}
	
	@PostMapping(value="/find/id",produces="application/text; charset=utf8")
	public ResponseEntity<String> find_Id(@Valid @RequestBody EmailRequest Ereq
			,BindingResult BindingResult) {
		ResponseEntity<String> resEntity=null;
		EmailUtil emailUtil=new EmailUtil(sender);
		if(!BindingResult.hasErrors()) {
			UserVO user=usvc.selectMemberFromEmail(Ereq.getEmail());
			if(user.getId()!=null) {
				emailUtil.sendID(user);
			}
			
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.EMAIL_SEND_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}
	@PostMapping(value="/change/pw",produces="application/text; charset=utf8")
	public ResponseEntity<String> find_Pw(@Valid @RequestBody EmailRequest Ereq
			,BindingResult BindingResult
			, HttpSession session) {
		ResponseEntity<String> resEntity=null;
		EmailUtil emailUtil=new EmailUtil(sender);
		if(!BindingResult.hasErrors()) {
			UserVO user=usvc.selectMemberFromEmail(Ereq.getEmail());
			if(user.getId()!=null) {
				emailUtil.sendFindPw(user,session);
			}
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.EMAIL_SEND_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}
	
	
	@PostMapping(value="/report",produces="application/text; charset=utf8")
	public ResponseEntity<String> report(@Valid @RequestBody reportVO report,
			BindingResult BindingResult,
			@AuthenticationPrincipal PrincipalDetails principal) {
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			report.setId(principal.getId());
			usvc.report(report);
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.REPORT_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		
		
		return resEntity;
	}
	@PutMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> changePW(@Valid @RequestBody PWRequest password
			,BindingResult BindingResult,HttpSession session,
			@AuthenticationPrincipal PrincipalDetails principal){
		ResponseEntity<String> resEntity=null;		
		if(!BindingResult.hasErrors()) {
			UserVO user= new UserVO();
			String id=(String)session.getAttribute(password.getKey());
			if(id==null) {
				return new ResponseEntity<String>(MESSAGE_CODE.KEY_ALREADY_EXPIRED.getMessage(),HttpStatus.BAD_REQUEST);
			}
			user.setId(id);
			user.setPassword(password.getNew_passwd());
			usvc.updatePW(user);
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.CHANGE_PASSWORD_SUCCESS.getMessage(),HttpStatus.OK);
			if(principal!=null) {
				Authentication authentication = AuthManager.authenticate(new UsernamePasswordAuthenticationToken(id, password.getNew_passwd()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			session.removeAttribute(password.getKey());
			session.removeAttribute("Auth");
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}

	@PostMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> join(@Valid @RequestBody UserVO UserVO,
			BindingResult BindingResult,HttpSession session){
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			if(usvc.isID(UserVO.getId())>0) {
				resEntity = new ResponseEntity<String>(MESSAGE_CODE.ALREADY_EXIST_ID.getMessage(),HttpStatus.BAD_REQUEST);
				return resEntity;
			}
			if(usvc.isUserName(UserVO.getUsername())>0){
				resEntity = new ResponseEntity<String>(MESSAGE_CODE.ALREADY_EXIST_USERNAME.getMessage(),HttpStatus.BAD_REQUEST);
				return resEntity;
			}
			if(session.getAttribute("email_Check_Success")==null || !((String)session.getAttribute("email")).equals(UserVO.getEmail())) {
				resEntity = new ResponseEntity<String>(MESSAGE_CODE.EMAIL_CHECK_FAIL.getMessage(),HttpStatus.BAD_REQUEST);
				return resEntity;
			}
			usvc.insertMember(UserVO);
			session.removeAttribute("email_Check_Success");
			session.removeAttribute("email_Check_Addr");
			session.removeAttribute("email");
			resEntity = new ResponseEntity<String>(MESSAGE_CODE.JOIN_USER_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
		}
		return resEntity;
	}
	@DeleteMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> deleteMember(@AuthenticationPrincipal PrincipalDetails principal){
		ResponseEntity<String> resEntity=null;
		usvc.deleteMember(principal.getId());
		resEntity = new ResponseEntity<String>(MESSAGE_CODE.USER_WITHDRAW_SUCCESS.getMessage(),HttpStatus.OK);
		return resEntity;
	} 
}
