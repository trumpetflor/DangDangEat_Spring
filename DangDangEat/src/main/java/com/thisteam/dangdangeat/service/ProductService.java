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

	//상품 상세정보 조회
	// => 파라미터 : 상품번호  리턴타입 : ProductVO(product)
	public ProductVO getProductDetail(int pro_code) {
		return mapper.selectProductDetail(pro_code);
	}

	//상품 등록
	public int prodInsertPro(ProductVO product) {
		return mapper.insertProduct(product);
	}

	//상품 수정
	public int ProdUpdatePro(ProductVO product) {
		return mapper.updateProdUpdate(product);
	}

	//상품 삭제
	public int ProdDeletePro(ProductVO product) {
		return mapper.deleteProdDelete(product);
	}

	

}















