package com.test.Contoller;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.test.domain.BoardType;
import com.test.domain.ERROR_CODE;

@ControllerAdvice
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	private ModelAndView errMV(Exception e) {
		ModelAndView mv= new ModelAndView();
		BoardType boardType= BoardType.ARCTURUS;
		System.out.println(e.toString());
		mv.setViewName("/err_exception");
		mv.addObject("boardType", boardType);
		mv.addObject("exception", ERROR_CODE.DENIED_ACCESS.getMessage());
		return mv;
	}
	@ExceptionHandler(FileNotFoundException.class)
	private ModelAndView Fileerr(Exception e) {
		ModelAndView mv= new ModelAndView();
		BoardType boardType= BoardType.ARCTURUS;
		System.out.println(e.toString());
		mv.setViewName("/err_exception");
		mv.addObject("boardType", boardType);
		mv.addObject("exception", ERROR_CODE.FILE_DOWNLOAD_FAIL.getMessage());
		return mv;
	}
}
