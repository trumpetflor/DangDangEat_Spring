package com.thisteam.dangdangeat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.CartVO;
import com.thisteam.dangdangeat.vo.WishlistVO;

public interface CartMapper {
	
	// 장바구니 담기
	int insertCart(CartVO cart);

	// 장바구니 목록
	List<CartVO> selectCartList(@Param("member_id") String member_id,
								@Param("startRow") int startRow,
								@Param("listLimit") int listLimit);
	
	// 장바구니 중복상품 확인
	String isCartExist(CartVO cart);

	// 장바구니 수량 수정
	int updateAmount(@Param("sId") String sId,
					@Param("pro_code") int pro_code,
					@Param("cart_amount") int cart_amount);
	
	// 장바구니 개별 삭제
	int deleteCart(@Param("sId") String sId,
					@Param("pro_code") int pro_code);
	
	// 장바구니 목록 갯수
	int selectCartListCount(String sId);

	// 위시리스트 담기
	int insertWishlist(WishlistVO wish);

	// 위시리스트 중복상품 확인
	String isWishlistExist(WishlistVO wish);
	
	// 위시리스트 목록
	List<WishlistVO> selectWishlist(@Param("member_id") String member_id,
									@Param("startRow") int startRow,
									@Param("listLimit") int listLimit);
	
	// 위시리스트 개별 삭제
	int deleteWishlist(@Param("member_id") String member_id,
						@Param("pro_code") int pro_code);
	
	// 위시리스트 목록 갯수
	int selectWishlistCount(String sId);
	
	

}













