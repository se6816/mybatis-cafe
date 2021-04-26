package com.test.ex;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.xml.ws.spi.http.HttpHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.Service.BbsServiceImpl;
import com.test.Service.ReplyServiceImpl;
import com.test.Service.UserServiceImpl;
import com.test.Utils.AES256Util;
import com.test.config.MVCconfig;
import com.test.config.MyBatisConfig;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.Log;
import com.test.domain.PageCriteria;
import com.test.domain.ReplyVO;
import com.test.domain.SortType;
import com.test.domain.replyPageCriteria;
import com.test.domain.reportVO;
import com.test.dto.writeRequestDto;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MVCconfig.class,MyBatisConfig.class})
public class BDAOTest {

	
//	@Resource
	private BbsServiceImpl BbsService;
	
	private ReplyServiceImpl replysc;
	
//	@Resource
	private UserServiceImpl Usvc;
	private static Logger logger = LoggerFactory.getLogger(BDAOTest.class);
	
	
	public void insertTest() throws Exception{
		BbsVO bvo= new BbsVO();
		bvo.setSubject("테스트제목입니다.");
		bvo.setContent("테스트 내용입니다.");
		bvo.setWriter("test");
//		BbsService.write(bvo);
	}
	public void readTest() throws Exception{
//		System.out.println(BbsService.read(1).toString());
//		logger.info(BbsService.read(1).toString());
	}
	
	public void updateTest() throws Exception{
		BbsVO bvo= new BbsVO();
		bvo.setBid(2);
		bvo.setSubject("수정 테스트 글이빈다");
		bvo.setContent("수정테스트 내요입니다.");
//		BbsService.modify(bvo);
	}
	
	public void deleteTest() throws Exception{
//		BbsService.remove(3);
	}
	
	public void listTest() throws Exception{
		PageCriteria pageCria = new PageCriteria();
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.queryParam("page",10)
				.queryParam("numPerPage", pageCria.getNumPerPage())
				.queryParam("bcode",1)
				.queryParam("SortType",pageCria.getSortType())
				.build();
		System.out.println(uriComponents.toString());
	}
	
/*	
	public void listPageTest() throws Exception{
		int page=1;
		List<BbsVO> list= BbsService.listPage(page);
		for(BbsVO BbsVO : list) {
			System.out.println(BbsVO.getBid()+  " : "+BbsVO.getContent());
		}
	}*/
	public void listCriteriaTest() throws Exception{
		PageCriteria pri = new PageCriteria();
		BoardType board= BoardType.ARCTURUS;
		pri.setPage(1);
		System.out.println(pri.toString());
		List<BbsVO> list= BbsService.listCriteria(pri,board);
		for(BbsVO BbsVO : list) {
		System.out.println(BbsVO.getBid()+  " : "+BbsVO.getContent()+BbsVO.getReplyCount());
		}
	}
	
	//UriComponentsBuilder을 이용하는 법 : org.springframeork.eb.util에있음
	
	public void uriTest() throws Exception{
		UriComponents uriComponents = 
				UriComponentsBuilder.newInstance()
				.path("{module}/prj/read")
				.queryParam("bid",100)
				.queryParam("numPerPage",20)
				.build()
				.expand("bbs")
				.encode();
		
		System.out.println(uriComponents.toString());
		System.out.println(uriComponents.toUriString());
		System.out.println(uriComponents.toUri());
				
	}
	@Autowired
	private ApplicationContext context;
	public void tt() {
		
		Environment env = context.getEnvironment();  
		
		System.out.println(env.getProperty("spring.datasource.logdriver"));
		System.out.println(env.getProperty("spring.datasource.password"));
	}
	public void ddd() throws Exception {
		BbsVO bvo=BbsService.read(196632, BoardType.ARCTURUS);
		System.out.println(bvo.toString());
	//	PageCriteria cri= new PageCriteria();
	//	cri.setPage(1);
	//	cri.setFindType("SC");
	//	cri.setKeyword("subject");
	//	List<BbsVO> list= BbsService.listCriteria(cri, BoardType.ARCTURUS);
	//	for(BbsVO bvo: list) {
	//		logger.info(bvo.getBid() +": "+bvo.getContent());
	//	}
	//	Calendar cal= Calendar.getInstance();
	//	SimpleDateFormat simple= new SimpleDateFormat("yyyyMMdd");
	//	System.out.println(simple.format(cal.getTime()));
	}
	public void ReplyPosttest() {
		ReplyVO reply= new ReplyVO();
		reply.setBid(196631);
		reply.setWriter("userIm");
		reply.setContent("bb");
		reply.setRgroup(0);
		reply.setRstep(0);
		reply.setSecret(false);
		replysc.write(reply, BoardType.ARCTURUS);
	}
	@Test
	public void List(){
		HashSet<Integer> exist_hash= new HashSet<>();
		List<Integer> list= new ArrayList();
		int[] aa= new int[] {1,2,3};
		HashSet<Integer> hash= new HashSet<>();
		list.add(1);
		list.add(2);
		list.add(3);
		List<Integer> stream=list.stream()
						.filter(i->!hash.contains(i))
						.collect(Collectors.toList());
		
		stream.forEach(System.out::println);
		
		
	}
	public void encrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		Date date= new Date();
		String key= "aes256-test-key!!";
		String key2= "aes222-test-keey!!";
		AES256Util aes;
		aes= new AES256Util(key);
		AES256Util aes2;
		aes2= new AES256Util(key2);
		String str= "11";
		System.out.println(aes.encrypt(str));
		System.out.println(aes.decrypt(aes.encrypt(str)));
		System.out.println(aes2.encrypt(str));
		System.out.println(aes2.decrypt(aes2.encrypt(str)));
	}
	
	public void itrator() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication== null
				 ) {
			System.out.println("aa");
		}
	}
	
	public void report() throws Exception{
		reportVO report= new reportVO();
		com.test.domain.reportType reportType= com.test.domain.reportType.Fword;
		report.setBid("397316");
		report.setId("user");
		report.setReportType(reportType);
		Usvc.report(report);
	}
}
