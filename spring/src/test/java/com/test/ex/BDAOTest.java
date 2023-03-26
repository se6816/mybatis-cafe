package com.test.ex;

import static org.junit.Assert.assertEquals;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cafe.classic.Service.Bbs.BbsServiceImpl;
import com.cafe.classic.Service.reply.ReplyService;
import com.cafe.classic.Service.reply.ReplyServiceImpl;
import com.cafe.classic.Service.user.UserService;
import com.cafe.classic.Service.user.UserServiceImpl;
import com.cafe.classic.config.MVCconfig;
import com.cafe.classic.config.MyBatisConfig;
import com.cafe.classic.config.RedisConfig;
import com.cafe.classic.domain.BbsListVO;
import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Page;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.ReplyVO;
import com.cafe.classic.domain.SortType;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.replyPageCriteria;
import com.cafe.classic.domain.ReportVO;
import com.cafe.classic.dto.writeRequestDto;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MVCconfig.class,MyBatisConfig.class})
public class BDAOTest implements ApplicationContextAware{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private com.cafe.classic.Service.Bbs.BbsService BbsService;
	@Autowired
	private ReplyService replysc;
	
	@Autowired
	private UserService Usvc;
	private static ApplicationContext ctx;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx=applicationContext;
		
	}
    @Test
	public void contextLoads() throws Exception {
    	ApplicationContext ct=getApplicationContext();
	      if (ct != null) {
	          String[] beans = ctx.getBeanDefinitionNames();
	          System.out.println("시작");
	           for (String bean : beans) {
	             System.out.print("1");
	             System.out.println("bean : " + bean);
	            }
	        }
	      else {
	    	  System.out.println("null");
	      }
	    }
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
		BoardType board= BoardType.arcturus;
		pri.setPage(1);
		System.out.println(pri.toString());
		java.util.List<BbsListVO> list= BbsService.listCriteria(pri,board);
	
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
	

	
	public void report() throws Exception{
		ReportVO report= new ReportVO();
		com.cafe.classic.domain.reportType reportType= com.cafe.classic.domain.reportType.Fword;
		report.setBid("397316");
		report.setId("user");
		report.setReportType(reportType);
		Usvc.report(report);
	}
	public void logTest() {
		System.out.println("log테스트");
		logger.debug("debug");
		logger.info("info");

	}
	
	public void ReplyPosttest() throws Exception {
		ReplyVO reply= new ReplyVO();
		reply.setBid(196631);
		reply.setWriter("userIm");
		reply.setContent("bb");
		reply.setRgroup(0);
		reply.setRstep(0);
		reply.setSecret(false);
		replysc.write(reply, BoardType.arcturus);
	}


}
