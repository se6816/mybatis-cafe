package com.test.Contoller;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.test.domain.ERROR_CODE;

@ControllerAdvice
public class CommonExceptionAdvice {
	
//	@ExceptionHandler(Exception.class)
//	public String common(Exception e) {
//		System.out.println(e.toString());
//		return "err_exception";
//	}
	@ExceptionHandler(Exception.class)
	private ModelAndView errMV(Exception e) {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/err_exception");
		mv.addObject("exception", e);
		return mv;
	}
	@ExceptionHandler(FileNotFoundException.class)
	private ModelAndView Fileerr(Exception e) {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/err_exception");
		mv.addObject("exception", ERROR_CODE.FILE_DOWNLOAD_FAIL);
		return mv;
	}
}
