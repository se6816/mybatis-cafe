package com.test.Contoller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

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

import com.test.Service.UploadService;
import com.test.Utils.FileUtils;
import com.test.domain.BoardType;
import com.test.domain.ERROR_CODE;
import com.test.domain.FileVO;

@RestController

public class FileController {
	@Autowired 
	Environment env;
	
	@Autowired
	UploadService uSvc;
	
	@Autowired
	FileUtils fileutil;
	
	
	
	@RequestMapping(value="/file/{boardType}/upload", method=RequestMethod.POST)
	public HashMap<String,Object> sendFile(@PathVariable("boardType") BoardType boardType,
			@RequestParam("file") MultipartFile multipartFile) {
		int fno;
		HashMap<String,Object> hash= new HashMap<String,Object>();
		
		fno=fileutil.save(multipartFile,boardType);
		if(fno==0) {
			hash.put("message",ERROR_CODE.UPLOAD_FILE_FAIL.getMessage());
			hash.put("httpstatus", HttpStatus.BAD_REQUEST);
			return hash;
		}
		FileVO FVO=uSvc.selectFile(fno, boardType);
		hash.put("file", FVO);
		hash.put("httpstatus", HttpStatus.OK);
		return hash;
	}
	
	@GetMapping(value="/download/{boardType}/{fid}")
	public ResponseEntity<InputStreamResource> Download(@PathVariable("boardType") BoardType boardType,
			@PathVariable("fid") int fid
			) throws FileNotFoundException {
		System.out.println("다운로드 실행");
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
