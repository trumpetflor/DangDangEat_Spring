package com.thisteam.dangdangeat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.thisteam.dangdangeat.service.OrderService;
import com.thisteam.dangdangeat.vo.CouponVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;



	@GetMapping(value = "SelectCoupon" )
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
		boolean isMypage = false;
		session =  request.getSession(false);
		  
		if(session != null) {
			sId = (String)session.getAttribute("sId");
		}
		
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
		boolean isMypage = false;
		 response.setCharacterEncoding("UTF-8");
		session =  request.getSession(false);
	
	
		if(request.getParameter("isMypage") != null) {
			isMypage = Boolean.valueOf(request.getParameter("isMypage"));
		}
		JSONArray couponList = service.getUsableMemberCoupon(sId);
		if(isMypage) {
			request.setAttribute("couponList", couponList);
		
		}
		return "member/mypage_couponAjax";
	}
	
	

	@GetMapping(value = "SearchCouponCode.ajax" )// ajax요청 서블릿- 쿠폰검색버튼
	@ResponseBody
	public void SearchCouponCode(HttpSession session, CouponVO coupon, HttpServletResponse response){
		System.out.println(coupon.getCp_code());

		String sId = (String)session.getAttribute("sId");
		service.selectCouponCodebyUser(sId, coupon.getCp_code());
		
		
		
	}

	
}













