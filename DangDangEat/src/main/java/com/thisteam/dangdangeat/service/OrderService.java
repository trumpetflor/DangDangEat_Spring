package com.thisteam.dangdangeat.service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.CouponMapper;
import com.thisteam.dangdangeat.mapper.OrderMapper;
import com.thisteam.dangdangeat.vo.CartProductVO;
import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.Coupon_viewVO;
import com.thisteam.dangdangeat.vo.Mc_viewVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.OrderProductVO;
import com.thisteam.dangdangeat.vo.OrdersBeanVO;

@Service
public class OrderService {
	

  @Autowired
  private OrderMapper mapper;  // mijoo
  @Autowired
  private CouponMapper mapper_cp; // hawon
  
	
  // ==================================== Hawon ====================================
  
	
	//관리자페이지 쿠폰 리스트
	public List<Coupon_viewVO> selectCouponList() {
		
		return mapper_cp.selectCouponList();
	}

	
	//관리자페이지 쿠폰 전체 개수 조회
	public int getCouponTotalAmount() {
		return mapper_cp.selectCouponCount();
	}

	// 관리자 쿠폰등록페이지에서 중복된 쿠폰코드인지 확인(Ajax)
	public boolean isDuplicateCoupon(String cp_code) {
		boolean isExist = false;
		
		if(mapper_cp.selectCouponCode(cp_code) != null) {// 조회결과가 있으면 중복된 쿠폰
			isExist = true;
		}
		return isExist;
	}
	
	//회원별 사용가능 쿠폰 조회
	public JSONArray getUsableMemberCoupon(String sId) {
//		System.out.println("getUsableMemberCoupon 메서드");
//		System.out.println("sId:  "+sId);
		
//		//1. 생일쿠폰 업데이트 작업
//		mapper_cp.updateBirthCp(sId);
		
		//2. 쿠폰 조회 후 JSON 데이터로 변경

		List<Map<String,Object>> couponList = mapper_cp.selectMemberCoupon(sId);
		JSONArray jArr = new JSONArray(mapper_cp.selectMemberCoupon(sId));
		
//		System.out.println("jArr :  " + jArr);
		return jArr;
	}

	
	//1. 사용가능한 쿠폰인지 조회
	public boolean isUsableCouponCodebyUser(String cp_code) {
//	System.out.println("사용 가능? "+ mapper_cp.selectCouponCodebyUser(cp_code));
		return mapper_cp.selectCouponCodebyUser(cp_code) == null ? false : true;
	}
	//2. 쿠폰 등록 작업
	public int giveCouponToMember(String sId,String cp_code) {
		 int insertCount = 0;
		//1.회원이 가지고 있는 쿠폰인지 조회
		 String PossessCode =  mapper_cp.selectIsPossessCode(sId,cp_code);
		 
		 //2. 사용가능한 쿠폰일 경우 회원 쿠폰 테이블에 Insert
		 if(PossessCode == null) {
			 insertCount  = mapper_cp.insertCouponCodeToMember( sId ,  cp_code);
		 }

		return insertCount;
	}
	

	
	//쿠폰 히스토리
	public List<Mc_viewVO> getCouponHistory(String sId) {
		return mapper_cp.selectCouponHistory(sId);
		
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


