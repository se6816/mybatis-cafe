package com.cafe.classic.Contoller.User;



import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.classic.Utils.EmailServiceUtil;
import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.MESSAGE_CODE;
import com.cafe.classic.domain.RedisKey;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.ReportVO;
import com.cafe.classic.dto.EmailRequest;
import com.cafe.classic.dto.PWRequest;
import com.cafe.classic.exception.AlreadyDataExistsException;
import com.cafe.classic.exception.FormValidException;
import com.cafe.classic.Service.email.EmailService;
import com.cafe.classic.Service.user.UserService;

/**
 * 유저 API 컨트롤러
 * @author user
 *
 */
@RequestMapping("/api")
@RestController
public class UserAPIController {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private final UserService usvc;	
	
	private final EmailService EmailService;
    
	private final ValueOperations<String,String> ValueOperations;
	
	public UserAPIController(UserService usvc,
			com.cafe.classic.Service.email.EmailService emailService, StringRedisTemplate redisTemplate) {
		this.usvc = usvc;
		EmailService = emailService;
		this.ValueOperations = redisTemplate.opsForValue();
	}
	/**
	 * 이메일  인증
	 * @param user
	 * @param session
	 * @param BindingResult
	 * @return
	 */
	@PostMapping(value="/email/check",produces="application/text; charset=utf8")
	public ResponseEntity<String> emailCheck(@Validated(UserVO.ValidateEmail.class)@RequestBody UserVO user,
			BindingResult BindingResult) {		
		if(BindingResult.hasErrors()) {
			FieldError error =BindingResult.getFieldError();
			throw new FormValidException(error.getDefaultMessage());
		}
		if(usvc.isEmail(user.getEmail())>0) {
			throw new AlreadyDataExistsException(MESSAGE_CODE.EMAIL_ALREADY_EXISTS.getMessage());
		}
		
		EmailService.sendEmailCheck(user);
		return new ResponseEntity<String>(MESSAGE_CODE.EMAIL_SEND_SUCCESS.getMessage(),HttpStatus.OK);
	
	}
    /**
     * 아이디 찾기 기능으로 인증 번호를 생성한다.
     * @param Ereq
     * @param BindingResult
     * @return
     */
	@PostMapping(value="/find/id",produces="application/text; charset=utf8")
	public ResponseEntity<String> find_Id(@Valid @RequestBody EmailRequest Ereq
			,BindingResult BindingResult) {
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			UserVO user=usvc.selectMemberFromEmail(Ereq.getEmail());
			if(user.getId()!=null) {
				EmailService.sendID(user);
			}
			
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.EMAIL_SEND_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}
	/**
	 * 비밀번호 찾기 기능이다.
	 * @param Ereq
	 * @param BindingResult
	 * @param session
	 * @return
	 */
	@PostMapping(value="/change/pw",produces="application/text; charset=utf8")
	public ResponseEntity<String> find_Pw(@Valid @RequestBody EmailRequest Ereq
			,BindingResult BindingResult) {
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			UserVO user=usvc.selectMemberFromEmail(Ereq.getEmail());
			if(user.getId()!=null) {
				EmailService.sendFindPw(user);
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
	public ResponseEntity<String> report(@Valid @RequestBody ReportVO report,
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
	/**
	 * 비밀번호 변경 기능이다.
	 * @param password
	 * @param BindingResult  오류에 대한 정보가 담겨있다.
	 * @param principal
	 * @return
	 */
	@PutMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> changePW(@Valid @RequestBody PWRequest pwd
			,BindingResult BindingResult,
			@AuthenticationPrincipal PrincipalDetails principal){
		ResponseEntity<String> resEntity=null;		
		if(!BindingResult.hasErrors()) {
			String email=ValueOperations.get(RedisKey.AUTH_KEY+pwd.getKey());
			if(email==null) {
				return new ResponseEntity<String>(MESSAGE_CODE.KEY_ALREADY_EXPIRED.getMessage(),HttpStatus.BAD_REQUEST);
			}
			UserVO user=usvc.selectMemberFromEmail(email);
			UserVO newUser=new UserVO();
			newUser.setId(user.getId());
			newUser.setPassword(pwd.getNew_passwd());
			usvc.updatePW(newUser);
			resEntity=new ResponseEntity<String>(MESSAGE_CODE.CHANGE_PASSWORD_SUCCESS.getMessage(),HttpStatus.OK);

		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}
    /**
     * 회원가입 기능이다.
     * @param UserVO
     * @param BindingResult 에러결과
     * @return
     */
	@PostMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> join(@Valid @RequestBody UserVO UserVO,
			BindingResult BindingResult){
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
			if(ValueOperations.get(RedisKey.EMAIL_AUTHORIZATION_KEY+UserVO.getEmail())==null) {
				resEntity = new ResponseEntity<String>(MESSAGE_CODE.EMAIL_CHECK_FAIL.getMessage(),HttpStatus.BAD_REQUEST);
				return resEntity;
			}
			usvc.insertMember(UserVO);
			resEntity = new ResponseEntity<String>(MESSAGE_CODE.JOIN_USER_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
		}
		return resEntity;
	}
	/**
	 * 유저 탈퇴와 관련된 API
	 * @param principal 로그인 정보
	 * @return
	 */
	@DeleteMapping(value="/user",produces="application/text; charset=utf8")
	public ResponseEntity<String> deleteMember(@AuthenticationPrincipal PrincipalDetails principal){
		ResponseEntity<String> resEntity=null;
		usvc.deleteMember(principal.getId());
		resEntity = new ResponseEntity<String>(MESSAGE_CODE.USER_WITHDRAW_SUCCESS.getMessage(),HttpStatus.OK);
		return resEntity;
	} 
}
