package com.thisteam.dangdangeat.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.thisteam.dangdangeat.service.OrderService;
import com.thisteam.dangdangeat.service.ProductService;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.ProductVO;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	//상품 등록 화면
	@GetMapping(value = "/ProductInsertForm.pd")
	public String insert(@RequestParam(defaultValue = "") String msg,
			Model model) {
		model.addAttribute("msg", msg);
		return "product/product_insertForm";
	}
	
	//상품 등록 Action
	@PostMapping(value = "/ProdInsertPro")
	public String prodInsertPro(@ModelAttribute ProductVO product, Model model, HttpSession session,
		 @RequestParam("file_thumb") MultipartFile thumb_file,
		 @RequestParam("file_img") MultipartFile img_file, MultipartRequest request) {
		//파일업로드 시작
		String uploadPath = "/resources/upload"; // 업로드 가상디렉토리(이클립스)
		String realPath = session.getServletContext().getRealPath(uploadPath);
		//실제 업로드 경로 
		System.out.println("실제 업로드 경로 : " + realPath);
		 
		String pro_thumb = thumb_file.getOriginalFilename().toString(); // 실제 등록 썸네일 파일명
		product.setPro_thumb(pro_thumb);
		product.setPro_real_thumb(pro_thumb);
		
		String pro_img = img_file.getOriginalFilename().toString(); // 실제 등록 이미지 파일명
		product.setPro_img(pro_img);
		product.setPro_real_img(pro_img);
		
		// 썸네일 파일 생성
		File thumb_f = new File(realPath, pro_thumb); 
		try {
			thumb_file.transferTo(thumb_f);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// 파일 경로가 존재하지 않을 경우 파일 경로 생성
		if(!thumb_f.exists()) {
			thumb_f.mkdirs();
		}
		// 썸네일 파일 생성 끝

		// 이미지 파일 생성
		File img_f = new File(realPath, pro_img); 
		try {
			img_file.transferTo(img_f);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// 파일 경로가 존재하지 않을 경우 파일 경로 생성
		if(!img_f.exists()) {
			img_f.mkdirs();
		}
		// 이미지 파일 생성 끝
		
		
		int insertCount = service.prodInsertPro(product);
		if(insertCount > 0) { // 등록 성공 시
			return "redirect:/ProductInsertForm.pd?msg="+"1"; 
		}else {
			model.addAttribute("msg", "상품 등록 실패!");
			return "fail_back";
		}
		
		
	} 
	
	//상품 수정 화면
	@GetMapping(value = "/ProductModifyForm.pd")
	public	String ProdUpdateForm(
			@RequestParam(defaultValue = "") int pro_code, 
			@RequestParam(defaultValue = "") String msg,
			Model model) {
		
		if(!"2".equals(msg)) {
			ProductVO product = service.getProductDetail(pro_code);	
			model.addAttribute("product", product);
		}
		model.addAttribute("msg", msg);
		
		return "product/product_modifyForm";
	}

	//상품 상세정보 수정 진행(update)
	@PostMapping(value = "/ProdUpdatePro")
		public String prodDetailUpdatePro(
				@ModelAttribute ProductVO product, 
				@RequestParam("file_thumb") MultipartFile thumb_file,
				@RequestParam("file_img") MultipartFile img_file, 
				Model model, HttpSession session){
			
				//파일업로드 시작
				String uploadPath = "/resources/upload"; // 업로드 가상디렉토리(이클립스)
				String realPath = session.getServletContext().getRealPath(uploadPath);
				//실제 업로드 경로 
				System.out.println("실제 업로드 경로 : " + realPath);
				
				String pro_thumb = thumb_file.getOriginalFilename().toString(); // 실제 등록 썸네일 파일명
				product.setPro_thumb(pro_thumb);
				product.setPro_real_thumb(pro_thumb);
				
				String pro_img = img_file.getOriginalFilename().toString(); // 실제 등록 이미지 파일명
				product.setPro_img(pro_img);
				product.setPro_real_img(pro_img);
				
				// 썸네일 파일 생성
				File thumb_f = new File(realPath, pro_thumb); 
				try {
					thumb_file.transferTo(thumb_f);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				// 파일 경로가 존재하지 않을 경우 파일 경로 생성
				if(!thumb_f.exists()) {
					thumb_f.mkdirs();
				}
				// 썸네일 파일 생성 끝
		
				// 이미지 파일 생성
				File img_f = new File(realPath, pro_img); 
				try {
					img_file.transferTo(img_f);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				// 파일 경로가 존재하지 않을 경우 파일 경로 생성
				if(!img_f.exists()) {
					img_f.mkdirs();
				}
				// 이미지 파일 생성 끝
				
				
				int updateCount  = service.ProdUpdatePro(product);
				return "redirect:/ProductModifyForm.pd?pro_code="+product.getPro_code() 
				+ "&msg="+"1";
	
		}	

	//상품 상세정보 삭제 진행(delete)
	@PostMapping(value = "/ProdDeletePro")
		public String prodDetailDeletePro(
				@ModelAttribute ProductVO product,
				Model model, HttpSession session){
		
			int deleteCount  = service.ProdDeletePro(product);
			return "redirect:/ProductModifyForm.pd?pro_code="+product.getPro_code() 
			+ "&msg="+"2";
	
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
	
	//상품 상세정보 
	@GetMapping(value = "/ProductDetail.pd")
	public String detail(
			@RequestParam(defaultValue = "1") int pro_code, 
			Model model) {
		
	// Service 객체의 getProductDetail() 메서드를 호출하여 게시물 상세 정보 조회
	// => 파라미터 : 상품번호  리턴타입 : ProductVO(product)
		ProductVO product = service.getProductDetail(pro_code);
		
		// Model 객체에 ProductVO 객체 추가
		model.addAttribute("product", product);
			
		return "product/list_detail";
	}	
}













