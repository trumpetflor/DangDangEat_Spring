package com.thisteam.dangdangeat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thisteam.dangdangeat.service.CartService;
import com.thisteam.dangdangeat.vo.CartVO;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.WishlistVO;

@Controller
public class CartController {

	@Autowired
	private CartService service;
	
	// 장바구니 담기
	@GetMapping(value = "CartInsert")
	public void cartInsert(@RequestParam(defaultValue = "1") int amount,
							@RequestParam int pro_code,
							HttpSession session, 
							HttpServletResponse response) {
		
		try {
			
			CartVO cart = new CartVO();
			// 아이디,수량,제품코드 저장
			String sId = (String)session.getAttribute("sId");
			cart.setMember_id(sId);
			cart.setCart_amount(amount);
			cart.setPro_code(pro_code);
			System.out.println(cart);
			// 중복 여부 확인
			String isExist = service.isCartExist(cart);
			if(isExist == null) { // 없음 = 추가해야함
				// 장바구니 추가
				int insertCount = service.insertCart(cart);
				if(insertCount > 0) { // 추가 성공
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("true");
					
				} else { // 추가 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("false");
					
				}
				
			} else { // 이미 있음
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("duplication");
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 장바구니 다중 추가
	@ResponseBody
	@PostMapping("/CartInsertJson")
	public void cartInsertJson(@RequestBody String jsonData,
								HttpSession session,
								HttpServletResponse response) {
		System.out.println(jsonData);
		
		String sId = (String)session.getAttribute("sId");
		Gson gson = new Gson();
		List<CartVO> cartList =
				gson.fromJson(jsonData, new TypeToken<List<CartVO>>(){}.getType());
		int resultCount = 1;
		for(CartVO cart : cartList) {
			cart.setMember_id(sId);
			cart.setCart_amount(1);
			System.out.println("장바구니 : " + cart);
			
			// 중복 확인
			String isExist = service.isCartExist(cart);
			if(isExist == null) { // 중복 아님
				// 장바구니 추가
				int insertCount = service.insertCart(cart);
				System.out.println(cart.getPro_code()+": 추가됨");
				 // 실패 판별
				if(insertCount <= 0) {
					resultCount = 0;
				} 
			} else {
				System.out.println(cart.getPro_code()+": 중복상품");
			}
		}

		try {
			// 한번이라도 실패하면 0임
			if(resultCount > 0) { // 추가 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 추가 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 장바구니 목록 조회
	@GetMapping(value = "CartList")
	public String cartList(@RequestParam(defaultValue = "1") int pageNum,
							HttpSession session,
							Model model) {
		
		// 세션 아이디
		String sId = "";
		if(session.getAttribute("sId") != null) {
			sId = (String)session.getAttribute("sId");
		} else { // 로그인페이지 이동
			model.addAttribute("msg", "로그인이 필요합니다");
			model.addAttribute("url", "/MemberLoginForm");
			return "redirect";
		}
		
		// 로그인 여부
		if(sId != null && !sId.equals("")) {  // 세션 아이디 있을 경우
			System.out.println("sId : " + sId);
		} else {
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "fail_back";
		}
		
		// ========================= 페이징 처리 =========================
		int listLimit = 10; // 한 페이지 당 상품 10개
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		// 1. 한 페이지에 표시할 페이지 목록(번호) 갯수 계산
		int listCount = service.getCartListCount(sId);
		
		// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정 (페이지 번들의 갯수)
		int pageListLimit = 10; // 한 페이지 당 페이지갯수 10개
		
		// 3. 전체 페이지 목록 수 계산
		int maxPage = listCount / listLimit 
						+ (listCount % listLimit == 0 ? 0 : 1); 
		
		// 4. 시작 페이지 번호 계산
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		// 5. 끝 페이지 번호 계산
		int endPage = startPage + pageListLimit - 1;
		
		// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
		//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);		
		
		// 페이징 정보 객체(pageInfo)
		model.addAttribute("pageInfo", pageInfo);	
		
		// 장바구니 목록
		List<CartVO> cartList = service.getCartList(sId, startRow, listLimit);

		model.addAttribute("cartList", cartList);
		
		return "cart/cart_detail_view";
	}
	
	// 장바구니 수량 수정
	@GetMapping(value = "CartAmountModify")
	public void CartAmountModify(
			@RequestParam int cart_amount,
			@RequestParam int pro_code,
			HttpSession session,
			HttpServletResponse response,
			Model model) {
		
		String sId = (String)session.getAttribute("sId");
		System.out.println(sId + ", amount:" + cart_amount + ", procode: " + pro_code);
		int modifyCount = service.modifyAmount(sId, pro_code, cart_amount);
		
		try {
			if(modifyCount > 0) { // 수정 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 장바구니 개별 삭제
	@GetMapping(value = "CartDelete")
	public void cartDelete(@RequestParam int pro_code,
							HttpSession session,
							HttpServletResponse response,
							Model model) {
		
		String sId = (String)session.getAttribute("sId");
		
		int deleteCount = service.deleteCart(sId, pro_code);
		
		try {
			
			if(deleteCount > 0) { // 삭제 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 삭제 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 장바구니 다중 삭제
	@ResponseBody
	@PostMapping("/CartDeleteJson")
	public void cartDeleteJson(@RequestBody String jsonData,
								HttpSession session,
								HttpServletResponse response) {
//		System.out.println(jsonData);
		
		String sId = (String)session.getAttribute("sId");
		Gson gson = new Gson();
		List<CartVO> cartList =
				gson.fromJson(jsonData, new TypeToken<List<CartVO>>(){}.getType());
//		System.out.println(cartList);
		
		int deleteCount = service.deleteCheckCart(sId, cartList);
		try {
			
			if(deleteCount > 0) { // 삭제 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 삭제 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 위시리스트 담기
	@GetMapping(value = "WishlistInsert")
	public void wishlistInsert(@RequestParam int pro_code,
								HttpSession session,
								HttpServletResponse response) {
		WishlistVO wish = new WishlistVO();
		wish.setPro_code(pro_code);
		String sId = (String)session.getAttribute("sId");
		wish.setMember_id(sId);
		System.out.println(wish);
		
		try {
			// 중복 여부 확인
			String isExist = service.isWishlistExist(wish);
			
			if(isExist == null) { // 중복 아님
				
				int insertCount = service.insertWishlist(wish);
				if(insertCount > 0) { // 추가 성공
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("true");
					
				} else { // 추가 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("false");
					
				}
			} else { // 이미 있는 상품
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("duplication");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 위시리스트 다중 추가
	@ResponseBody
	@PostMapping("/WishlistInsertJson")
	public void wishlistInsertJson(@RequestBody String jsonData,
								HttpSession session,
								HttpServletResponse response) {
		System.out.println(jsonData);
		
		String sId = (String)session.getAttribute("sId");
		Gson gson = new Gson();
		List<WishlistVO> wishlist =
				gson.fromJson(jsonData, new TypeToken<List<WishlistVO>>(){}.getType());
		System.out.println("위시리스트 : " + wishlist);
		int resultCount = 1;
		for(WishlistVO wish : wishlist) {
			wish.setMember_id(sId);
			
			// 중복 확인
			String isExist = service.isWishlistExist(wish);
			if(isExist == null) { // 중복 아님
				// 위시리스트 추가
				int insertCount = service.insertWishlist(wish);
				System.out.println(wish.getPro_code()+": 추가됨");
				 // 실패 판별
				if(insertCount <= 0) {
					resultCount = 0;
				} 
			} else {
				System.out.println(wish.getPro_code()+": 중복상품");
			}
		}

		try {
			// 한번이라도 실패하면 0임
			if(resultCount > 0) { // 추가 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 추가 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 위시리스트 목록 조회
	@GetMapping(value = "WishlistDetail")
	public String wishlistList(@RequestParam(defaultValue = "1") int pageNum,
								HttpSession session,
								Model model) {
		
		// 세션 아이디
		String sId = "";
		if(session.getAttribute("sId") != null) {
			sId = (String)session.getAttribute("sId");
		} else { 
			model.addAttribute("msg", "로그인이 필요합니다");
			model.addAttribute("url", "/MemberLoginForm");
			return "redirect";
		}
		
		// 로그인 여부
		if(sId != null && !sId.equals("")) {  // 세션 아이디 있을 경우
			System.out.println("sId : " + sId);
		} else {
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "fail_back";
		}
		
		// ========================= 페이징 처리 =========================
		int listLimit = 10; // 한 페이지 당 상품 10개
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		// 1. 한 페이지에 표시할 페이지 목록(번호) 갯수 계산
		int listCount = service.getWishlistCount(sId);
		
		// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정 (페이지 번들의 갯수)
		int pageListLimit = 10; // 한 페이지 당 페이지갯수 10개
		
		// 3. 전체 페이지 목록 수 계산
		int maxPage = listCount / listLimit 
						+ (listCount % listLimit == 0 ? 0 : 1); 
		
		// 4. 시작 페이지 번호 계산
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		// 5. 끝 페이지 번호 계산
		int endPage = startPage + pageListLimit - 1;
		
		// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
		//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);		
		
		// 페이징 정보 객체(pageInfo)
		model.addAttribute("pageInfo", pageInfo);
		
		// 위시리스트 목록
		List<WishlistVO> wishlist = service.getWishlist(sId, startRow, listLimit);
		model.addAttribute("wishlist", wishlist);
		
		
		return "cart/wishlist_detail_view";
	}
	
	// 위시리스트 개별 삭제
	@GetMapping(value = "WishlistDelete")
	public void wishlistDelete(@RequestParam int pro_code,
								HttpSession session,
								HttpServletResponse response,
								Model model) {
		
		String sId = (String)session.getAttribute("sId");
		
		int deleteCount = service.deleteWishlist(sId, pro_code);
		
		try {
			
			if(deleteCount > 0) { // 삭제 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 삭제 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 위시리스트 다중 삭제
	@ResponseBody
	@PostMapping("/WishlistDeleteJson")
	public void wishlistDeleteJson(@RequestBody String jsonData,
								HttpSession session,
								HttpServletResponse response) {
		System.out.println(jsonData);
		
		String sId = (String)session.getAttribute("sId");
		Gson gson = new Gson();
		List<WishlistVO> wishlist =
				gson.fromJson(jsonData, new TypeToken<List<WishlistVO>>(){}.getType());
		System.out.println(wishlist);
		
		int deleteCount = service.deleteCheckWish(sId, wishlist);
		try {
			
			if(deleteCount > 0) { // 삭제 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
			} else { // 삭제 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}













