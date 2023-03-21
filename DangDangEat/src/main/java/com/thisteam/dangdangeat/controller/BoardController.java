package com.thisteam.dangdangeat.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

import com.mysql.cj.Session;

import com.thisteam.dangdangeat.service.BoardService;
import com.thisteam.dangdangeat.service.ProductService;
import com.thisteam.dangdangeat.vo.NoticeVO;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.ProductVO;
import com.thisteam.dangdangeat.vo.ReviewVO;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	@Autowired
	private ProductService proService;
	
	
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
	
	// 리뷰 작성 권한 확인
	@ResponseBody
	@GetMapping(value = "checkOrderedProduct")
	public void checkOrderedProduct(
			@RequestParam int pd
			, Model model
			, HttpSession session
			, HttpServletResponse response
			) {
		// 세션 아이디 확인
		String id = (String)session.getAttribute("sId");

		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 경우
			model.addAttribute("result", "잘못된 접근입니다.");
		} else { // 세션 아이디 있을 경우
			// 주문 상품 중 리뷰 작성 여부 확인
			ReviewVO review = service.checkOrderProduct(id, pd);
			
		}
		
		model.addAttribute("result", "true"); // 임시
		
	}
	
	// 리뷰 등록 폼
	@GetMapping(value = "ReviewWrite")
	public String ReviewWrite(
			@RequestParam int pro_code
			, Model model
			, HttpSession session
			, HttpServletResponse response
			) {
		
		// 세션 아이디 확인
		String id = (String)session.getAttribute("sId");

		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 경우
			model.addAttribute("msg", "로그인이 필요한 페이지입니다.");
			model.addAttribute("url", "/MemberLoginForm");
			
			return "redirect";
		} else { // 세션 아이디 있을 경우
			// Service 객체의 getProductDetail() 메서드를 호출하여 게시물 상세 정보 조회
			// => 파라미터 : 상품번호  리턴타입 : ProductVO(product)
			ProductVO product = proService.getProductDetail(pro_code);
			
			// Model 객체에 ProductVO 객체 추가
			model.addAttribute("product", product);
			
			return "board/review_write_form";
		}
		
	}
	
	@PostMapping(value = "ReviewWritePro")
	public String reviewWritePro(
			@ModelAttribute ReviewVO review
			, Model model
			, HttpSession session
			, HttpServletResponse response
			) {
		
		System.out.println(review);
		
		String uploadDir = "/resources/upload"; //가상 업로드 위치 지정
		String saveDir = session.getServletContext().getRealPath(uploadDir); //실제 업로드 위치 설정
		System.out.println("실제 업로드 경로 : " + saveDir);
		System.out.println("리뷰 vo :"+review);

		// -------------- java.nio 패키지(Files, Path, Paths) 객체 활용 ---------------------------
		Path path = Paths.get(saveDir); // 실제 업로드 경로 지정

		try {
			Files.createDirectories(path);//실제 지정된 업로드 경로에 디렉토리 생성
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//----------------------------------------

		MultipartFile[] mFiles = review.getFiles();// 파일 객체에 파일 넣기

		String originalFileNames = "";//서버에 등록 될 파일 이름
		String realFileNames = "";//실제 저장될 파일 이름

		// 복수개의 파일에 접근하기 위한 반복문
		for(MultipartFile mFile : mFiles) {
			String originalFileName = mFile.getOriginalFilename();
			if(!originalFileName.equals("")) {
				// 파일명 중복 방지 대책
				String uuid = UUID.randomUUID().toString();
				System.out.println("업로드 될 파일명 : " + uuid + "_" + originalFileName);

				// 파일명을 결합하여 보관할 변수에 하나의 파일 문자열 결합
				originalFileNames += originalFileName + "/";
				realFileNames += uuid + "_" + originalFileName + "/";
			} else {
				// 파일이 존재하지 않을 경우 널스트링으로 대체(뒤에 슬래시 포함)
				originalFileNames += "/";
				realFileNames += "/";
			}
		}

		// productVO 객체에 원본 파일명과 업로드 될 파일명 저장
		//		  image.setImage_main_file(originalFileNames);
		review.setReview_file(originalFileNames); // original 파일이름
		review.setReview_real_file(realFileNames); // 실제 파일 이름
		System.out.println("원본 파일명 : " +review.getReview_file() );
		System.out.println("업로드 될 파일명 : " + review.getReview_real_file());
		
		// 상품 리뷰 등록
		int insertCount = service.registReview(review);
		
		if(insertCount > 0) { // 등록 성공
			model.addAttribute("msg", "리뷰가 성공적으로 등록되었습니다.");
			model.addAttribute("url", "/ProductDetail.pd?pro_code=" + review.getPro_code());
			
			return "redirect";
		} else { // 등록 실패
			model.addAttribute("msg", "리뷰 등록에 실패하였습니다.");
			return "fail_back";
		}
	}
	
	
	
	
	
	
	// ======================== jakyoung 끝 ===================================
	

}













