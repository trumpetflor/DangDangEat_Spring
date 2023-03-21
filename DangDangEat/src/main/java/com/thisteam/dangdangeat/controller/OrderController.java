package com.thisteam.dangdangeat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.thisteam.dangdangeat.vo.Mc_viewVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.OrderProductArrVO;
import com.thisteam.dangdangeat.vo.OrderProductVO;
import com.thisteam.dangdangeat.vo.OrdersBeanVO;
import com.thisteam.dangdangeat.vo.PaymentsVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		sId =  (String)request.getSession(false).getAttribute("sId");

		JSONArray couponList = service.getUsableMemberCoupon(sId);
	
		request.setAttribute("couponList", couponList);
		System.out.println("sId="+sId);
		//쿠폰 히스토리
		 List<Mc_viewVO> couponHistory = service.getCouponHistory(sId);
		 request.setAttribute("couponHistory", couponHistory);
		
		return "member/mypage_couponAjax";
	}
	
	

	@GetMapping(value = "SearchCouponCode.ajax" )// ajax요청 서블릿- 쿠폰검색버튼
	@ResponseBody
	public void SearchCouponCode(HttpSession session, CouponVO coupon, HttpServletResponse response){
		System.out.println(coupon.getCp_code());
		
		try {	
			response.setCharacterEncoding("UTF-8");
			boolean canUse =  service.isUsableCouponCodebyUser(coupon.getCp_code());
			
			if(canUse) {//사용가능할 쿠폰일 경우 insert작업
				String sId = (String)session.getAttribute("sId");
				int insertCount = service.giveCouponToMember(sId, coupon.getCp_code());
				
				if(insertCount > 0) {
					JSONArray couponList = service.getUsableMemberCoupon(sId);
					response.getWriter().print(couponList);
				}else {//이미 등록된 쿠폰=>true 
					response.getWriter().print(true);
				}
				
			}else {//사용불가능한 쿠폰인 경우=>false
				response.getWriter().print(false);
			}
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
	 * "/OrderForm" 
	 * 
	 * 2.(완료)orders * order_product 테이블로 주문정보 입력
	 * "/OrderInsertPro" 
	 * 
	 * 3.(완료)장바구니 상품 결제 페이지
	 * "/OrderPaymentForm" - order/order_payment.jsp 
	 * 
	 * 3-(1).(완료) 쿠폰 페이지에서 받아온 쿠폰코드로 할인금액 계산하는 페이지
	 * "/OrderCouponPro" 
	 * 
	 * 4.(완료) 주문확인서 생성 및 결제 작업 진행 페이지
	 * "/OrderPaymentPro" 
	 * 
	 * 5.(완료) 주문확인서 페이지 
	 * "/OrderConfirm" - order/orderConfirmation.jsp
	 * 
	 */
	
	//---------------------주문/결제 작업------------------------
	
	// 복수데이터 처리 가능 버전 (23/03/16 이후 업데이트)
	
	// 1. 주문서 작성 페이지 
	@GetMapping(value = "/OrderForm")
	public String orderForm(HttpSession session,@RequestParam("pro_code") int[] proCodeArr, Model model) {
		
		String id = (String)session.getAttribute("sId");
		
		// 주문 회원 정보 조회
		List<MemberVO> memberList = service.getMemberList(id);
		
		// 장바구니 정보 조회 
		List<CartProductVO> cartList = new ArrayList<>(); 
		
		  for(int i=0; i < proCodeArr.length; i++) {
			  int pro_code = proCodeArr[i];
			  CartProductVO cart = service.getCartList(id, pro_code);
			  System.out.println("cart 잘 진행되니 : " + cart);
			  cartList.add(cart);
		  }
		  
		  model.addAttribute("memberList", memberList);
		  model.addAttribute("cartList", cartList);
		
		return "order/orderForm";
		
	} // orderForm

	
	// 2. DB에 주문정보 등록 (orders * order_product 테이블) 
	@PostMapping(value = "/OrderInsertPro")
	public String OrderInsert(
			@ModelAttribute OrdersBeanVO order,
			@ModelAttribute OrderProductArrVO orderProductArr,
			HttpSession session,@RequestParam("pro_code") int[] proCodeArr, Model model) {
	
		String id = (String)session.getAttribute("sId");
		System.out.println("컨트롤러로 넘어온 주문자 정보 : " + order);
		
		// 주문 미완료 & 중복 데이터 방지를 위한 데이터 처리 구문
		service.deleteOrder(id);
		
		// 복수데이터를 하나의 주문번호로 처리하도록 주문번호 생성 
		// orders(주문자 정보 등록) + 주문번호 생성
		int order_code = service.insertOrders(order, id);
		System.out.println("복수데이터 처리를 위한 주문번호 " +order_code);
		
		// 주문자 정보 리스트 (출력용)
		List<OrdersBeanVO> orderMemberList = service.getOrderMemberList(order_code, id);
		System.out.println("오더컨트롤러 주문자 정보 확인 : " + orderMemberList);
		// 주문상품 정보 리스트 (출력용)
		List<CartProductVO> orderProductList = new ArrayList<>();
		// (1). 주문자 정보 & 주문상품 등록 구문 (orders & order_product table)
		int insertCount = 0;
		for(int i=0; i < proCodeArr.length; i++) {
			// 복수상품 데이터(배열) > 단수상품 데이터 vo에 저장
			OrderProductVO orderProduct = new OrderProductVO();
			// 상품코드 저장을 위한 변수 선언
			int pro_code = proCodeArr[i];
			orderProduct.setOrder_stock(orderProductArr.getOrder_stock()[i]);
			orderProduct.setPro_code(pro_code);
			orderProduct.setOrder_code(order_code);
			
			System.out.println("잘 넘어오나 orderProduct - " + orderProduct);
			
			// 주문자 & 주문상품 정보 등록
			insertCount = service.insertOrderProduct(orderProduct);
			
			// 주문상품 정보 리스트 (출력용)
			CartProductVO cartProduct = service.getOrderProductList(id, pro_code);
			orderProductList.add(cartProduct);
			System.out.println("오더컨트롤러 주문상품 정보 확인 : " + orderProductList);
			
			if(insertCount == 0) { // 주문 정보 등록 실패
				
				service.deleteUncompletedOrder(id, pro_code);
				model.addAttribute("msg", "주문 정보 등록 실패!");
				return "fail_back";
				
			} 
			
		}
		
		// 2-(5). 주문금액 합 계산하는 구문
		int totalPrice = service.getTotalPrice(order_code);
		
		model.addAttribute("orderMemberList", orderMemberList);
		model.addAttribute("orderProductList", orderProductList);
		model.addAttribute("totalPrice", totalPrice);
		
		return "order/order_payment";
		
	} // OrderInsert
	
	// 3. 결제 페이지 : 쿠폰 페이지에서 받아온 쿠폰 코드로 할인금액 계산
	@GetMapping(value = "/OrderCouponPro")
	public void couponPro(Model model, String cp_code, int pro_amount, int order_code, HttpServletResponse response) {
		System.out.println("쿠폰 코드로 할인금액 계산 : " + cp_code);
		System.out.println("쿠폰 코드로 pro_amount 계산 : " + pro_amount);
		System.out.println("쿠폰 코드로 order_code 계산 : " + order_code);
		
		// 쿠폰 적용한 할인금액 계산
		int cp_discount_price = service.getCouponDiscountPrice(cp_code, pro_amount, order_code);
		System.out.println("잘 계산된 discount price" + cp_discount_price);
		model.addAttribute("cp_discount_price", cp_discount_price);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(cp_discount_price);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} // couponPro
	
	
	// 4. 주문확인서 생성 및 결제 작업 진행 비즈니스 로직
	@PostMapping(value = "OrderPaymentPro")
	public String OrderPaymentPro(Model model, HttpSession session, HttpServletResponse response,
			@RequestParam("pro_code") int[] proCodeArr, @RequestParam("hidden_total") int total, int order_code,
			 @ModelAttribute PaymentsVO payments) {
		
		/*  결제 단계에서 결제 완료 후 
		 *    상품 테이블 : 재고 변경
		 *    회원 테이블 : 주문정보 추가 
		 *    관리자 테이블 : 주문정보 추가 (비회원/회원 분류) + 매출 연결
		 *    쿠폰 테이블 : 사용 쿠폰 mc_used n > y 로 변경하기
		 */
		
		
		// 0. 최종주문번호 생성 구문
		String id = (String)session.getAttribute("sId");
		System.out.println("상품 코드 잘넘어 옵니까" + proCodeArr);
		System.out.println("상품금액 잘넘어 옵니까" + total);
		System.out.println("주문번호 잘넘어 옵니까" + order_code);
		System.out.println("payments 잘넘어 옵니까" + payments);
		int pay_amount = total - payments.getCp_discount_amount();
		System.out.println("잘 계산된 pay_amount" + pay_amount);
		
		// 주문번호 생성 구문------------------------------------------
		
			// 현재 날짜 구하기
	        LocalDate now = LocalDate.now();
	        // 포맷 정의
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd00");
	        int formatedNow = Integer.parseInt(now.format(formatter));
	        int pay_number = (formatedNow + order_code);
	        // 결과 출력
	        System.out.println(pay_number);  // 23010500
	        
	     //------------------------------------------------------------------
        // 1. 주문 테이블의 order_status = 1로 변경 시키기
	       boolean isOrderStatusUpdate = service.updateOrderStatus(id, order_code);
	    // 2. 결제 테이블에 정보 입력
	       int paymentInsertCount = service.paymentInsertPro(payments, pay_number, pay_amount);
        // 3. 결제 정보 리스트 생성
           List<PaymentsVO> paymentList = service.getPaymentsList(pay_number, order_code);
           model.addAttribute("paymentList", paymentList);
        // 4. 결제 상품 정보 리스트 생성 
           List<CartProductVO> orderProductList = new ArrayList<>();
        // 5. 배송 정보 및 주문자 정보 리스트 생성
           List<OrdersBeanVO> orderInfoList = service.getOrderPaymentInfoList(id, order_code);
           model.addAttribute("orderInfoList", orderInfoList);
		// 6. 결제완료시 상품 테이블에서 수량변경
	       int productQtyUpdateCount = 0;
		// 7. 카트에서 주문한 상품 삭제
	       int cartDeleteCount = 0;
		// 8. 사용한 쿠폰 mc_used = 'N' > 'Y'
	       int couponUpdateCount = service.getCouponUpdateCount(payments, id);
	  
	       for(int i=0; i < proCodeArr.length; i++) {
	    	   
				int pro_code = proCodeArr[i];
				// 4. 결제 상품 정보 리스트 생성 
				 CartProductVO cart = service.getPaymentProductList(id, pro_code);
		         orderProductList.add(cart);
		           
		        // 6. 결제완료시 상품 테이블에서 수량변경
			       productQtyUpdateCount = service.productQtyUpdate(order_code, pro_code);
				
			    // 7. 카트에서 주문한 상품 삭제
			       cartDeleteCount = service.cartDelete(id, pro_code);
			}
	       
	       // 4. 결제 상품 정보 리스트 생성
	       model.addAttribute("orderProductList", orderProductList);
	       
	       try {
			// 결제 후 주문 상태 수정
			   if(isOrderStatusUpdate) {
					// 상품 수량 수정
					 if(productQtyUpdateCount > 0) {
						 // 결제 정보 입력
						 if(paymentInsertCount > 0 ) {
							 // 결제 완료 카트 삭제 
							 if(cartDeleteCount > 0) {
								 // cp_target(new_member, event)면 쿠폰 사용여부를 N > Y로 바꿈
								 if(couponUpdateCount >= 0) {
									 
									 // 주문확인서 페이지로 이동
									 return "order/orderConfirmation";
									 
									 } else {
									 	response.setContentType("text/html; charset=UTF-8");
										PrintWriter out = response.getWriter();
										out.println("<script>");
										out.println("alert('쿠폰 사용여부 업데이트 실패')");
										out.println("history.back()");
										out.println("</script>");
									 }// couponUpdateCount
								 
								 } else {
										response.setContentType("text/html; charset=UTF-8");
										PrintWriter out = response.getWriter();
										out.println("<script>");
										out.println("alert('중복카트 삭제 실패')");
										out.println("history.back()");
										out.println("</script>");
										
								 } // cartDeleteCount
							 
							} else {
								response.setContentType("text/html; charset=UTF-8");
								PrintWriter out = response.getWriter();
								out.println("<script>");
								out.println("alert('결제정보 입력 실패')");
								out.println("history.back()");
								out.println("</script>");
							} // paymentInsertCount
					 	} else {
							response.setContentType("text/html; charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("<script>");
							out.println("alert('상품 수량 업데이트 실패')");
							out.println("history.back()");
							out.println("</script>");
						 
					 } // productQtyUpdateCount
						 
				} else {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('주문상태 업데이트 실패')");
					out.println("history.back()");
					out.println("</script>");
				} //isOrderStatusUpdate
			   
		} catch (IOException e) {
			e.printStackTrace();
		}
	       
       model.addAttribute("msg", "결제 실패!");
       return "fail_back";
	}
	
	//---------------------주문/결제 작업------------------------
	
	

	
}













