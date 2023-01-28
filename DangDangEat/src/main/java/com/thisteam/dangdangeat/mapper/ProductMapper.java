package com.thisteam.dangdangeat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.ProductVO;

public interface ProductMapper {

	//1. 상품 리스트 조회
	// => 파라미터 : 검색어, 카테고리, 시작행번호, 목록갯수   리턴타입 : List<ProductBean>(ProductList)
	// => 주의! 복수개의 파라미터 지정 시 @Param 어노테이션 필수! (파라미터명 지정)
	List<ProductVO> selectProductList(
			@Param("keyword") String keyword, 
			@Param("category") String category, 
			@Param("startRow") int startRow, 
			@Param("listLimit") int listLimit);
	
	//2. 전체 상품 수 조회
	// => 파라미터 : 키워드, 카테고리   리턴타입 : int(listCount)
	// => 주의! 복수개의 파라미터 지정 시 @Param 어노테이션 필수! (파라미터명 지정)	
	int selectProductListCount(
			@Param("keyword") String keyword, 
			@Param("category") String category);

	//3. 상품 상세 정보 조회
	// => 파라미터 : 상품번호  리턴타입 : ProductVO(product)
	ProductVO selectProductDetail(int pro_code);

	
	

}













