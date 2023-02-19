package com.thisteam.dangdangeat.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.thisteam.dangdangeat.service.OrderService;
import com.thisteam.dangdangeat.vo.CartProductVO;
import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.OrderProductVO;
import com.thisteam.dangdangeat.vo.OrdersBeanVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;

  
  // ==================================== Hawon =============================================

	@GetMapping(value = "SelectCoupon")
	public String selectCoupon(){
		
		return "order/coupon_select";
	}


	@GetMapping(value = "SearchUsableCoupon.ajax")// ajax요청 서블릿
	@ResponseBody
	public void SearchUsableCoupon(HttpSession session, 
									HttpServletResponse response, 
									HttpServletRequest request) {
		System.out.println("SearchUsableCoupon 메서드");
		 
		String sId = null;
		session =  request.getSession(false);
		  
		if(session != null) {
			sId = (String)session.getAttribute("sId");
		}
		System.out.println("sId : "+ sId);
		JSONArray couponList = service.getUsableMemberCoupon(sId);
		System.out.println("couponList : "+  couponList);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(couponList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}		
	
	

	@GetMapping(value = "SearchUsableCouponMypage.ajax" )// ajax요청 서블릿
	public String SearchUsableCouponMypage(HttpSession session, 
									HttpServletResponse response, 
									HttpServletRequest request){
		
		String sId = null;
	
		 response.setCharacterEncoding("UTF-8");
		session =  request.getSession(false);

		
		JSONArray couponList = service.getUsableMemberCoupon(sId);
	
		request.setAttribute("couponList", couponList);
		
		
		return "member/mypage_couponAjax";
	}
	
	

	@GetMapping(value = "SearchCouponCode.ajax" )// ajax요청 서블릿- 쿠폰검색버튼
	@ResponseBody
	public void SearchCouponCode(HttpSession session, CouponVO coupon, HttpServletResponse response){
		System.out.println(coupon.getCp_code());

		String sId = (String)session.getAttribute("sId");
		service.selectCouponCodebyUser(sId, coupon.getCp_code());
		
		
		
	}

  // ==================================== Mijoo =============================================
	/*
	 *  1. 주문 상품 관련 정보 + 주문 회원 정보 들고 이동
	 *  (post 방식으로 주문자의 order_detail을 db에 저장)	
	 * 
	 *  2. 주문 상품 관련 정보를 목록에 뿌리기 - orderform & orderConfirmation
	 *  (get 방식으로 db에 있는 정보 가져오기)
	 *  
	 *  3. 주문 회원 정보 - 주문자 정보에 뿌리기 (memberDAO 이용)
	 *  + 주문 정보 DB에 저장
	 * 
	 *  4. 주문자 정보와 배송정보가 일치하지 않는
	 *    비회원 주문일 경우 따로 주문 정보 DB에 저장
	 *  
	 *  5. 주문상품, 총수량, 총 금액 결제 테이블로 전달
	 *  
	 *  6. 결제 단계에서 결제 완료 후 
	 *    상품 테이블 : 재고 변경
	 *    회원 테이블 : 주문정보 추가 
	 *    관리자 테이블 : 주문정보 추가 (비회원/회원 분류) + 매출 연결
	 *    
	 */
	
	/*
	 * 1.(완료) 주문 페이지 (주문서 작성)
	 * "/OrderForm.od" - orderFormAction 
	 * 
	 * 2.(완료)orders * order_product 테이블로 주문정보 입력
	 * "/OrderInsertPro.od" - orderInsertProAction
	 * 
	 * 3.(완료)장바구니 상품 결제 페이지
	 * "/OrderPaymentForm.od" - order/order_payment.jsp / redirect
	 * 
	 * 3-(1).(완료) 쿠폰 페이지에서 받아온 쿠폰코드로 할인금액 계산하는 페이지
	 * "/OrderCouponPro.od" - OrderCouponProAction 
	 * 
	 * 4. 주문확인서 생성 및 결제 작업 진행 페이지
	 * "/OrderPaymentPro.od" - OrderPaymentProAction
	 * 
	 * 5. 주문확인서 페이지 
	 * "/OrderConfirm.od" - order/orderConfirmation.jsp
	 * 
	 */
	
	//---------------------주문/결제 작업------------------------
	
	// 1. 주문서 작성 페이지
	@GetMapping(value = "/OrderForm")
	public String orderForm(HttpSession session, int proCode, int cartCode, Model model) {
		
		String id = (String)session.getAttribute("sId");
		
		// 주문 회원 정보 조회
		List<MemberVO> memberList = service.getMemberList(id);
		
		// 장바구니 정보 조회 
		List<CartProductVO> cartList = service.getCartList(id, proCode, cartCode);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("cartList", cartList);
		
		return "order/orderForm";
		
	} // orderForm
	
	// 2. DB에 주문정보 등록 (orders * order_product 테이블)
	@PostMapping(value = "/OrderInsertPro")
	public String OrderInsert(
			@ModelAttribute OrdersBeanVO order,
			@ModelAttribute OrderProductVO orderProduct,
			HttpSession session, int proCode, int cartCode, Model model) {
	
		String id = (String)session.getAttribute("sId");
		int insertCount = 0;
		
		// 카트번호가 중복되는 주문상품이 있을경우 삭제하는 구문(구조상 100% 실행됨)
		int selectCount = service.selectCartOrder(cartCode);
			
			if(selectCount > 0) { // 카트번호 중복되는 주문상품이 있을 경우

				// 중복되는 주문상품을 삭제 후
				service.deleteCartOrder();
					
				// 주문자 정보 & 주문상품 등록 구문 (orders & order_product table)
				insertCount = service.insertOrder(order, orderProduct, cartCode);
				
			}
		
		
		// 주문자 정보 리스트 (출력용)
		List<OrdersBeanVO> orderMemberList = service.getOrderMemberList(id, cartCode);
		
		// 주문상품 정보 리스트 (출력용)
		List<CartProductVO> orderProductList = service.getOrderProductList(id, cartCode);
		
		if(insertCount > 0) { // 주문 정보 등록 성공
			
			model.addAttribute("orderMemberList", orderMemberList);
			model.addAttribute("orderProductList", orderProductList);
			return "order/order_payment";
			
		} else { // 주문 정보 등록 실패
			
			model.addAttribute("msg", "주문 정보 등록 실패!");
			return "fail_back";
			
		}

	} // OrderInsert
	
	// 3. 결제 페이지 : 쿠폰 페이지에서 받아온 쿠폰 코드로 할인금액 계산
	@GetMapping(value = "/OrderCouponPro")
	public void couponPro(Model model, int cart_code, String cp_code) {
		
		// 쿠폰 적용한 할인금액 계산
		int cp_discount_price = service.getCouponDiscountPrice(cart_code, cp_code);
		model.addAttribute("cp_discount_price", cp_discount_price);
		
	}
	
	
	//---------------------주문/결제 작업------------------------
	
	

	
}













