package com.thisteam.dangdangeat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.CartProductVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.OrderProductVO;
import com.thisteam.dangdangeat.vo.OrdersBeanVO;
import com.thisteam.dangdangeat.vo.PaymentsVO;

public interface OrderMapper {

	//---------------------주문/결제 작업------------------------

	// 1-(1). 주문서에 회원의 정보를 가져오는 추상메서드 정의
	List<MemberVO> selectMemberList(String id);
	
	// 1-(2). 주문서에 장바구니 결제 상품 정보를 가져오는 추상메서드 정의
	List<CartProductVO> selectCartList(
			@Param("id") String id, 
			@Param("pro_code") int pro_code);
	
	
	// 2-(1). 주문자 정보 등록 구문 (orders)
	int insertOrder(@Param("order") OrdersBeanVO order, @Param("id") String id);
	
	// 2-(1). 주문상품 등록 구문 (order_product)
	int insertOrderProduct(@Param("orderProduct") OrderProductVO orderProduct,@Param("id") String id);
	
	//----------------------------------------------------------
	
	// 2-(2). 주문자 정보 리스트 (출력용)
	List<OrdersBeanVO> selectOrderMemberList(String id);

	// 2-(3). 주문상품 정보 리스트 (출력용)
	List<CartProductVO> selectOrderProductList(@Param("id") String id, @Param("pro_code") int pro_code);
	
	// 2-(4). 주문 상태가 0인 경우(=미완료) orders & order_product 테이블에서 삭제 (= 중복 데이터 방지)
	int deleteUncompleteOrder(String id);
	int deleteUncompletedProduct(@Param("id") String id, @Param("pro_code") int pro_code);
	
	// 3. 결제 페이지 : 쿠폰 페이지에서 받아온 쿠폰 코드로 할인금액 계산
	int selectCouponDiscountPrice(String cp_code);

	// 3. 결제 페이지 : 최대 할인금액 계산
	int selectCouponMaxDiscountPrice(String cp_code);
	
	
	// 4. 주문확인서 생성 및 결제 작업 진행 비즈니스 로직
	
	// 4-(1). 주문 테이블의 order_status = 1로 변경 시키기
	int UpdateOrderStatus(@Param("id") String id, @Param("order_code") int order_code);

	// 4-(2). 결제 테이블에 정보 입력
	int paymentInsertPro(@Param("payments") PaymentsVO payments,
			 @Param("pay_number") int pay_number,@Param("pro_amount") int pro_amount);

	// 4-(3). 결제완료시 상품 테이블에서 수량변경
	int UpdateProductQty(@Param("order_code") int order_code, @Param("pro_code") int pro_code);

	// 4-(4). 카트에서 주문한 상품 삭제
	int DeleteCart(@Param("id") String id, @Param("pro_code") int pro_code);

	// 4-(5). 사용한 쿠폰 mc_used = 'N' > 'Y'
	int updateCouponStatusE(@Param("payments") PaymentsVO payments, @Param("id") String id);
	int updateCouponStatusM(@Param("payments") PaymentsVO payments, @Param("id") String id);

	// 4-(6). 결제 정보 리스트 생성
	List<PaymentsVO> SelectOrderPaymentsList(@Param("pay_number")int pay_number, @Param("order_code")int order_code);

	// 4-(7). 결제 상품 정보 리스트 생성 
	List<CartProductVO> selectPaymentProductList(
			@Param("id") String id, @Param("pro_code") int pro_code);

	// 4-(8). 배송 정보 및 주문자 정보 리스트 생성
	List<OrdersBeanVO> selectOrderPaymentInfoList(@Param("id") String id, @Param("order_code") int order_code);

	

	

	




	


	
	
	
	//---------------------주문/결제 작업------------------------
}













