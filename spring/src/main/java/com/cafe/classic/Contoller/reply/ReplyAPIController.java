package com.cafe.classic.Contoller.reply;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

import com.cafe.classic.Service.Bbs.BbsService;
import com.cafe.classic.Service.reply.ReplyService;
import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.MESSAGE_CODE;
import com.cafe.classic.domain.ReplyVO;
import com.cafe.classic.domain.replyPageCriteria;
import com.cafe.classic.exception.FormValidException;
import com.cafe.classic.exception.RestNoAuthException;


@RestController
public class ReplyAPIController {
	
	
	private final ReplyService Rsvc;
	
	@Autowired
	public ReplyAPIController(ReplyService rsvc) {
		this.Rsvc = rsvc;
	}
	
	/**
	 * 댓글 리스트 출력
	 * @param boardType 게시판 종류
	 * @param page 페이지
	 * @param bid 글 번호
	 * @return 댓글 리스트
	 */
	@GetMapping(value="/reply/{boardType}/{bid}",produces="application/json; charset=utf8")
	public HashMap<String,Object> replyGet(@PathVariable("boardType") BoardType boardType, @RequestParam int page, 
		@PathVariable("bid") int bid, @AuthenticationPrincipal PrincipalDetails principal){
		HashMap<String, Object> map=new HashMap<String,Object>();
		replyPageCriteria replyPageCriteria= new replyPageCriteria();
		replyPageCriteria.setPage(page);
		String username=Optional.of(principal.getUsername()).orElse("unknown");
		List<ReplyVO> list= Rsvc.listCriteria(bid, replyPageCriteria, boardType, username);
		
		map.put("list", list);
		map.put("status",HttpStatus.OK);
		return map;
	}

	/**
	 * 댓글 등록
	 * @param boardType 게시판 종류
	 * @param reply 댓글 정보
	 * @param BindingResult  폼 검증 결과
	 * @param principal 로그인 정보
	 * @return
	 */
	@PostMapping(value="/api/reply/{boardType}",produces="application/text; charset=utf8")
	public ResponseEntity<String> replyPost(@PathVariable("boardType") BoardType boardType,@Valid @RequestBody ReplyVO reply,
			BindingResult BindingResult, 
			@AuthenticationPrincipal PrincipalDetails principal){
		if(BindingResult.hasErrors()) {
			FieldError error =BindingResult.getFieldError();
			throw new FormValidException(error.getDefaultMessage());
		}
		reply.setWriter(principal.getUsername());
		Rsvc.write(reply, boardType);
		return new ResponseEntity<String>(MESSAGE_CODE.REPLY_SUCCESS.getMessage(),HttpStatus.OK);	
	}
	/**
	 * 댓글 삭제
	 * @param rid 댓글 번호
	 * @param boardType 게시판 종류
	 * @param principal 로그인 정보
	 * @return 
	 */
	@DeleteMapping(value="/api/reply/{boardType}/{rid}",produces="application/text; charset=utf8")
	public ResponseEntity<String> deleteReply(@PathVariable("rid") String rid,
			@PathVariable("boardType") BoardType boardType,
			@AuthenticationPrincipal PrincipalDetails principal){
		if(!Rsvc.read(rid, boardType).isAuthentication(principal.getUsername())){
			throw new RestNoAuthException();
		}
		Rsvc.deleteReply(rid, boardType);
		return new ResponseEntity<String>(MESSAGE_CODE.REPLY_DELETE_SUCCESS.getMessage(),HttpStatus.OK);		   					
	}

}
