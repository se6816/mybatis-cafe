package com.cafe.classic.Contoller.Bbs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe.classic.Service.Bbs.BbsService;
import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.NoticeType;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.PagingMaker;
import com.cafe.classic.domain.UserCategory;
import com.cafe.classic.exception.NoAuthException;
import com.cafe.classic.exception.PageNotFoundException;

@Controller
public class BbsController {
	private final Logger logger= LoggerFactory.getLogger(BbsController.class);
	@Autowired
	private BbsService bsvc;
	
	@ModelAttribute("nav")
	public Category getCategory() {
		return BoardType.arcturus;
	}
	/**
	 * 글 작성 페이지로 이동한다.
	 * @param boardType 게시판 종류
	 * @param PageCria 페이지 정보
	 * @param model
	 * @return 글 작성 페이지
	 */
	@RequestMapping(value="/bbs/{boardType}/write",method=RequestMethod.GET)
	public String writeGET(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
							PageCriteria PageCria,Model model) {
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(PageCria);
		model.addAttribute("pagingMaker", pagingMaker);
		return "/bbs/write";
	}
	
	/**
	 * 게시판 페이지 메인	
	 * @param boardType 게시판 종류
	 * @param model 모델
	 * @param PageCria 페이지 정보
	 * @return 게시판 페이지
	 */
	@RequestMapping(value= {"/bbs/{boardType}","bbs/{boardType}/main"},method=RequestMethod.GET)
	public String list(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
			   			Model model,PageCriteria PageCria){
		model.addAttribute("list", bsvc.listCriteria(PageCria,boardType));
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.constructData(PageCria, bsvc.countData(PageCria,boardType));
		model.addAttribute("pagingMaker", pagingMaker);
		return "/bbs/list";
	}
	
	/**
	 * 게시판 글 상세 페이지 
	 * @param boardType 게시판 종류
	 * @param pageCria 페이지 정보
	 * @param bid 글 번호 
	 * @param model 모델
	 * @param principal 로그인 정보
	 * @return 게시판 글 view
	 */
	@RequestMapping(value="/bbs/{boardType}/{bid}", method=RequestMethod.GET)
	public String read(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
   						PageCriteria pageCria, @PathVariable("bid") int bid, Model model,
   						@AuthenticationPrincipal PrincipalDetails principal){
		boolean isLovers=false;
		logger.info("글 읽기");
		if(principal!=null) {
			isLovers=bsvc.isClickLovers(bid, principal.getId(), boardType);
		}
		
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(pageCria);
		model.addAttribute("isLovers",isLovers);
		model.addAttribute("pagingMaker", pagingMaker);
		BbsVO bvo =bsvc.read(bid,boardType);
		model.addAttribute("bbsVO", bvo);
		return "/bbs/read";
	}
	/**
	 * 글 수정 페이지
	 * @param boardType 게시판 종류
	 * @param pageCria 페이지 정보
	 * @param bid 글 번호
	 * @param model 모델
	 * @param principal 로그인 정보
	 * @return
	 */
	@RequestMapping(value="/bbs/{boardType}/{bid}/modify", method=RequestMethod.GET)
	public String modifyForm(@ModelAttribute("boardType")@PathVariable("boardType") BoardType boardType, 
   			PageCriteria pageCria, @PathVariable("bid") int bid, Model model
   			,@AuthenticationPrincipal PrincipalDetails principal){
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(pageCria);
		BbsVO bvo= bsvc.read(bid,boardType); 
		if(!principal.getUsername().equals(bvo.getWriter())) {
			throw new NoAuthException();
		}
		model.addAttribute("pagingMaker", pagingMaker);
		model.addAttribute("bbsVO", bvo);

		return "/bbs/modify";
	}
	/**
	 * 유저 정보 페이지
	 * @param model
	 * @param id 아이디
	 * @param userCategory 유저 카테고리
	 * @param page 페이지 정보
	 * @return 유저 정보 view
	 */
	@GetMapping("/user/{id}/{userCategory}")
	public String userInfo(Model model,@ModelAttribute("id") @PathVariable("id") String id
			               ,@ModelAttribute("userCategory") @PathVariable("userCategory") UserCategory userCategory
			               ,PageCriteria page){
		PagingMaker pagingMaker= new PagingMaker();
		if(userCategory.name().equals("bbs")) {
			pagingMaker.constructData(page, bsvc.userCountData(id));
			model.addAttribute("bbsList", bsvc.userListCriteria(page, id));
		}
		else {
			pagingMaker.constructData(page, bsvc.userReplyCountData(id));
			model.addAttribute("replyList", bsvc.userReplyListCriteria(page, id));
		}
		model.addAttribute("pagingMaker", pagingMaker);
		
		return "user/userInfo";
	}
	
	/**
	 * 공지사항 페이지 글 읽기
	 * @param pageCria 페이지 정보
	 * @param bid  글 번호
	 * @param model
	 * @return 공지사항 글 view
	 */
	@RequestMapping(value="/news/{bid}", method=RequestMethod.GET)
	public String read(PageCriteria pageCria, @PathVariable("bid") int bid, Model model){
		logger.info("글 읽기");
		NoticeType noticeType= NoticeType.notice;
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.setPageCria(pageCria);
		model.addAttribute("pagingMaker", pagingMaker);
		BbsVO bvo =bsvc.read(bid,noticeType); 
		model.addAttribute("bbsVO", bvo);
		model.addAttribute("noticeType", noticeType);
		return "/bbs/notice_read";
	}
	
	/**
	 * 공지사항 페이지
	 * @param model 모델	 
	 * @param PageCria 페이징
	 * @return 공지사항 리스트 view
	 */
	@RequestMapping(value= {"/news","/news/main"}, method=RequestMethod.GET)
	public String news(Model model, PageCriteria PageCria) {
		NoticeType noticeType= NoticeType.notice;
		model.addAttribute("list", bsvc.listCriteria(PageCria,noticeType));
		PagingMaker pagingMaker= new PagingMaker();
		pagingMaker.constructData(PageCria, bsvc.countData(PageCria,noticeType));
		model.addAttribute("pagingMaker", pagingMaker);
		model.addAttribute("noticeType", noticeType);
		return "/bbs/notice";
	}	
	
	/**
	 * 메인 페이지
	 * @param model
	 * @return 메인 페이지 view
	 * @throws Exception
	 */
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String mainPage(Model model){
		PageCriteria pageCria = new PageCriteria();
		BoardType boardType= BoardType.arcturus;
		model.addAttribute("bestArcturus", bsvc.hotShowArticle(pageCria, BoardType.arcturus));
		model.addAttribute("bestStarcraft", bsvc.hotShowArticle(pageCria, BoardType.starcraft));
		model.addAttribute("boardType", boardType);
		return "main_page";
	}	
}
