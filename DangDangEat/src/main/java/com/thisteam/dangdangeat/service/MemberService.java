package com.thisteam.dangdangeat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.MemberMapper;
import com.thisteam.dangdangeat.vo.MemberVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	// 로그인, 패스워드 확인
	public String getPass(String member_id) {
//		System.out.println("Service : " + member_id);
		return mapper.selectPass(member_id);
	}

	// 회원 정보 조회
	public MemberVO getMemberInfo(String id) {
		return mapper.selectMemberInfo(id);
	}

	// 회원 정보 수정
	public int modifyMemberInfo(MemberVO member) {
		return mapper.modifyMemberInfo(member);
	}

	// 회원 주문 수 조회
	public int getMemberOrderCount(String id) {
		return mapper.selectMemberOrderCount(id);
	}

	 // 회원 쿠폰 수 조회
	public int getMemberCouponCount(String id) {
		return mapper.selectMemberCouponCount(id);
	}

	// 회원 리뷰 수 조회
	public int getMemberReviewCount(String id) {
		return mapper.selectMemberReviewCount(id);
	}

	// 회원 문의 수 조회
	public int getMemberQnaCount(String id) {
		return mapper.selectMemberQnaCount(id);
	}

	// 회원 탈퇴 및 상태 업데이트
	public int memberWithdraw(MemberVO member) {
		return mapper.updateMember(member);
	}
	
	// 회원 아이디 중복 체크
	public String idCheck(String id) {
		return mapper.selectidCheck(id);
	}
	
	// 회원 이메일 중복 체크
	public String emailCheck(String email) {
		return mapper.selectEmailCheck(email);
	}
	
	// 회원 가입
	public int memberJoinPro(MemberVO member) {
		return mapper.insertMemberJoin(member);
	}
	
	

}















