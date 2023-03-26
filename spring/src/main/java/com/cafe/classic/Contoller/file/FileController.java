package com.cafe.classic.Contoller.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.classic.Service.file.UploadService;
import com.cafe.classic.Utils.FileUtils;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.FileVO;
import com.cafe.classic.domain.MESSAGE_CODE;
import com.cafe.classic.exception.FileException;

@RestController
public class FileController {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired 
	Environment env;
	
	@Autowired
	UploadService uSvc;
	
	@Autowired
	FileUtils fileutil;
	
	
	/**
	 * 파일 업로드
	 * @param boardType 게시판 종류
	 * @param multipartFile 파일 정보
	 * @return
	 */
	@RequestMapping(value="/file/{boardType}/upload", method=RequestMethod.POST)
	public HashMap<String,Object> sendFile(@PathVariable("boardType")BoardType boardType,
			@RequestParam("file") MultipartFile multipartFile) {
		int fno;
		HashMap<String,Object> hash= new HashMap<String,Object>();
		fno=fileutil.save(multipartFile,boardType);
		if(fno==0) {
			throw new FileException(MESSAGE_CODE.UPLOAD_FILE_FAIL.getMessage());
		}
		FileVO FVO=uSvc.selectFile(fno, boardType);
		hash.put("file", FVO);
		hash.put("httpstatus", HttpStatus.OK);
		return hash;
	}
	/**
	 * 파일 다운로드
	 * @param boardType 게시판 종류
	 * @param fid 파일 번호
	 * @return 파일 데이터
	 * @throws FileNotFoundException 파일이 존재하지 않는다.
	 */
	@GetMapping(value="/download/{boardType}/{fid}")
	public ResponseEntity<InputStreamResource> Download(@PathVariable("boardType")BoardType boardType,
			@PathVariable("fid") int fid
			) throws FileNotFoundException {
		logger.info("{} 다운로드",fid);
		FileVO fvo= uSvc.searchFile(fid, boardType);
		File file= new File(fvo.getPathName());
		String ContentType= "IMAGE/"+fvo.getContent_Type();
		InputStreamResource resource= new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fvo.getFileName())
				.contentType(MediaType.parseMediaType(ContentType)).contentLength(fvo.getFileSize())
				.body(resource);
	}
	
	
}
