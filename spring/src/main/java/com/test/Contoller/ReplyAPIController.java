package com.test.Contoller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.Service.ReplyService;
import com.test.Utils.AES256Util;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.BoardType;
import com.test.domain.ERROR_CODE;
import com.test.domain.ReplyVO;
import com.test.domain.replyPageCriteria;


@RestController
public class ReplyAPIController {
	
	@Autowired
	private ReplyService Rsvc;
	@Autowired
	private AES256Util aes;
	
	@GetMapping(value="/reply/{boardType}/{bid}",produces="application/json; charset=utf8")
	public HashMap<String,Object> replyGet(@PathVariable("boardType") BoardType boardType, @RequestParam int page, 
		Authentication auth,@PathVariable("bid") int bid) throws Exception{
		HashMap<String, Object> map=new HashMap<String,Object>();
		List<String> del= new ArrayList<String>();
		replyPageCriteria replyPageCriteria= new replyPageCriteria();
		replyPageCriteria.setPage(page);
		map.put("list", Rsvc.listCriteria(bid, replyPageCriteria, boardType));
		map.put("status",HttpStatus.OK);
		return map;
	}
	@PostMapping(value="/api/reply/{boardType}",produces="application/text; charset=utf8")
	public ResponseEntity<String> replyPost(@PathVariable("boardType") BoardType boardType,@Valid @RequestBody ReplyVO reply,
			BindingResult BindingResult, 
			@AuthenticationPrincipal PrincipalDetails principal) throws Exception{
		ResponseEntity<String> resEntity=null;
		
		if(!BindingResult.hasErrors()) {
			reply.setWriter(principal.getUsername());
			System.out.println(reply.toString());
			Rsvc.write(reply, boardType);
			resEntity = new ResponseEntity<String>(ERROR_CODE.REPLY_SUCCESS.getMessage(),HttpStatus.OK);	
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
	}
	@DeleteMapping(value="/api/reply/{boardType}/{rid}",produces="application/text; charset=utf8")
	public ResponseEntity<String> deleteReply(@PathVariable("rid") String rid,
			@PathVariable("boardType") BoardType boardType) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException{
		ResponseEntity<String> resEntity=null;
			try {
				Rsvc.deleteReply(Integer.parseInt(aes.decrypt(rid)), boardType);
				resEntity = new ResponseEntity<String>(ERROR_CODE.REPLY_DELETE_SUCCESS.getMessage(),HttpStatus.OK);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return resEntity;
	}

}
