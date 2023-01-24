package com.thisteam.dangdangeat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.thisteam.dangdangeat.service.MemberService;
import com.thisteam.dangdangeat.vo.MemberVO;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	
	
	// 회원가입 결과
	@GetMapping(value = "/MemberJoinResult")
	public String joinResult() {
		return "member/member_join_result"; // member_join_result.jsp 로 포워딩
	}
	
	// 로그인 페이지
	@GetMapping(value = "/MemberLoginForm")
	public String login() {
		return "member/login"; // login.jsp 로 포워딩
	}

	// 로그인 프로
	@PostMapping(value = "/MemberLoginPro")
	public String loginPro(@ModelAttribute MemberVO member, Model model, HttpSession session) {
		
//		System.out.println("controller : " + member);
		
		// 비밀번호 암호화 !!DB에 비밀번호 재설정 필요(해싱, 솔팅 다름)!!
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder(); // 객체 생성
		String pass = service.getPass(member.getMember_id()); // id 에 해당하는 암호화 비밀번호 가져오기
		
//		System.out.println(pass);
		
		// getPasswd 의 결과값이 xml 구문 결과 없으면 null 로 오는 건지...? 그면 ""은 비교할 필요 아예 X?
		
		// 암호화 비밀번호 비교
		if(pass == null || !passwdEncoder.matches(member.getMember_pass(), pass)) { // 실패(id 에 해당하는 pass 없거나 pass 맞지 X
			model.addAttribute("msg", "아이디 혹은 비밀번호가 틀렸댕!");
			return "fail_back"; // "fail_back.jsp" 포워딩
		} else { // 성공
			session.setAttribute("sId", member.getMember_id()); // 세션 아이디 저장
			return "redirect:/"; // 메인페이지로 리다이렉트
		}
		
//		return "";
	}
	
	// 로그아웃
	@GetMapping(value = "/MemberLogout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 초기화
		return "redirect:/";
	}
	
	// 비밀번호 확인 페이지
	@GetMapping(value = "/CheckPassForm")
	public String checkPass() {
		return "member/check_pass"; // check_pass.jsp 로 포워딩
	}
	
	// 비밀번호 확인 프로
	@PostMapping(value = "/MemberCheckPro")
	public String memberCheck(
			@ModelAttribute MemberVO member, 
			Model model, 
			HttpSession session) {
		
		String id = (String)session.getAttribute("sId");
		
		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 경우
			model.addAttribute("msg", "로그인이 필요한 페이지입니다.");
			return "MemberLoginForm";
		} else { // 세션 아이디 있을 경우
			// 비밀번호 암호화 !!DB에 비밀번호 재설정 필요(해싱, 솔팅 다름)!!
			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder(); // 객체 생성
			String pass = service.getPass(id); // id 에 해당하는 암호화 비밀번호 가져오기
			
			// 암호화 비밀번호 비교
			if(pass == null || !passwdEncoder.matches(member.getMember_pass(), pass)) { // 불일치(id 에 해당하는 pass 없거나 pass 맞지 X
				model.addAttribute("msg", "비밀번호가 틀렸댕!");
				return "fail_back"; // "fail_back.jsp" 포워딩
			} else { // 비밀번호 일치
				return "redirect:MemberInfo"; // 회원 정보 페이지로 리다이렉트
			}
		}
		
	}
	
	// 회원 정보
	@GetMapping(value = "MemberInfo")
	public String memberInfo(
			Model model, 
			HttpSession session) {
		
		String id = (String)session.getAttribute("sId");
		
		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 때 쫓아내기
			model.addAttribute("msg", "로그인이 필요한 페이지입니다!");
			return "MemberLoginForm";
		} else { // 세션 아이디 있을 경우
			MemberVO member = service.getMemberInfo(id);
			
			model.addAttribute("member", member);
			
			return "member/member_info";
		}
		
	}
	
	
	
	
}













