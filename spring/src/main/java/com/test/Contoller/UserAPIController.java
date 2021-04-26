package com.test.Contoller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.Service.UserService;
import com.test.Utils.AES256Util;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.ERROR_CODE;
import com.test.domain.RoleType;
import com.test.domain.UserVO;
import com.test.domain.reportVO;
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
	
	@PostMapping(value="/report",produces="application/text; charset=utf8")
	public ResponseEntity<String> report(@Valid @RequestBody reportVO report,
			BindingResult BindingResult,
			@AuthenticationPrincipal PrincipalDetails principal) {
		report.setId(principal.getId());
		usvc.report(report);
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			resEntity=new ResponseEntity<String>(ERROR_CODE.REPORT_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		
		
		return resEntity;
	}
	@PutMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> changePW(@Valid @RequestBody PWRequest password
			,BindingResult BindingResult
			,@AuthenticationPrincipal PrincipalDetails principal){
		ResponseEntity<String> resEntity=null;		
		if(!encoder.matches(password.getCur_passwd(),principal.getPassword())) {
			resEntity=new ResponseEntity<String>(ERROR_CODE.CUR_PASSWORD_FAIL.getMessage(),HttpStatus.BAD_REQUEST);
			return resEntity;
		}
		if(!BindingResult.hasErrors()) {
			UserVO user= new UserVO();
			user.setId(principal.getId());
			user.setPassword(password.getNew_passwd());
			usvc.updatePW(user);
			resEntity=new ResponseEntity<String>(ERROR_CODE.CHANGE_PASSWORD_SUCCESS.getMessage(),HttpStatus.OK);
			Authentication authentication = AuthManager.authenticate(new UsernamePasswordAuthenticationToken(principal.getId(), password.getNew_passwd()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}

	@PostMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> join(@Valid @RequestBody UserVO UserVO,
			BindingResult BindingResult){
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			if(usvc.isID(UserVO.getId())>0) {
				resEntity = new ResponseEntity<String>(ERROR_CODE.ALREADY_EXIST_USERNAME.getMessage(),HttpStatus.BAD_REQUEST);
				return resEntity;
			}
			if(usvc.isUserName(UserVO.getUsername())>0){
				resEntity = new ResponseEntity<String>(ERROR_CODE.ALREADY_EXIST_USERNAME.getMessage(),HttpStatus.BAD_REQUEST);
				return resEntity;
			}
			usvc.insertMember(UserVO);
			Authentication authentication = AuthManager.authenticate(new UsernamePasswordAuthenticationToken(UserVO.getId(), UserVO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			resEntity = new ResponseEntity<String>(ERROR_CODE.JOIN_USER_SUCCESS.getMessage(),HttpStatus.OK);
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
		resEntity = new ResponseEntity<String>(ERROR_CODE.USER_WITHDRAW_SUCCESS.getMessage(),HttpStatus.OK);
		return resEntity;
	} 
}
