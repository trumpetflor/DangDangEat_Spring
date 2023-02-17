package com.thisteam.dangdangeat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thisteam.dangdangeat.service.AdminService;
import com.thisteam.dangdangeat.service.OrderService;
import com.thisteam.dangdangeat.service.ProductService;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.PageInfo;
import com.thisteam.dangdangeat.vo.ProductVO;


@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	@Autowired
	private OrderService odService;
	@Autowired
	private ProductService poService;

	// ============================================== yeram ================================================
	
	// 관리자 메인페이지
	@GetMapping(value = "/AdminMain")
	public String main() {
		return "admin/admin_main";
	}

	// ============================================== sewon ================================================
	
	//top에 있는 about 페이지로 이동 
	@GetMapping(value = "/About.ad")
	public String insert() {
		return "main/about";
	}

	//관리자페이지(상품관리)
	@GetMapping(value = "/AdminProductList")
	public String ProductList(
			@RequestParam(defaultValue = "") String category,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "1") int pageNum,
			Model model) {
	
		// ---------------------------------------------------------------------------
		// 페이징 처리를 위한 변수 선언
		int listLimit = -1; // 전체 검색을 위해 -1로 값을 설정함! 
		int startRow = (pageNum - 1) * listLimit; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
		
//		System.out.println(startRow);
		
		// Service 객체의 getProductList() 메서드를 호출하여 게시물 목록 조회
		// => 파라미터 : 검색어, 카테고리, 시작행번호, 목록갯수   리턴타입 : List<ProductBean>(ProductList)
		System.out.println("keyword==="+keyword);
		System.out.println("category==="+category);
		System.out.println("startRow==="+startRow);
		System.out.println("listLimit==="+listLimit);
		List<ProductVO> productList = poService.getProductList(keyword, category, startRow, listLimit);

		
		// ---------------------------------------------------------------------------
		// 페이징 처리 
		//1. 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
		//=> 파라미터 : 검색타입, 검색어   리턴타입 : int(listCount)
		int listCount = poService.getProductListCount(keyword,category);
		
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
		model.addAttribute("category", category);
		
		return "admin/admin_product";
		
	}
	

// ============================================== jakyoung ================================================
	
	@GetMapping(value = "AdminMemberList")
	public String memberList(
			Model model,
			HttpSession session) {
		
		String id = (String)session.getAttribute("sId");
		
		if(id == null || id.equals("") || !id.equals("admin")) { // 세션 아이디가 null 이거나 "" 이거나 admin 이 아닐 경우
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "redirect:/";
		} else { // 세션 아이디 있을 경우
			// 회원 목록 조회
			List<MemberVO> memberList = service.getMemberList();
			
			model.addAttribute("memberList", memberList); // Model 객체에 회원 목록 저장
			
			return "admin/admin_member_list"; // 관리자 회원 목록 페이지 포워딩
		}
	}
	
	// 회원 상태 수정
	@GetMapping(value = "AdminMemberUpdate")
	public String memberUpdate(
		@RequestParam String id,
		@RequestParam String status,
		Model model,
		HttpSession session
			) {
		
		String sid = (String)session.getAttribute("sId");
		
		if(id == null || sid.equals("") || !sid.equals("admin")) { // 세션 아이디가 null 이거나 "" 이거나 admin 이 아닐 경우
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "redirect:/";
		} else { // 세션 아이디 있을 경우
			// MemberVO 객체 생성 및 파라미터 저장
			MemberVO member = new MemberVO();
			member.setMember_id(id);
			member.setMember_status(status);
			
			// 회원 상태 수정
			int updateCount = service.memberUpdate(member);
			
			if(updateCount > 0) { // 회원 정보 수정 성공
				return "redirect:AdminMemberList";
			} else {
				model.addAttribute("msg", "회원 정보 수정 실패");
				return "fail_back";
			}
			
		}
		
	}
	
   // 회원 삭제
   @GetMapping(value = "AdminMemberDelete")
   public String memberDelete(
         @RequestParam String id,
         Model model,
         HttpSession session
            ) {
         
         String sid = (String)session.getAttribute("sId");
         
         if(id == null || sid.equals("") || !sid.equals("admin")) { // 세션 아이디가 null 이거나 "" 이거나 admin 이 아닐 경우
            model.addAttribute("msg", "잘못된 접근입니다!");
            return "redirect:/";
         } else { // 세션 아이디 있을 경우
            // MemberVO 객체 생성 및 파라미터 저장
            MemberVO member = new MemberVO();
            member.setMember_id(id);
            
            // 회원 삭제 수행
            int deleteCount = 0;
            try {
               deleteCount = service.memberDelete(member);
            } catch (DataIntegrityViolationException e) {
               System.out.println("java.sql.SQLIntegrityConstraintViolationException"
                     + ": Cannot delete or update a parent row"
                     + ": a foreign key constraint fails"
                     + " (`class5_220823_team2`.`auth`, CONSTRAINT `auth_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `member` (`member_id`))");
               e.printStackTrace();
            }
            
            if(deleteCount > 0) { // 회원 삭제 성공
               return "redirect:AdminMemberList";
            } else {
               model.addAttribute("msg", "회원 삭제 실패!");
               return "fail_back";
            }
            
         }
	
   }


	
  // ============================================== hawon ================================================
  
 	//관리자 쿠폰등록 view페이지
	@GetMapping(value = "AdminCouponRegister")
	public String CouponRegister() {
		return "admin/admin_coupon";
	}
  
  
	@GetMapping(value = "AdminCouponList")
	public String AdminCouponList(Model model) {
		
		
		
		// admin_couponList.jsp에서 보여질 쿠폰 리스트 JSONArray형태로 request객체에 저장
//		JSONArray CouponList =  odService.SelectCouponList();
//		System.out.println(CouponList +" - "+this.getClass());
//		model.setAttribute("CouponList", CouponList);
		
		// 쿠폰 개수 리턴받음
		int couponCount = odService.getCouponTotalAmount();
//		model.addAttribute("couponCount", CouponList);
		


		return "admi/admin_couponList";
	}
  
  
  

}





