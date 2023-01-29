package com.thisteam.dangdangeat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thisteam.dangdangeat.service.CartService;
import com.thisteam.dangdangeat.vo.CartVO;

@Controller
public class CartController {

	@Autowired
	private CartService service;
	
	// 장바구니 담기
	@GetMapping(value = "CartInsert")
	public String cartInsert() {
		
		
		return "product/list_detail";
	}
	
	// 장바구니 목록
	@GetMapping(value = "CartList")
	public String cartList(HttpSession session, Model model) {
		
		String member_id = (String)session.getAttribute("sId");
		
		List<CartVO> cartList = service.getCartList(member_id);
		
		model.addAttribute("cartList", cartList);
		
		return "cart/cart_detail_view";
	}
	
	// 장바구니 수량 수정
	@PostMapping(value = "CartAmountModify")
	public String CartAmountModify() {
		
		return "redirect:/CartList";
	}
	
	// 장바구니 삭제
	@PostMapping(value = "CartDelete")
	public String cartDelete() {
		
		return "redirect:/CartList";
	}
	
	// 위시리스트 담기
	@GetMapping(value = "WishlistInsert")
	public String wishlistInsert() {
		
		
		return "product/list_detail";
	}
	
	// 위시리스트 목록
	@GetMapping(value = "WishlistDetail")
	public String wishlistList() {
		
		return "cart/wishlist_detail_view";
	}
	
	// 위시리스트 삭제
	@PostMapping(value = "CartDelete")
	public String wishlistDelete() {
		
		return "redirect:/WishlistDetail";
	}
	
	
}













