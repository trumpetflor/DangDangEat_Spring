package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.AdminMapper;

import com.thisteam.dangdangeat.vo.AdminOrderListVO;
import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.Coupon_viewVO;
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


	// ======================================== hawon =============================================
	//쿠폰 수정
	public int updateCoupon(Coupon_viewVO coupon) {
		// TODO Auto-generated method stub
		return mapper.updateCoupon(coupon);
	}
	//쿠폰 수정 후 수정된 쿠폰 객체
	public Coupon_viewVO getCoupon(String cp_code) {
		// TODO Auto-generated method stub
		return mapper.selectCoupon(cp_code);
	}

	//쿠폰 등록
	public int registerCoupon(CouponVO coupon) {
		// TODO Auto-generated method stub
		return mapper.insertCoupon(coupon);
	}
	
	//자동 생일쿠폰 발행을 위해 member_coupon테이블에 insert작업
	public int registerBirthCouponToMc(String cp_code) {
		return mapper.insertBirthCouponToMc(cp_code);
	}

	//관리자 쿠폰등록페이지_쿠폰코드 중복인지 확인
	public boolean isExistCode(String coupon_code) {
		String cp_code = mapper.isExistCode(coupon_code);
		if(cp_code != null) {
			return true;
		}else {
			return false;
		}
		
	}
	//쿠폰삭제
	public int deleteCoupon(String cp_code) {

		return mapper.deleteCoupon(cp_code);
	}
	//회원가입시에 쿠폰 등록
	public int autoCouponInsertForNewMem(String member_id) {
		
		return mapper.insertAutoCouponForNewMem(member_id);
	}


//============================ admin/orderList 미주 ===========================================

	// 관리자 - 주문 관리 페이지
	public List<AdminOrderListVO> getOrderList() {
		return mapper.selectOrderList();
	}

//============================ admin/orderList 미주 ===========================================	

	

}















