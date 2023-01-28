package com.thisteam.dangdangeat.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.CouponMapper;
import com.thisteam.dangdangeat.vo.CouponVO;

@Service
public class OrderService {
	

//	@Autowired
//	private OrderMapper mapper; 
	
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
	
	//쿠폰 삭제(수정)
	public int deleteCoupon(CouponVO coupon) {
		return cp_mapper.deleteCoupon(coupon);
	}
	
	//쿠폰 전체 개수 조회
	public int getCouponTotalAmount() {
		return cp_mapper.selectCouponCount();
	}

	
	public JSONArray getUsableMemberCoupon(String sId) {
		//1. 생일쿠폰 업데이트 작업
//		cp_mapper.updateBirthCp(sId);
		
		
		//2. 쿠폰 조회 후 JSON 데이터로 변경
		//TODO: 임시 ! 나중에 지울것
		sId = "admin";
		cp_mapper.selectMemberCoupon(sId);
		System.out.println("sId:  "+sId);
		JSONArray jArr = new JSONArray(cp_mapper.selectMemberCoupon(sId));
		System.out.println("jArr :  " + jArr);
		return jArr;
	}

	// 관리자 쿠폰등록페이지에서 중복된 쿠폰코드인지 확인(Ajax)
	public boolean isDuplicateCoupon(String cp_code) {
		boolean isExist = false;
		
		if(cp_mapper.selectCouponCode(cp_code) != null) {// 조회결과가 있으면 중복된 쿠폰
			isExist = true;
		}
		return isExist;
	}
	
	//쿠폰 검색
	public JSONArray selectCouponCodebyUser(String sId,String cp_code) {
		JSONArray jArr = new JSONArray();
		//1. 사용가능한 쿠폰인지 조회
		System.out.println("검색된 쿠폰 코드: "+cp_code);
		String usableCp_code = cp_mapper.selectCouponCodebyUser(cp_code);
		System.out.println("usableCp_code: " +  usableCp_code);//TODO: 왜 null?
		if(usableCp_code != null) {
		//2. 사용가능한 쿠폰일 경우 회원 쿠폰 테이블에 Insert
			int insertCount  = cp_mapper.InsertCouponCodeToMemCp( sId ,  cp_code);
			System.out.println("insertCount : " +  insertCount);
		
			 jArr = new JSONArray(cp_mapper.selectMemberCoupon(sId));
			 
		}
		System.out.println("jArr : " +  jArr);
		return jArr;
	}
	
	
	
	
	

}















