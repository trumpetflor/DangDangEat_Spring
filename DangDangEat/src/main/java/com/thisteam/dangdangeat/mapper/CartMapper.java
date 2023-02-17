package com.thisteam.dangdangeat.mapper;

import java.util.List;

import com.thisteam.dangdangeat.vo.CartVO;

public interface CartMapper {
	
	// 장바구니 담기
	int insertCart(CartVO cart);

	// 장바구니 목록
	List<CartVO> selectCartList(String member_id);
	
	// 장바구니 중복상품 확인
	String isCartExist(CartVO cart);
	
	
	

}













