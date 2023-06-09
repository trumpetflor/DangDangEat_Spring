package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.MemberMapper;
import com.thisteam.dangdangeat.vo.AuthVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.Member_Mypage_ViewVO;
import com.thisteam.dangdangeat.vo.Review_product_viewVO;

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
//	public int getMemberOrderCount(String id) {
//		return mapper.selectMemberOrderCount(id);
//	}

	 // 회원 쿠폰 수 조회
//	public int getMemberCouponCount(String id) {
//		return mapper.selectMemberCouponCount(id);
//	}

	// 회원 리뷰 수 조회
//	public int getMemberReviewCount(String id) {
//		return mapper.selectMemberReviewCount(id);
//	}

	// 회원 문의 수 조회
//	public int getMemberQnaCount(String id) {
//		return mapper.selectMemberQnaCount(id);
//	}

	// 회원 탈퇴 및 상태 업데이트
	public int memberWithdraw(MemberVO member) {
		return mapper.updateMember(member);
	}

	// 회원 이메일 수정
	public int updateMemberEmail(String id, String email) {
		// 회원 이메일 중복 조회
		String memberEmail = mapper.selectEmailCheck(email);
		
		if(memberEmail == null || memberEmail.equals("")) {
			return mapper.updateMemberEmail(id, email);
		} else {
			return 0;
		}
		
	}

	// 회원 아이디 찾기
	public MemberVO findMemberId(MemberVO member) {
		return mapper.selectMemberId(member);
	}

	// 회원 비밀번호 찾기
	public MemberVO findMemberPass(MemberVO member) {
		return mapper.selectMemberPass(member);
	}

	// 회원 임시 비밀번호 변경
	public int updateMemberPass(MemberVO member) {
		return mapper.updateMemberPass(member);
	}
	
	//----------------------- sangwoo ---------------------// 
	
	// 회원 아이디 중복 체크
	public String idCheck(String id) {
		return mapper.selectidCheck(id);
	}
	
	// 회원 이메일 중복 체크
	public String emailCheck(String email) {
		return mapper.selectEmailCheck(email);
	}

	// 멤버 마이페이지 조회(주문, 쿠폰, 리뷰, 질문 수)
	public Member_Mypage_ViewVO getMemberMypage(String id) {
		return mapper.selectMemberMypageView(id);
	}
	
	// 회원 가입
	public int memberJoinPro(MemberVO member) {
		return mapper.insertMemberJoin(member);
	}
	
	// 메일 인증 정보 등록
	public void insertAuthMail(AuthVO auth) {
		mapper.insertAuthMail(auth);
	}
	
	// 인증 메일 확인
	public int checkAuthMail(AuthVO auth) {
		return mapper.checkAuthMail(auth);
	}
	
	// 회원 메일 인증 상태 업데이트
	public void updateMemberAuth(AuthVO auth) {
		mapper.updateMemberAuth(auth);
	}

	
	//----------------------- sangwoo ---------------------// 
	
	//----------------------- hawon ---------------------//
	//내가 쓴 리뷰 조회 함수
	public List<Review_product_viewVO> getMyReview(String id) {
	
		return mapper.selectMyReview(id);
		
	}
}















