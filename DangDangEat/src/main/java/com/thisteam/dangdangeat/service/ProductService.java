package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.ProductMapper;
import com.thisteam.dangdangeat.vo.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductMapper mapper;
	
	// 상품 리스트조회
	// => 파라미터 : 검색어, 카테고리, 시작행번호, 목록갯수   리턴타입 : List<ProductBean>(ProductList)
	public List<ProductVO> getProductList(String keyword, String category, int startRow, int listLimit) {
		return mapper.selectProductList(keyword, category, startRow, listLimit);
	}

	// 전체 상품 수 조회
	// => 파라미터 : 키워드, 카테고리   리턴타입 : int(listCount)
	public int getProductListCount(String keyword, String category) {
		return mapper.selectProductListCount(keyword, category);
	}
	

}















