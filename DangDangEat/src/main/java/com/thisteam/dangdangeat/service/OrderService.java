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
import com.thisteam.dangdangeat.vo.PaymentsVO;

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
	public CartProductVO getCartList(String id, int pro_code) {
		return mapper.selectCartList(id, pro_code);
	}

	// 2-(1)-1. 중복 주문건 처리 구문
	public void deleteOrder(String id) {
		mapper.deleteUncompletedProduct(id); // 미완료 주문상품 삭제
		mapper.deleteUncompleteOrder(id); // 미완료 주문자 정보 삭제
	}
	
	// 2-(1)-2. 주문자 정보 등록 (orders)
	public int insertOrders(OrdersBeanVO order, String id) {
		
		int insertOrdersCount = mapper.insertOrder(order, id);
		int order_code = 0;
		
		if(insertOrdersCount > 0) { // 주문자 정보 입력 성공 후
			System.out.println("주문자 정보 등록 성공");
			order_code = mapper.getOrderCode(id);
		}
		
		return order_code;
	}
	
	// 2-(1)-3. 주문상품 등록 구문 (order_product table)
	public int insertOrderProduct(OrderProductVO orderProduct) {

		int insertOrderProductCount = 0;
	
		insertOrderProductCount = mapper.insertOrderProduct(orderProduct);
			if(insertOrderProductCount > 0) {
			 System.out.println("주문 상품 정보 등록 성공");
			}
		return insertOrderProductCount;
	}
	
	// 2-(1). 주문자 정보 & 주문상품 등록 구문 (orders & order_product table)
//		public int insertOrderProduct(OrdersBeanVO order, OrderProductVO orderProduct, String id, int pro_code) {
			// 1. 중복 주문 처리 구문
//			int pro_code = orderProduct.getPro_code();
//			mapper.deleteUncompletedProduct(id, pro_code); // 미완료 주문상품 삭제
//			mapper.deleteUncompleteOrder(id); // 미완료 주문자 정보 삭제
//			
//			// 2. 주문자 정보 등록 (orders)
//			int insertOrderCount = mapper.insertOrder(order, id);
//			int insertOrderProductCount = 0;
//			
//			if(insertOrderCount > 0) { // 주문자 정보 입력 성공 후
				// 3. 주문상품 등록 (order_product)
//				insertOrderProductCount = mapper.insertOrderProduct(orderProduct, id);
//				System.out.println("주문 상품 정보 등록 성공");
//			}
//			
//			return insertOrderProductCount;
//		}

	// 2-(2). 주문자 정보 리스트 (출력용)
	public List<OrdersBeanVO> getOrderMemberList(int order_code, String id) {
		return mapper.selectOrderMemberList(order_code, id);
	}

	// 2-(3). 주문상품 정보 리스트 (출력용)
	public CartProductVO getOrderProductList(String id, int pro_code) {
		return mapper.selectOrderProductList(id, pro_code);
	}

	// 2-(4). 주문정보 등록 실패 시 실행하는 구문
	public void deleteUncompletedOrder(String id, int pro_code) {
		// 1. 주문 상태가 0인 경우(=미완료) orders 테이블에서 삭제 (= 중복 데이터 방지)
		int deleteOrderCount = mapper.deleteUncompletedProduct(id);
		if(deleteOrderCount > 0) {
			mapper.deleteUncompleteOrder(id);
		}
		
	}
	
	// 2-(5). 주문금액 합 계산하는 구문
	public int getTotalPrice(int order_code) {
		return mapper.getTotalPrice(order_code);
	}

	
	// 3. 결제 페이지 : 쿠폰 페이지에서 받아온 쿠폰 코드로 할인금액 계산
	public int getCouponDiscountPrice(String cp_code, int pro_amount, int order_code) {
		
		int couponDiscountAmount = mapper.selectCouponDiscountPrice(cp_code, pro_amount, order_code); // 쿠폰 적용 할인 금액 (주문마다 - 변동)
		int couponMaxDiscount = mapper.selectCouponMaxDiscountPrice(cp_code); // 최대 할인 금액(쿠폰마다 정해진 상한선 - 고정)
		
		System.out.println("서비스로 넘어온 할인 금액" + couponDiscountAmount);
		System.out.println("서비스로 넘어온 최대 할인 금액" + couponMaxDiscount);
		int finalCouponDiscount = 0; // 최종 리턴값
		
		if(couponDiscountAmount > couponMaxDiscount) {
			finalCouponDiscount = couponMaxDiscount;
		} else {
			finalCouponDiscount = couponDiscountAmount;
		}
		
		return finalCouponDiscount;
	}


	// 4. 주문확인서 생성 및 결제 작업 진행 비즈니스 로직
	
	// 4-(1). 주문 테이블의 order_status = 1로 변경 시키기
	public boolean updateOrderStatus(String id, int order_code) {
		boolean isOrderStatusUpdate = false;
		
		int OrderStatusUpdateCount = mapper.UpdateOrderStatus(id, order_code);
		
		if(OrderStatusUpdateCount > 0 ) {
			isOrderStatusUpdate = true; 
		} 
		return isOrderStatusUpdate;
	}
	
	// 4-(2). 결제 테이블에 정보 입력
	public int paymentInsertPro(PaymentsVO payments, int pay_number, int pay_amount) {
		int paymentInsertCount = 0;
		
		paymentInsertCount = mapper.paymentInsertPro(payments, pay_number, pay_amount);
		
		return paymentInsertCount;
	}

	// 4-(3). 결제완료시 상품 테이블에서 수량변경
	public int productQtyUpdate(int order_code, int pro_code) {
		int productQtyUpdateCount = 0;
		
		productQtyUpdateCount = mapper.UpdateProductQty(order_code, pro_code);
		
		return productQtyUpdateCount;
	}

	// 4-(4). 카트에서 주문한 상품 삭제
	public int cartDelete(String id, int pro_code) {
		int cartDeleteCount = 0;
		
		cartDeleteCount = mapper.DeleteCart(id, pro_code);
		
		return cartDeleteCount;
	}

	// 4-(5). 사용한 쿠폰 mc_used = 'N' > 'Y'
	public int getCouponUpdateCount(PaymentsVO payments, String id) {
		int couponUpdateCount = 0;
		
		// 'cp_target = event'
		couponUpdateCount = mapper.updateCouponStatusE(payments, id);
		
		// 'cp_target = new_member'
		if(couponUpdateCount >= 0) {
			couponUpdateCount = mapper.updateCouponStatusM(payments, id);
		}
		return couponUpdateCount;
	}

   // 4-(6). 결제 정보 리스트 생성
	public List<PaymentsVO> getPaymentsList(int pay_number, int order_code) {
		List<PaymentsVO> paymentsList = null;
		
		paymentsList = mapper.SelectOrderPaymentsList(pay_number, order_code);
		System.out.println("service로 잘넘어온 결제 정보 : " + paymentsList);
		
		return paymentsList;
	}

	// 4-(7). 결제 상품 정보 리스트 생성 
	public CartProductVO getPaymentProductList(String id, int pro_code) {
		CartProductVO paymentProductList= null;
		
		paymentProductList = mapper.selectPaymentProductList(id, pro_code);
		
		return paymentProductList;
	}
	
	// 4-(8). 배송 정보 및 주문자 정보 리스트 생성
	public List<OrdersBeanVO> getOrderPaymentInfoList(String id, int order_code) {
		List<OrdersBeanVO> orderPaymentInfoList= null;
		
		orderPaymentInfoList = mapper.selectOrderPaymentInfoList(id, order_code);
		
		return orderPaymentInfoList;
	}


	
	

	
	//---------------------주문/결제 작업------------------------
  
  

}


