package com.thisteam.dangdangeat.mapper;

import java.util.List;

import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.WishlistVO;

public interface AdminMapper {

	// 회원 목록 조회
	List<MemberVO> selectMemberList();

	// 회원 정보 수정
	int updateMember(MemberVO member);

	// 회원 삭제
	int deleteMember(MemberVO member);

	
	// ============================================== yeram ================================================
	// Wish Top
	List<WishlistVO> selectWishlistTop();

	// 한 달 가입 수
	int selectMemberMonthlyJoin();

	// 한 달 매출액
	int selectSalesTotalMonthly();

	// 한 달 주문 건 수
	int selectOrderMonthly();

	// 한 달 리뷰 수
	int selectReviewCountMonthly();
	
	// ============================================== yeram ================================================
	
	

}













