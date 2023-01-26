package com.thisteam.dangdangeat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.CouponMapper;
import com.thisteam.dangdangeat.vo.CouponVO;

@Service
public class OrderService {
	

//	@Autowired
//	private OrderMapper mapper; 두개 가능?
	@Autowired
	CouponMapper cp_mapper;
	
	//쿠폰 등록
	public int insertCoupon(CouponVO coupon) {
		return cp_mapper.insertCoupon(coupon);
	}
	
	//쿠폰 수정
	public int updateCoupon(CouponVO coupon) {
		return cp_mapper.updateCoupon(coupon);
	}
	
	//쿠폰 삭제
	public int deleteCoupon(CouponVO coupon) {
		return cp_mapper.deleteCoupon(coupon);
	}
	
	// 관리자페이지 쿠폰 개수 조회
	public int getCouponTotalAmount() {
		return cp_mapper.selectCouponCount();
	}
	
	
	
	

}















