package com.thisteam.dangdangeat.mapper;

import com.thisteam.dangdangeat.vo.MemberVO;

public interface MemberMapper {

	// 로그인, 패스워드 확인
	String selectPass(String id);

	// 회원 정보 조회
	MemberVO selectMemberInfo(String id);

	// 회원 정보 수정
	int modifyMemberInfo(MemberVO member);
	
	

}













