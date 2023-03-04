package com.thisteam.dangdangeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.thisteam.dangdangeat.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// ======================== jakyoung 시작 ===================================
	// 상품 상세페이지 리뷰 표출
	public void review(
			@RequestParam(defaultValue = "1") int pd
			
			) {
		
	}
	
	// ======================== jakyoung 끝 ===================================
	
}













