package com.thisteam.dangdangeat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.cj.Session;
import com.thisteam.dangdangeat.service.BoardService;
import com.thisteam.dangdangeat.vo.NoticeVO;
import com.thisteam.dangdangeat.vo.PageInfo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// 공지 리스트
	@GetMapping(value = "/NoticeList")
	public String noticeList(@RequestParam(defaultValue = "") String keyword,
							@RequestParam(defaultValue = "1") int pageNum,
							Model model) {
		
		int listLimit = 10;
		int startRow = (pageNum - 1) * listLimit;
		
		List<NoticeVO> noticeList = service.getNoticeList(keyword,pageNum,startRow,listLimit);
		
		int listCount = service.getNoticeListCount(keyword);
		
		int pageListLimit = 10;
		
		int maxPage = listCount / listLimit
						+ (listCount % listLimit == 0 ? 0 : 1);
		
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		int endPage = startPage + pageListLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(listCount,pageListLimit,maxPage,startPage,endPage);
		
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pageInfo", pageInfo);
		
		
		return "board/notice_list";
	}
	// 공지쓰기 폼
	@GetMapping(value = "/NoticeWriteForm")
	public String noticeWriteForm() {
		
		return "board/notice_write";
	}
	// 공지쓰기
	@PostMapping(value = "/NoticeWritePro")
	public String noticeWritePro(@ModelAttribute NoticeVO notice,
								HttpSession session,
								Model model) {
		
//		System.out.println(notice);
		
		notice.setMember_id((String)session.getAttribute("sId"));
//		System.out.println(sId);
		
		int insertCount = service.insertNotice(notice);
		
		if(insertCount > 0) {
			return "redirect:NoticeList";
		} else {
			model.addAttribute("msg","공지 작성에 실패하였습니다");
			return "fail_back";
		}
		
		
	}
	// 공지 상세정보
	@GetMapping(value = "/NoticeDetail")
	public String noticeDetail(@RequestParam(defaultValue = "0") int notice_code,
								@RequestParam(defaultValue = "1") int pageNum,
								Model model) {
		
//		System.out.println("확인 : " + notice_code);
		NoticeVO detailList = service.getDetailList(notice_code);
//		System.out.println("확인 : " + detailList);
		model.addAttribute("notice",detailList);
		
		return "board/notice_detail";
	}
	// 공지 수정 폼
	@GetMapping(value = "/NoticeModifyForm")
	public String noticeModifyForm(@RequestParam(defaultValue = "0") int notice_code,
									@RequestParam(defaultValue = "1") int pageNum,
									Model model) {
		NoticeVO detailList = service.getDetailList(notice_code);
		model.addAttribute("notice",detailList);
		
		return "board/notice_modify";
	}
	// 공지 수정
	@PostMapping(value = "/NoticeModifyPro")
	public String noticeModifyPro(@ModelAttribute NoticeVO notice,
									@RequestParam(defaultValue = "0") int notice_code,
									@RequestParam(defaultValue = "1") int pageNum,
									HttpSession session,
									Model model) {
//		System.out.println("수정 확인 : " + notice);
		notice.setMember_id((String)session.getAttribute("sId"));
		int updateCount = service.modifyNotice(notice);
		if(updateCount > 0) {
			return "redirect:NoticeDetail?notice_code="+notice_code+"&pageNum="+pageNum;
		} else {
			model.addAttribute("msg","공지 수정에 실패하였습니다");
			return "fail_back";
		}
	}
	// 공지 삭제
	@GetMapping(value = "NoticeDeletePro")
	public String noticeDeletePro(@RequestParam(defaultValue = "0") int notice_code,
									@RequestParam(defaultValue = "1") int pageNum,
									Model model) {
		
		int deleteCount = service.deleteNotice(notice_code);
		if(deleteCount > 0) {
			return "redirect:NoticeList?pageNum="+pageNum;
		} else {
			model.addAttribute("msg","공지 삭제에 실패하였습니다");
			return "fail_back";
		}
	}
}













