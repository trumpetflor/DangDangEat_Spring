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
import com.thisteam.dangdangeat.vo.MemberVO;


@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	private OrderService odService;

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





