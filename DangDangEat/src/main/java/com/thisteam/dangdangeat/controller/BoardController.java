package com.thisteam.dangdangeat.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thisteam.dangdangeat.service.BoardService;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.ReviewVO;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
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













