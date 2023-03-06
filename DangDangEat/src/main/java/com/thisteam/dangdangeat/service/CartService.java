package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.CartMapper;
import com.thisteam.dangdangeat.vo.CartVO;
import com.thisteam.dangdangeat.vo.WishlistVO;

@Service
public class CartService {
	
	@Autowired
	private CartMapper mapper;
	
	
	// 장바구니 담기
	public int insertCart(CartVO cart) {
		return mapper.insertCart(cart);
	}
	
	// 장바구니 중복상품 확인
	public String isCartExist(CartVO cart) {
		return mapper.isCartExist(cart);
	}
	
	// 장바구니 목록
	public List<CartVO> getCartList(String member_id, int startRow, int listLimit) {
		return mapper.selectCartList(member_id, startRow, listLimit);
	}

	// 장바구니 수량 수정
	public int modifyAmount(String sId, int pro_code, int cart_amount) {
		return mapper.updateAmount(sId, pro_code, cart_amount);
	}

	// 장바구니 개별 삭제
	public int deleteCart(String sId, int pro_code) {
		return mapper.deleteCart(sId, pro_code);
	}

	// 장바구니 목록 갯수
	public int getCartListCount(String sId) {
		return mapper.selectCartListCount(sId);
	}
	
	// 장바구니 다중 삭제
	public int deleteCheckCart(String sId, List<CartVO> cartList) {
		return mapper.deleteCheckCart(sId, cartList);
	}
	// 위시리스트 담기
	public int insertWishlist(WishlistVO wish) {
		return mapper.insertWishlist(wish);
	}
	
	// 위시리스트 중복상품 확인
	public String isWishlistExist(WishlistVO wish) {
		return mapper.isWishlistExist(wish);
	}
	
	// 위시리스트 목록
	public List<WishlistVO> getWishlist(String member_id, int startRow, int listLimit) {
		return mapper.selectWishlist(member_id, startRow, listLimit);
	}
	
	// 위시리스트 개별 삭제
	public int deleteWishlist(String member_id, int pro_code) {
		return mapper.deleteWishlist(member_id, pro_code);
	}
	
	// 위시리스트 다중 삭제
	public int deleteCheckWish(String sId, List<WishlistVO> wishlist) {
		return mapper.deleteCheckWish(sId, wishlist);
	}
	
	// 위시리스트 목록 갯수
	public int getWishlistCount(String sId) {
		return mapper.selectWishlistCount(sId);
	}




	
	
	

}















