package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.BoardMapper;

import com.thisteam.dangdangeat.vo.NoticeVO;
import com.thisteam.dangdangeat.vo.QnaVO;
import com.thisteam.dangdangeat.vo.ReviewVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
  
	// ======================== sangwoo 시작 ===================================
	public List<NoticeVO> getNoticeList(String keyword, int pageNum, int startRow, int listLimit) {
		return mapper.getNoticeList(keyword,pageNum,startRow,listLimit);
	}

	public int getNoticeListCount(String keyword) {
		return mapper.getNoticeListCount(keyword);
	}

	public int insertNotice(NoticeVO notice) {
		return mapper.insertNotice(notice);
	}

	public NoticeVO getDetailList(int notice_code) {
		return mapper.getDetailList(notice_code);
	}

	public int modifyNotice(NoticeVO notice) {
		return mapper.modifyNotice(notice);
	}

	public int deleteNotice(int notice_code) {
		return mapper.deleteNotice(notice_code);
	}
    
	public List<QnaVO> getQnaList(String keyword, int pageNum, int startRow, int listLimit) {
		return mapper.getQnaList(keyword,pageNum,startRow,listLimit);
	}
	
	public int getQnaListCount(String keyword) {
		return mapper.getQnaListCount(keyword);
	}
	
	public QnaVO getQnaDetailList(int qna_code) {
		return mapper.getQnaDetaiList(qna_code);
	}
	
	public int insertQna(QnaVO qna) {
		return mapper.insertQna(qna);
	}
	// ======================== sangwoo 끝 ===================================
  
  
	// ======================== jakyoung 시작 ===================================
	
	// 해당 상품 리뷰 목록 가져오기
	public List<ReviewVO> getReviewList(int pro_code, String keyword, int startRow, int listLimit) {
		return mapper.selectReviewList(pro_code, keyword, startRow, listLimit);
	}

	// 해당 상품 리뷰 개수
	public int getReviewListCount(int pro_code, String keyword) {
		return mapper.selectReviewListCount(pro_code, keyword);
	}

	// 주문 상품 중 리뷰 작성 여부 확인
	public ReviewVO checkOrderProduct(String member_id, int pro_code) {
		return mapper.selectProductReview(member_id, pro_code);
	}

	// 상품 리뷰 등록
	public int registReview(ReviewVO review) {
		return mapper.insertReview(review);
	}

	
	// ======================== jakyoung 끝 ===================================
	

}















