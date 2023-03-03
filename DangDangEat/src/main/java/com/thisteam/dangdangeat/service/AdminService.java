package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.AdminMapper;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.WishlistVO;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper mapper;

	// 회원 목록 조회
	public List<MemberVO> getMemberList() {
		return mapper.selectMemberList();
	}
	
	// 회원 정보 수정
	public int memberUpdate(MemberVO member) {
		return mapper.updateMember(member);
	}

	// 회원 삭제
	public int memberDelete(MemberVO member) {
		return mapper.deleteMember(member);
	}
	
	
	// ============================================== yeram ================================================
	// Wish Top
	public List<WishlistVO> getWishlistTop() {
		return mapper.selectWishlistTop();
	}

	// 한 달 가입 수
	public int getMemberMonthlyJoin() {
		return mapper.selectMemberMonthlyJoin();
	}
	
	// 한 달 매출액
	public int getSalesTotalMonthly() {
		return mapper.selectSalesTotalMonthly();
	}

	// 한 달 주문 건 수
	public int getOrderMonthly() {
		return mapper.selectOrderMonthly();
	}
	
	// 한 달 리뷰 수
	public int getReviewCountMonthly() {
		return mapper.selectReviewCountMonthly();
	}
	
	// ============================================== yeram ================================================
	

}















