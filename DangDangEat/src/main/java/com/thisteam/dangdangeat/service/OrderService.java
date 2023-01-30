package com.thisteam.dangdangeat.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.CouponMapper;
import com.thisteam.dangdangeat.mapper.OrderMapper;
import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.CartProductVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.OrderProductVO;
import com.thisteam.dangdangeat.vo.OrdersBeanVO;

@Service
public class OrderService {
	

  @Autowired
  private OrderMapper mapper;  // mijoo
	private CouponMapper cp_mapper; // hawon
  
	
  // ==================================== Hawon ====================================
  
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
	
	
  
  
  
  
  // ==================================== Mijoo ====================================

	//---------------------주문/결제 작업------------------------
	
	// 1-(1). 주문서에 회원의 정보를 가져오는 메서드
	public List<MemberVO> getMemberList(String id) {
		return mapper.selectMemberList(id);
	}

	// 1-(2). 주문서에 장바구니 결제 상품 정보를 가져오는 메서드
	public List<CartProductVO> getCartList(String id, int proCode, int cartCode) {
		return mapper.selectCartList(id, proCode, cartCode);
	}

	// 2-(1)-1. 카트번호가 중복되는 주문상품 조회 구문
	public int selectCartOrder(int cartCode) {
		return mapper.selectCartOrder(cartCode);
	}
	
	// 2-(1)-2. 카트번호가 중복되는 주문상품 삭제 구문
	public void deleteCartOrder() {
		
		int deleteCount = mapper.deleteCartOrder();
		
		if(deleteCount >= 0) {
			mapper.deleteOrderStatusEqZero();
		}
		
	}
	
	//------------ 2-(1)-1,2가 완료된 후 실행되는 2-(1)---------
	
	// 2-(1). 주문자 정보 & 주문상품 등록 구문 (orders & order_product table)
	public int insertOrder(OrdersBeanVO order, OrderProductVO orderProduct, int cartCode) {
		int insertOrderCount = mapper.insertOrder(order, orderProduct, cartCode);
		int insertOrderProductCount = 0;
		
		if(insertOrderCount > 0) { // 주문자 정보 입력 성공 후
			// 주문상품 등록 구문 실행
			insertOrderProductCount =mapper.insertOrderProduct(order, cartCode);
		}
		
		return insertOrderProductCount;
	}

	// 2-(2). 주문자 정보 리스트 (출력용)
	public List<OrdersBeanVO> getOrderMemberList(String id, int cartCode) {
		return mapper.selectOrderMemberList(id, cartCode);
	}

	// 2-(3). 주문상품 정보 리스트 (출력용)
	public List<CartProductVO> getOrderProductList(String id, int cartCode) {
		return mapper.selectOrderProductList(id, cartCode);
	}

	// 3. 결제 페이지 : 쿠폰 페이지에서 받아온 쿠폰 코드로 할인금액 계산
	public int getCouponDiscountPrice(int cart_code, String cp_code) {
		int couponDiscountAmount = mapper.selectCouponDiscountPrice(cart_code, cp_code); // 쿠폰 적용 할인 금액 (주문마다 - 변동)
		int couponMaxDiscount = mapper.selectCouponMaxDiscountPrice(cp_code); // 최대 할인 금액(쿠폰마다 정해진 상한선 - 고정)
		
		int finalCouponDiscount = 0; // 최종 리턴값
		
		if(couponDiscountAmount > couponMaxDiscount) {
			finalCouponDiscount = couponMaxDiscount;
		} else {
			finalCouponDiscount = couponDiscountAmount;
		}
		
		return finalCouponDiscount;
	}
	
	//---------------------주문/결제 작업------------------------
  
  

}


