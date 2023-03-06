package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.BoardMapper;
import com.thisteam.dangdangeat.vo.ReviewVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;

	// ======================== jakyoung 시작 ===================================
	
	// 해당 상품 리뷰 목록 가져오기
	public List<ReviewVO> getReviewList(int pro_code, String keyword, int startRow, int listLimit) {
		return mapper.selectReviewList(pro_code, keyword, startRow, listLimit);
	}

	// 해당 상품 리뷰 개수
	public int getReviewListCount(int pro_code, String keyword) {
		return mapper.selectReviewListCount(pro_code, keyword);
	}
	
	// ======================== jakyoung 끝 ===================================
	

}















