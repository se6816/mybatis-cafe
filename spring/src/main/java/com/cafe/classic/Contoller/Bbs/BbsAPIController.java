package com.cafe.classic.Contoller.Bbs;

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

import com.cafe.classic.Service.Bbs.BbsService;
import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.MESSAGE_CODE;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.PagingMaker;
import com.cafe.classic.dto.writeRequestDto;
import com.cafe.classic.exception.FormValidException;
import com.cafe.classic.exception.NoAuthException;
import com.cafe.classic.exception.RestNoAuthException;

@RequestMapping("/api")
@RestController
public class BbsAPIController {

	private BbsService bsvc;
	
	@Autowired
	public BbsAPIController(BbsService bsvc) {
		this.bsvc = bsvc;
	}

	/**
	 * 좋아요 클릭
	 * @param boardType 게시판 종류
	 * @param bid 글 번호
	 * @param principal 로그인 정보
	 * @return 200
	 */
	@PostMapping(value="/lovers/{boardType}/{bid}")
	public ResponseEntity<String> addLovers(@PathVariable("boardType") BoardType boardType,
			@PathVariable("bid") int bid,
			@AuthenticationPrincipal PrincipalDetails principal) {
		bsvc.clicklovers(bid, principal.getId(), boardType);
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	/**
	 * 글 수정 api
	 * @param boardType
	 * @param writeRequestDto
	 * @param BindingResult
	 * @return
	 */
	@RequestMapping(value="/board/{boardType}", method=RequestMethod.PUT, produces="application/text; charset=utf8")
	public ResponseEntity<String> modifyArticle(@PathVariable("boardType") BoardType boardType,@Valid @RequestBody writeRequestDto writeRequestDto,
			BindingResult BindingResult,@AuthenticationPrincipal PrincipalDetails principal) {
		if(BindingResult.hasErrors()) {
			FieldError error =BindingResult.getFieldError();
			throw new FormValidException(error.getDefaultMessage());
		}
		BbsVO bvo= bsvc.read(writeRequestDto.getBid(),boardType); 
		if(!principal.getUsername().equals(bvo.getWriter())) {
			throw new RestNoAuthException();
		}
		bsvc.modify(writeRequestDto, boardType);
		return new ResponseEntity<String>(MESSAGE_CODE.UPDATE_ARTICLE_SUCCESS.getMessage(),HttpStatus.OK);
	}	
	/**
	 * 글 작성 api
	 * @param boardType 게시판 종류
	 * @param writeRequestDto  폼 정보
	 * @param BindingResult 폼 검증 결과
	 * @param principal 로그인 정보
	 * @return 200
	 */
	@RequestMapping(value="/board/{boardType}",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public ResponseEntity<String> writePOST(@PathVariable("boardType") BoardType boardType,@Valid @RequestBody writeRequestDto writeRequestDto,
			BindingResult BindingResult,@AuthenticationPrincipal PrincipalDetails principal){
		if(BindingResult.hasErrors()) {
			FieldError error =BindingResult.getFieldError();
			throw new FormValidException(error.getDefaultMessage());
		}
		writeRequestDto.setWriter(principal.getUsername());
		bsvc.write(writeRequestDto,boardType);
		return new ResponseEntity<String>(MESSAGE_CODE.WRITE_ARTICLE_SUCCESS.getMessage(),HttpStatus.OK);

	}
	/**
	 * 글 삭제 api
	 * @param boardType 게시판 종류
	 * @param bid 글 번호
	 * @return
	 */
	@RequestMapping(value="/board/{boardType}/{bid}",method=RequestMethod.DELETE,produces="application/text; charset=utf8")
	public ResponseEntity<String> deleteArticle(@PathVariable("boardType") BoardType boardType,@PathVariable("bid") int bid, 
			@AuthenticationPrincipal PrincipalDetails principal){
		BbsVO bvo= bsvc.read(bid,boardType); 
		if(!principal.getUsername().equals(bvo.getWriter())) {
			throw new RestNoAuthException();
		}
		bsvc.remove(bid,boardType);
		return new ResponseEntity<String>(MESSAGE_CODE.DELETE_ARTICLE_SUCCESS.getMessage(),HttpStatus.OK);	
	}
	

}
