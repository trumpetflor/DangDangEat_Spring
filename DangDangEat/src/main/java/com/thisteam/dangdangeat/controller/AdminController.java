package com.thisteam.dangdangeat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thisteam.dangdangeat.service.AdminService;
import com.thisteam.dangdangeat.vo.MemberVO;

@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	
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
				
				// 회원 삭제
				int deleteCount = service.memberDelete(member);
				
				if(deleteCount > 0) { // 회원 삭제 성공
					return "redirect:AdminMemberList";
				} else {
					model.addAttribute("msg", "회원 삭제 실패");
					return "fail_back";
				}
				
			}
			
		}
	
	
}













