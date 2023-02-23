package com.thisteam.dangdangeat.mapper;

import com.thisteam.dangdangeat.vo.AuthVO;
import com.thisteam.dangdangeat.vo.MemberVO;

public interface MemberMapper {

	// 로그인, 패스워드 확인
	String selectPass(String id);

	// 회원 정보 조회
	MemberVO selectMemberInfo(String id);

	// 회원 정보 수정
	int modifyMemberInfo(MemberVO member);
	
	// 회원 주문 수 조회
	int selectMemberOrderCount(String id);

	// 회원 쿠폰 수 조회
	int selectMemberCouponCount(String id);

	// 회원 리뷰 수 조회
	int selectMemberReviewCount(String id);

	// 회원 문의 수 조회
	int selectMemberQnaCount(String id);

	// 회원 탈퇴 및 상태 업데이트
	int updateMember(MemberVO member);
	
	//----------------------- sangwoo ---------------------// 
	
	// 회원 아이디 중복 체크
	String selectidCheck(String id);
	
	// 회원 이메일 중복 체크
	String selectEmailCheck(String email);
	
	// 회원 가입
	int insertMemberJoin(MemberVO member);
	
	// 인증 메일 정보 등록
	void insertAuthMail(AuthVO auth);
	
	// 메일 인증 확인
	int checkAuthMail(AuthVO auth);
	
	// 회원 메일 인증 상태 업데이트
	void updateMemberAuth(AuthVO auth);

	//----------------------- sangwoo ---------------------// 

}













