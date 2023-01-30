package com.thisteam.dangdangeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.thisteam.dangdangeat.service.AdminService;
import com.thisteam.dangdangeat.service.OrderService;

@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	private OrderService odService;

	//관리자 쿠폰등록 view페이지
	@GetMapping(value = "AdminCouponRegister")
	public String CouponRegister() {
		return "admin/admin_coupon";
	}
	
	//top에 있는 about 페이지로 이동 
	@GetMapping(value = "/About.ad")
	public String insert() {
		return "main/about";
	}
	
	@GetMapping(value = "AdminCouponList")
	public String AdminCouponList(Model model) {
		
		
		
		//admin_couponList.jsp에서 보여질 쿠폰 리스트 JSONArray형태로 request객체에 저장
//		JSONArray CouponList =  odService.SelectCouponList();
//		System.out.println(CouponList +" - "+this.getClass());
//		model.setAttribute("CouponList", CouponList);
		
		//쿠폰 개수 리턴받음
		int couponCount = odService.getCouponTotalAmount();
//		model.addAttribute("couponCount", CouponList);
		


		return "admi/admin_couponList";
	}
}













