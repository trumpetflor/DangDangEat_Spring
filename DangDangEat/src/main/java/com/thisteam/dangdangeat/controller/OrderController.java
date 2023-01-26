package com.thisteam.dangdangeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thisteam.dangdangeat.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;



	@GetMapping(value = "SelectCoupon" )
	public String selectCoupon(){
		return "order/coupon_select";
	}


	@GetMapping(value = "SearchUsableCoupon.od" )// ajax요청 서블릿
	public String SearchUsableCoupon(){

		return "";
	}

	@GetMapping(value = "SearchCouponCode.od" )// ajax요청 서블릿- 쿠폰검색버튼
	public String SearchCouponCode(){
		return "";
	}

	
}













