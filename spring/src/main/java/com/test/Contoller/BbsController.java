package com.test.Contoller;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.Service.BbsService;
import com.test.Service.ReplyService;
import com.test.Utils.AES256Util;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.PageCriteria;
import com.test.domain.PagingMaker;
import com.test.domain.ReplyVO;
import com.test.domain.replyPageCriteria;

@Controller
@RequestMapping("/bbs/*")
public class BbsController {

	@Autowired
	private BbsService bsvc;
	@Autowired
	private ReplyService rsvc;
	@Autowired
	private AES256Util aes;
	
	
	@RequestMapping(value="{boardType}/write",method=RequestMethod.GET)
	public String writeGET(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
   			BbsVO bvo,PageCriteria PageCria,Model model) {
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(PageCria);
		model.addAttribute("pagingMaker", pagingMaker);
		return "/bbs/write";
	}
	@RequestMapping(value= {"{boardType}","{boardType}/main"},method=RequestMethod.GET)
	public String list(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
			   			BbsVO bvo,Model model,PageCriteria PageCria)
			   					throws Exception {
		model.addAttribute("list", bsvc.listCriteria(PageCria,boardType));
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.constructData(PageCria, bsvc.countData(PageCria,boardType));
		model.addAttribute("pagingMaker", pagingMaker);
		if(boardType.name().equalsIgnoreCase("NOTICE")) {
			return "/bbs/notice";
		}
	
		return "/bbs/list";
	}
	// RequestParam은 Servlet의 request.getParam()과  유사하다.
	// Servlet의 request는 HttpServletRequest
	// @RequestParam과 HttpServletRequest의 차이점 : 문자열, 숫자, 날짜 등의 형변환 여부
	
	@RequestMapping(value="{boardType}/{bid}", method=RequestMethod.GET)
	public String read(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
   						PageCriteria pageCria, @PathVariable("bid") int bid, Model model,
   						@AuthenticationPrincipal PrincipalDetails principal
   			   			,RedirectAttributes reAttr
			)throws Exception {
		boolean isLovers=false;
		if(principal!=null) {
			isLovers=bsvc.isClickLovers(bid, principal.getId(), boardType);
		}
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(pageCria);
		model.addAttribute("isLovers",isLovers);
		model.addAttribute("aes",aes);
		model.addAttribute("pagingMaker", pagingMaker);
		BbsVO bvo =bsvc.read(bid,boardType); 
		if(bvo.getSubject()==null) {
			reAttr.addFlashAttribute("err", "글이 존재하지않습니다");
			return "redirect:/bbs/main";
		}
		model.addAttribute("bbsVO", bvo);
		model.addAttribute("replyList", rsvc.listCriteria(bid, new replyPageCriteria(), boardType));
		return "/bbs/read";
	}
	
	@RequestMapping(value="{boardType}/{bid}/modify", method=RequestMethod.GET)
	public String modifyForm(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
   			PageCriteria pageCria, @PathVariable("bid") int bid, Model model
   			,@AuthenticationPrincipal PrincipalDetails principal
   			,RedirectAttributes reAttr)
   					throws Exception {
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(pageCria);
		BbsVO bvo= bsvc.read(bid,boardType);
		if(!principal.getUsername().equals(bvo.getWriter())) {
			reAttr.addFlashAttribute("err", "수정 권한이 없습니다.");
			return "redirect:/bbs/main";
		}
		model.addAttribute("pagingMaker", pagingMaker);
		model.addAttribute("bbsVO", bvo);

		return "/bbs/modify";
	}
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String mainPage(Model model) throws Exception {
		PageCriteria pageCria = new PageCriteria();
		BoardType[] boardTypes = BoardType.values();
		BoardType boardType= BoardType.ARCTURUS;
		Map<Integer,List<BbsVO>> recentArticle = new HashMap<Integer,List<BbsVO>>();
		Map<Integer,List<BbsVO>> hotArticle = new HashMap<Integer,List<BbsVO>>();
		for(int i=0; i<BoardType.values().length;i++) {
			recentArticle.put(i,bsvc.todayShowArticle(pageCria, boardTypes[i]));
			hotArticle.put(i, bsvc.hotShowArticle(pageCria, boardTypes[i]));
		}
		model.addAttribute("recentArticle",recentArticle);
		model.addAttribute("hotArticle", hotArticle);
		model.addAttribute("boardType", boardType);
		model.addAttribute("boardTypes",boardTypes);
		return "/main";
	}	
}
