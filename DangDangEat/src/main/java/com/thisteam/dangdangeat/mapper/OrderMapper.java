package com.thisteam.dangdangeat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.CartProductVO;
import com.thisteam.dangdangeat.vo.Mc_viewVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.OrderProductVO;
import com.thisteam.dangdangeat.vo.OrdersBeanVO;

public interface OrderMapper {

	//---------------------주문/결제 작업------------------------

	// 1-(1). 주문서에 회원의 정보를 가져오는 추상메서드 정의
	List<MemberVO> selectMemberList(String id);
	
	// 1-(2). 주문서에 장바구니 결제 상품 정보를 가져오는 추상메서드 정의
	List<CartProductVO> selectCartList(
			@Param("id") String id, 
			@Param("proCode") int proCode, 
			@Param("cartCode") int cartCode);

	// 2-(1)-1. 카트번호가 중복되는 주문상품 조회 구문
	int selectCartOrder(int cartCode);
	
	// 2-(1)-2. 카트번호가 중복되는 주문상품 삭제 구문 - 1
	int deleteCartOrder();
	// 2-(1)-2. 카트번호가 중복되는 주문상품 삭제 구문 - 2
	void deleteOrderStatusEqZero();
	
	//------------ 2-(1)-1,2가 완료된 후 실행되는 2-(1)---------
	
	// 2-(1). 주문자 정보 등록 구문 (orders)
	int insertOrder(
			@Param("order") OrdersBeanVO order, 
			@Param("orderProduct") OrderProductVO orderProduct, 
			@Param("cartCode") int cartCode);
	// 2-(1). 주문상품 등록 구문 (order_product)
	int insertOrderProduct(
			@Param("order") OrdersBeanVO order, 
			@Param("cartCode") int cartCode);
	
	//----------------------------------------------------------
	
	// 2-(2). 주문자 정보 리스트 (출력용)
	List<OrdersBeanVO> selectOrderMemberList(
			@Param("id") String id, 
			@Param("cartCode") int cartCode);

	// 2-(3). 주문상품 정보 리스트 (출력용)
	List<CartProductVO> selectOrderProductList(
			@Param("id") String id, 
			@Param("cartCode") int cartCode);

	// 3. 결제 페이지 : 쿠폰 페이지에서 받아온 쿠폰 코드로 할인금액 계산
	int selectCouponDiscountPrice(
			@Param("cartCode") int cart_code, 
			@Param("cpCode") String cp_code);

	// 3. 결제 페이지 : 최대 할인금액 계산
	int selectCouponMaxDiscountPrice(String cp_code);

	List<Mc_viewVO> selectCouponHistory(String sId);




	


	
	
	
	//---------------------주문/결제 작업------------------------
}













