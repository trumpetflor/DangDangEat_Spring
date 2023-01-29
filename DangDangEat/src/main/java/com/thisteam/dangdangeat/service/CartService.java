package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.CartMapper;
import com.thisteam.dangdangeat.vo.CartVO;

@Service
public class CartService {
	
	@Autowired
	private CartMapper mapper;
	
	// 장바구니 목록
	public List<CartVO> getCartList(String member_id) {
		return mapper.selectCartList(member_id);
	}
	
	

}















