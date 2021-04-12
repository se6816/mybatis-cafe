package com.test.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.Service.UploadService;
import com.test.domain.BoardType;
import com.test.domain.FileVO;
@Service
public class FileUtils {

	@Autowired
	Environment env;
	
	@Autowired
	private UploadService Usvc;
	
	
	public int report_save(MultipartFile multi) {
		return 1;
	}
	
	
	public int save(MultipartFile multi,BoardType boardType) {
		int UploadFail=0;
		System.out.println("시작");
		String uploadPath=env.getProperty("uploadFile.path");
		String resourcePath=env.getProperty("uploadFile.resourcePath");
		
		String OriginFile=multi.getOriginalFilename();
		String copyFile=OriginFile;
		if(copyFile !=null && !copyFile.equals("")) {
			Calendar cal= Calendar.getInstance();
			String currentTime=new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
			uploadPath= uploadPath.concat(currentTime);
			String ContentType=copyFile.substring(copyFile.lastIndexOf(".")+1);
			copyFile=UUID.randomUUID().toString()+"."+ContentType;
			
			String pathname=uploadPath+"/"+copyFile;
			resourcePath=resourcePath.concat(currentTime+"/"+copyFile);
			File file= new File(pathname);
			if(!file.isDirectory()) {
				file.mkdirs();
			}
			try {
				multi.transferTo(file);
				FileVO FVO = new FileVO();
				FVO.setContent_Type(ContentType);
				FVO.setFileName(copyFile);
				FVO.setOriginal_Filename(OriginFile);
				FVO.setFileSize((int)multi.getSize());
				FVO.setPathName(pathname);
				FVO.setReSource_PathName(resourcePath);
				Usvc.save(FVO, boardType);
				return FVO.getFILEID();
				} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return UploadFail;
	}
}
