package com.thisteam.dangdangeat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thisteam.dangdangeat.service.CartService;
import com.thisteam.dangdangeat.vo.CartVO;

@Controller
public class CartController {

	@Autowired
	private CartService service;
	
	// 장바구니 담기
	@PostMapping(value = "CartInsert")
	public String cartInsert(@RequestParam String path,
							@RequestParam(defaultValue = "1") int amount,
							@ModelAttribute CartVO cart,
							HttpSession session, 
							HttpServletResponse response) {
		
		try {
			
			// 아이디 저장
			String sId = (String)session.getAttribute("sId");
			cart.setMember_id(sId);
			cart.setCart_amount(amount);
			System.out.println(cart);
			// 이미 들어있는 상품인지 확인
			String isExist = service.isCartExist(cart);
			if(isExist == null) { // 없음 = 추가해야함
				// 장바구니 추가
				int insertCount = service.insertCart(cart);
				if(insertCount > 0) { // 추가 성공
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('장바구니에 상품이 담겼습니다!')");
					out.println("history.back()");
					out.println("</script>");
					
					// 경로값
					if(path == "product") {
						return "product/list_detail";
					} else if(path == "product_list") {
						return "product/list";
					} else if(path == "wishlist") {
						return "cart/wishlist_detail_view";
					}
				} else { // 추가 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('장바구니 추가 실패!')");
					out.println("history.back()");
					out.println("</script>");
					
					// 경로값
					if(path == "product") {
						return "product/list_detail";
					} else if(path == "product_list") {
						return "product/list";
					} else if(path == "wishlist") {
						return "cart/wishlist_detail_view";
					}
				}
				
			} else { // 이미 있음
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('장바구니에 있는 상품입니다!')");
				out.println("history.back()");
				out.println("</script>");
				
				// 경로값
				if(path == "product") {
					return "product/list_detail";
				} else if(path == "product_list") {
					return "product/list";
				} else if(path == "wishlist") {
					return "cart/wishlist_detail_view";
				}
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "product/list_detail";
	}
	
	// 장바구니 목록
	@GetMapping(value = "CartList")
	public String cartList(HttpSession session, Model model) {
		
		String member_id = (String)session.getAttribute("sId");
		
		// 장바구니 목록 가져오기
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
	@PostMapping(value = "WishlistDelete")
	public String wishlistDelete() {
		
		return "redirect:/WishlistDetail";
	}
	
	
}













