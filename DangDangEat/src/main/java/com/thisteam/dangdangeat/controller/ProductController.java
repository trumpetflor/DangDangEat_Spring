package com.thisteam.dangdangeat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thisteam.dangdangeat.service.OrderService;
import com.thisteam.dangdangeat.service.ProductService;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.ProductVO;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	//상품 등록
	@GetMapping(value = "/ProductInsertForm.pd")
	public String insert() {
		return "product/product_insertForm";
	}
	
	//상품 리스트
	@GetMapping(value = "/ProductList.pd")
	public String ProductList(
			@RequestParam(defaultValue = "") String category,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "1") int pageNum,
			Model model) {
	
		// ---------------------------------------------------------------------------
		// 페이징 처리를 위한 변수 선언
		int listLimit = 12; // 한 페이지에서 표시할 게시물 목록을 12개로 제한
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		
		// Service 객체의 getProductList() 메서드를 호출하여 게시물 목록 조회
		// => 파라미터 : 검색어, 카테고리, 시작행번호, 목록갯수   리턴타입 : List<ProductBean>(ProductList)
		List<ProductVO> productList = service.getProductList(keyword, category, startRow, listLimit);
		
		// ---------------------------------------------------------------------------
		// 페이징 처리 
		//1. 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
		//=> 파라미터 : 검색타입, 검색어   리턴타입 : int(listCount)
		int listCount = service.getProductListCount(keyword,category);
		
		// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정 (페이지 번들의 갯수)
		int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 10개로 제한	
		
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
		
		// 상품리스트 객체(productList) 와 페이징 정보 객체(pageInfo)를 Model 객체에 저장
		model.addAttribute("productList", productList);
		model.addAttribute("pageInfo", pageInfo);
		
		return "product/list";
	}
	
}













