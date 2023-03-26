package com.cafe.classic.exception;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.MESSAGE_CODE;
/**
 *  ExceptionAdvice
 * @author user
 *
 */
@ControllerAdvice
public class CommonExceptionAdvice {
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	/**
	 * 파일 관련 오류
	 * @param e
	 * @return
	 */
	@ExceptionHandler(FileException.class)
	@ResponseBody
	private ResponseEntity<String> FileException(FileException e) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(e.getMessage(),headers,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AlreadyDataExistsException.class)
	@ResponseBody
	private ResponseEntity<String> AlreadyDataExistsException(AlreadyDataExistsException e) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(e.getMessage(),headers,HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * form 검증 오류 시 발생
	 * @param e
	 * @return
	 */
	@ExceptionHandler(FormValidException.class)
	@ResponseBody
	private ResponseEntity<String> FormValidException(FormValidException e) {
		logger.info("{} 발생","FormValidException");
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(e.getMessage(),headers,HttpStatus.NOT_FOUND);
	}
	/**
	 * 없는 글의 좋아요를 할 시 발생
	 * @param e
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	private ResponseEntity<String> DataIntegrityViolationException(DataIntegrityViolationException e) {
		logger.info("{} 발생","DataIntegrityViolationException");
		logger.error(e.getMessage());
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(MESSAGE_CODE.PageNotFoundException.getMessage(),headers,HttpStatus.NOT_FOUND);
	}
	/**
	 * 없는 글 조회 시 발생
	 * @param e
	 * @return
	 */
	@ExceptionHandler(PageNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ModelAndView PageNotFoundException(PageNotFoundException e) {
		logger.info("{} 발생","PageNotFoundException");
		ModelAndView mv= new ModelAndView();
		BoardType boardType= BoardType.arcturus;
		mv.setViewName("/err_exception");
		mv.addObject("nav", boardType);
		mv.addObject("exception", MESSAGE_CODE.PageNotFoundException.getMessage());
		return mv;
	}
	/**
	 * 권한 없는 글에 수정, 삭제 시 발생
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RestNoAuthException.class)
	@ResponseBody
	private ResponseEntity<String> RestNoAuthException(RestNoAuthException e) {
		logger.info("{} 발생","RestNoAuthException");
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(MESSAGE_CODE.NoAuthException.getMessage(),headers,HttpStatus.FORBIDDEN);
	}
	/**
	 * 로그인 없이 글 작성 시 발생
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoAuthException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	private ModelAndView NoAuthException(NoAuthException e) {
		logger.info("{} 발생","NoAuthException");
		ModelAndView mv= new ModelAndView();
		BoardType boardType= BoardType.arcturus;
		mv.setViewName("/err_exception");
		mv.addObject("nav", boardType);
		mv.addObject("exception", MESSAGE_CODE.NoAuthException.getMessage());
		return mv;
	}
	/**
	 * URL 오류시 던진다.
	 * @param e
	 * @return
	 */
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ModelAndView errMV(Exception e) {
		logger.error(e.getMessage());
		ModelAndView mv= new ModelAndView();
		BoardType boardType= BoardType.arcturus;
		mv.setViewName("/err_exception");
		mv.addObject("nav", boardType);
		mv.addObject("exception", MESSAGE_CODE.DENIED_ACCESS.getMessage());
		return mv;
	}
	/**
	 * 파일이 없을 때
	 * @param e
	 * @return
	 */
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	private ModelAndView FileNotFoundException(Exception e) {
		ModelAndView mv= new ModelAndView();
		BoardType boardType= BoardType.arcturus;
		System.out.println(e.toString());
		mv.setViewName("/err_exception");
		mv.addObject("boardType", boardType);
		mv.addObject("exception", MESSAGE_CODE.FILE_DOWNLOAD_FAIL.getMessage());
		return mv;
	}
	
	
}
