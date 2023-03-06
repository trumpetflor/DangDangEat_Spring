package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.AdminMapper;
import com.thisteam.dangdangeat.vo.AdminOrderListVO;
import com.thisteam.dangdangeat.vo.MemberVO;

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

//============================ admin/orderList 미주 ===========================================
	
	// 관리자 - 주문 관리 페이지
	public List<AdminOrderListVO> getOrderList() {
		return mapper.selectOrderList();
	}
	
//============================ admin/orderList 미주 ===========================================	
	

}















