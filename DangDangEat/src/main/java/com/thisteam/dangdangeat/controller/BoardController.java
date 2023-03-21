package com.thisteam.dangdangeat.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mysql.cj.Session;

import com.thisteam.dangdangeat.service.BoardService;
import com.thisteam.dangdangeat.vo.NoticeVO;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.QnaVO;
import com.thisteam.dangdangeat.vo.ReviewVO;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// ======================== sangwoo 시작 ===================================
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
	
	// qna 리스트
	@GetMapping(value = "QnaList")
	public String qnaList(@RequestParam(defaultValue = "") String keyword,
							@RequestParam(defaultValue = "1") int pageNum,
							Model model) {
		
		int listLimit = 10;
		int startRow = (pageNum - 1) * listLimit;
		
		List<QnaVO> qnaList = service.getQnaList(keyword,pageNum,startRow,listLimit);
		
		int listCount = service.getQnaListCount(keyword);
		
		int pageListLimit = 10;
		
		int maxPage = listCount / listLimit
						+ (listCount % listLimit == 0 ? 0 : 1);
		
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		int endPage = startPage + pageListLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(listCount,pageListLimit,maxPage,startPage,endPage);
		
		
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("pageInfo", pageInfo);
		
		return "board/qna_list";
	}
	
	// qna 글 상세보기 조회
	@GetMapping(value = "QnaDetail")
	public String qnaDetail(@RequestParam(defaultValue = "0") int qna_code,
							@RequestParam(defaultValue = "1") int pageNum,
							Model model) {
		
		QnaVO detailList = service.getQnaDetailList(qna_code);
		model.addAttribute("qna",detailList);
		
		return "board/qna_detail";
	}
	
	// qna 글쓰기 폼
	@GetMapping(value = "QnaWriteForm")
	public String qnaWriteForm() {
		
		return "board/qna_write";
	}
	
	// qna 글쓰기 프로
	@PostMapping(value = "QnaWritePro")
	public String qnaWritePro(@ModelAttribute QnaVO qna,
								Model model,
								HttpSession session,
								@RequestParam("qna_image") MultipartFile file_qna,
								MultipartRequest request) {
		
		System.out.println(qna);
		//파일업로드 시작
		String uploadPath = "/resources/upload"; 
		String realPath = session.getServletContext().getRealPath(uploadPath);
		//실제 업로드 경로 
		System.out.println("실제 업로드 경로 : " + realPath);
		 
		String qna_image = file_qna.getOriginalFilename().toString();
		qna.setQna_file(qna_image);
		qna.setQna_real_file(qna_image);
		
		
		// 파일 생성
		File img_f = new File(realPath, qna_image); 
		try {
			file_qna.transferTo(img_f);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		// 파일 경로가 존재하지 않을 경우 파일 경로 생성
		if(!img_f.exists()) {
			img_f.mkdirs();
		}
		
		int qnaInsertCount = service.insertQna(qna);
		if(qnaInsertCount > 0) {
			return "redirect:QnaList";
		} else {
			model.addAttribute("msg","상품 문의 작성에 실패하였습니다");
			return "fail_back";
		}
	}
	// ======================== sangwoo 끝 ===================================
  
  
	// ======================== jakyoung 시작 ===================================
	// 상품 상세페이지 리뷰 표출
	@ResponseBody
	@GetMapping(value = "ReviewList")
	public void review(
			@RequestParam(defaultValue = "1") int pd,
			@RequestParam(defaultValue = "1") int pageNum, // 현재 페이지 번호
			@RequestParam(defaultValue = "") String keyword, // 검색어
			Model model,
			HttpSession session,
			HttpServletResponse response
			) {
		
		// 세션 아이디 존재 시 Model 객체에 저장 (버튼 표시 용)
		if(session.getAttribute("sId") != null && !session.getAttribute("sId").equals("")) {
			model.addAttribute("sId", session.getAttribute("sId"));
			System.out.println("sId : " + session.getAttribute("sId"));
		}
		
		// 페이징 처리를 위한 변수 선언
		int listLimit = 5; // 한 페이지에서 표시할 게시물 목록을 7개로 제한
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산

		// 거래처 목록 가져오기
		List<ReviewVO> reviewList = service.getReviewList(pd, keyword, startRow, listLimit);

		// 페이징 처리 
		//1. 검색어에 해당하는 거래처 정보(ClientVO)의 갯수 계산
		int listCount = service.getReviewListCount(pd, keyword);

		// 2. 한 페이지에서 표시할 페이지 숫자의 갯수 설정
		int pageListLimit = 10; // 한 페이지에서 표시할 페이지 수를 10개로 제한
		// 3. 전체 페이지 목록 수 계산
		int maxPage = listCount / listLimit 
				+ (listCount % listLimit == 0 ? 0 : 1); 

		// 4. 시작 페이지 번호 계산
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;

		// 5. 끝 페이지 번호 계산
		int endPage = startPage + pageListLimit - 1;

		// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
		//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
		if(endPage > maxPage) {
			endPage = maxPage;
		}

		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);		

		System.out.println(pageInfo);

		// 페이징 정보 객체(pageInfo)를 Model 객체에 저장
		//		model.addAttribute("pageInfo", pageInfo);

		// JSON 형식 변환
		JSONArray jsonArray = new JSONArray();

		for(ReviewVO review : reviewList) {
			review.setReview_content(review.getReview_content().replace("\r\n", "<br>")); // 출력 시 줄바꿈 처리
			System.out.println(review);
			JSONObject jsonObject = new JSONObject(review);
			System.out.println("리뷰 코드 : " + jsonObject.get("review_code"));

			jsonArray.put(jsonObject); // jsonArray 객체에 ClientVO 객체 배열(clientList) 넣기
		}

		JSONObject jsonObjectPage = new JSONObject(pageInfo);
		System.out.println("시작 페이지 : " + jsonObjectPage.get("startPage"));
		jsonArray.put(jsonObjectPage); // jsonArray 객체 마지막에 PageInfo 객체(pageInfo) 넣기

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonArray);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// ======================== jakyoung 끝 ===================================
	

}













