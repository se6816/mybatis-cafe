package com.test.Contoller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.Service.BbsService;
import com.test.Utils.AES256Util;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.ERROR_CODE;
import com.test.domain.PageCriteria;
import com.test.domain.PagingMaker;
import com.test.dto.writeRequestDto;

@RequestMapping("/api")
@RestController
public class BbsAPIController {
	
	@Autowired
	private BbsService bsvc;
	
	@PostMapping(value="/lovers/{boardType}/{bid}")
	public ResponseEntity<Integer> loversCheck(@PathVariable("boardType") BoardType boardType,
			@PathVariable("bid") int bid,
			@AuthenticationPrincipal PrincipalDetails principal) {
		ResponseEntity<Integer> resEntity = null;
		int isLoversCheck=bsvc.clicklovers(bid, principal.getId(), boardType);
		System.out.println(isLoversCheck);
		resEntity = new ResponseEntity<Integer>(isLoversCheck,HttpStatus.OK);
		return resEntity;
	}
	
	
	@RequestMapping(value="/board/{boardType}", method=RequestMethod.PUT, produces="application/text; charset=utf8")
	public ResponseEntity<String> modifyArticle(@PathVariable("boardType") BoardType boardType,@Valid @RequestBody writeRequestDto writeRequestDto,
			BindingResult BindingResult) throws Exception {
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			bsvc.modify(writeRequestDto, boardType);
			resEntity=new ResponseEntity<String>(ERROR_CODE.UPDATE_ARTICLE_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return resEntity;
		
	
	}	
	
	@RequestMapping(value="/board/{boardType}",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public ResponseEntity<String> writePOST(@PathVariable("boardType") BoardType boardType,@Valid @RequestBody writeRequestDto writeRequestDto,
			BindingResult BindingResult,@AuthenticationPrincipal PrincipalDetails principal) throws Exception{
		ResponseEntity<String> resEntity=null;
		if(!BindingResult.hasErrors()) {
			writeRequestDto.setWriter(principal.getUsername());
			bsvc.write(writeRequestDto,boardType);
			resEntity = new ResponseEntity<String>(ERROR_CODE.WRITE_ARTICLE_SUCCESS.getMessage(),HttpStatus.OK);
		}
		else {
			
			FieldError error =BindingResult.getFieldError();
			resEntity = new ResponseEntity<String>(error.getDefaultMessage(),HttpStatus.BAD_REQUEST);
		}
		return resEntity;
	}
	@RequestMapping(value="/board/{boardType}/{bid}",method=RequestMethod.DELETE,produces="application/text; charset=utf8")
	public ResponseEntity<String> deleteArticle(@PathVariable("boardType") BoardType boardType,@PathVariable("bid") int bid){
		ResponseEntity<String> resEntity = null;
		try {
			bsvc.remove(bid,boardType);
			resEntity = new ResponseEntity<String>(ERROR_CODE.DELETE_ARTICLE_SUCCESS.getMessage(),HttpStatus.OK);	
		} catch (Exception e) {
			e.printStackTrace();
			resEntity = new ResponseEntity<String>(ERROR_CODE.DELETE_ARCICLE_FAIL.getMessage(),HttpStatus.BAD_REQUEST);
		}

		return resEntity;
	}
	

}
