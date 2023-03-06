package com.thisteam.dangdangeat.mapper;

import java.util.List;

import com.thisteam.dangdangeat.vo.AdminOrderListVO;
import com.thisteam.dangdangeat.vo.MemberVO;

public interface AdminMapper {

	// 회원 목록 조회
	List<MemberVO> selectMemberList();

	// 회원 정보 수정
	int updateMember(MemberVO member);

	// 회원 삭제
	int deleteMember(MemberVO member);
//============================ admin/orderList 미주 ===========================================
	
	// 관리자 - 주문 관리 페이지
	List<AdminOrderListVO> selectOrderList();
	
//============================ admin/orderList 미주 ===========================================

}













