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
	@GetMapping(value = "/MemberJoinResult.me")
	public String joinResult() {
		return "member/member_join_result"; // member_join_result.jsp 로 포워딩
	}
	
	// 로그인 페이지
	@GetMapping(value = "/MemberLoginForm.me")
	public String login() {
		return "member/login"; // login.jsp 로 포워딩
	}

	// 로그인 프로
	@PostMapping(value = "/MemberLoginPro.me")
	public String loginPro(@ModelAttribute MemberVO member, Model model, HttpSession session) {
		
		// 비밀번호 암호화 !!DB에 비밀번호 재설정 필요(해싱, 솔팅 다름)!!
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder(); // 객체 생성
//		String passwd = service.getPasswd(member.getId());
		
		
		
		return "";
	}

	
}













