package com.thisteam.dangdangeat.controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thisteam.dangdangeat.service.AdminService;
import com.thisteam.dangdangeat.service.MemberService;
import com.thisteam.dangdangeat.vo.AuthVO;
import com.thisteam.dangdangeat.vo.MemberVO;
import com.thisteam.dangdangeat.vo.Member_Mypage_ViewVO;
import com.thisteam.dangdangeat.vo.ReviewVO;
import com.thisteam.dangdangeat.vo.Review_product_viewVO;



@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private AdminService service_ad;



	//----------------------- sangwoo ---------------------// 
  
	// 회원가입 페이지
	@GetMapping(value = "/MemberJoinForm")
	public String join() {
		return "member/memberJoin";
	}

	// 아이디 중복 체크
	@GetMapping(value = "/MemberIdCheck")
	@ResponseBody
	public void idCheck(@RequestParam("id") String id, HttpServletResponse response) {
		System.out.println("아이디 : " + id);
		String result = "";
		
		result = service.idCheck(id);
		try {
			if(result != null) {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("true");
			} else {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 이메일 중복 체크
	@GetMapping(value = "/MemberEmailCheck")
	@ResponseBody
	public void emailCheck(@RequestParam("email") String email, HttpServletResponse response) {
		System.out.println("이메일 : " + email);
		String result = "";
		
		result = service.emailCheck(email);
		try {
			if(result != null) {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("true");
			} else {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// 회원가입 프로
	@PostMapping(value = "/MemberJoin")
	public String joinPro(@ModelAttribute MemberVO member,Model model,
							@ModelAttribute AuthVO auth,
							HttpServletRequest request,
							HttpSession session,
							@Param("year") String year,
							@Param("month") String month,
							@Param("day") String day) {
		System.out.println(member);
		
		// 날짜 데이터 String -> Date 변환과 동시에 합치기
		if(year != null && !year.equals("")) {
			member.setMember_birth(Date.valueOf(year + "-" + month + "-" + day));
		}
		
		// 패스워드 와 이메일 해싱
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String securePass = passwordEncoder.encode(member.getMember_pass());
		String secureEmail = passwordEncoder.encode(member.getMember_email());
		System.out.println("패스워드 : " + securePass + " 이메일 : " + secureEmail);
		member.setMember_pass(securePass);
		auth.setAuth_code(secureEmail);
		auth.setAuth_id(member.getMember_id());
		

		String host = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
//		System.out.println("1번 : "+ request.getScheme());
//		System.out.println("2번 : " + request.getServerName());
//		System.out.println("3번 : " + request.getServerPort());
//		System.out.println("4번 : " + request.getContextPath());

		//=====추가 시작(hawon)=====
		//실행 위치: 회원가입이 완료 되었을때 실행
		//자동발급쿠폰이 있는 경우 회원가입시 자동 발급(hawon)
		int couponInsertCount = service_ad.autoCouponInsertForNewMem(member.getMember_id());
		//==========추가 끝(hawon)==========

		
		// 이메일 발송 
		MimeMessage mail = mailSender.createMimeMessage();
		String mailContent = "<h1>[ DangDangEat 이메일인증 ]</h1><br>"
								+ "<p>링크 클릭 시 이메일 인증이 완료됩니다.</p>"
								+ "<a href='"+host+"/MemberMailAuth?auth_id="+auth.getAuth_id()+"&auth_code="+auth.getAuth_code()+"'>링크를 눌러 인증완료하개!</a>";
		
		int joinResult = service.memberJoinPro(member);
		
		if(joinResult > 0) {
			try {
				mail.setSubject("댕댕잇 회원가입 인증","utf-8");
				mail.setText(mailContent,"utf-8","html");
				mail.addRecipient(Message.RecipientType.TO, new InternetAddress(member.getMember_email()));
				mailSender.send(mail);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MailException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			service.insertAuthMail(auth);
		} else {
			model.addAttribute("msg", "회원가입 실패!");
			return "fail_back";
		}
		return "redirect:MemberJoinResult";
	}
	
	// 회원가입 결과
	@GetMapping(value = "/MemberJoinResult")
	public String joinResult() {
		return "member/member_join_result"; // member_join_result.jsp 로 포워딩
	}
	
	// 메일 인증 확인 및 업데이트
	@GetMapping(value = "/MemberMailAuth")
	public String MailAuthPro(@Param("auth_id") String auth_id,
								@Param("auth_code") String auth_code,
								@ModelAttribute AuthVO auth,
								Model model) {
		auth.setAuth_id(auth_id);
		auth.setAuth_code(auth_code);
//		System.out.println("확인용 : " + auth);
		int mailAuthCount = service.checkAuthMail(auth);
		if(mailAuthCount > 0) {
			service.updateMemberAuth(auth);
		} else {
			model.addAttribute("msg", "인증 실패!");
			return "redirect:/"; 
		}
		return "redirect:/MemberLoginForm";
	}
	
	//----------------------- sangwoo ---------------------// 
	
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
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "fail_back";
		} else { // 세션 아이디 있을 경우
			// 비밀번호 암호화
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
	@GetMapping(value = "/MemberInfo")
	public String memberInfo(
			Model model, 
			HttpSession session) {

		String id = (String)session.getAttribute("sId");

		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 때 쫓아내기
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "fail_back";
		} else { // 세션 아이디 있을 경우
			MemberVO member = service.getMemberInfo(id); // 회원 정보 조회

			model.addAttribute("member", member);

			return "member/member_info";
		}

	}

	// 회원 정보 수정
	@PostMapping(value = "/MemberModifyPro")
	public String memberModify(
			@ModelAttribute MemberVO member,
			Model model,
			@RequestParam String newPass,
			HttpSession session) {

		String id = (String)session.getAttribute("sId");

		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 때 쫓아내기
			model.addAttribute("msg", "잘못된 접근입니다.");
			return "fail_back";
		} else { // 세션 아이디 있을 경우
			
			member.setMember_id(id); // MemberVO 객체에 세션 아이디 저장
			
			// 비밀번호 암호화
			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder(); // 객체 생성
			String pass = service.getPass(id); // id 에 해당하는 암호화 비밀번호 가져오기

			// 암호화 비밀번호 비교
			if(pass == null || !passwdEncoder.matches(member.getMember_pass(), pass)) { // 불일치(id 에 해당하는 pass 없거나 pass 맞지 X
				model.addAttribute("msg", "비밀번호가 틀렸댕!");
				return "fail_back"; // "fail_back.jsp" 포워딩
			} else { // 비밀번호 일치
				
				if(newPass != null && !newPass.equals("")) { // 신규 비밀번호 있을 경우 암호화
					newPass = passwdEncoder.encode(newPass);
					member.setMember_pass(newPass); // MemberVO 객체에 신규 비밀번호 저장
				} else { // 없을 경우 암호화 된 기존 비밀번호
					member.setMember_pass(pass);
				}
				
				int updateCount = service.modifyMemberInfo(member); // 회원 정보 수정 작업 수행
				
				if(updateCount > 0) { // 수정 성공
					// 회원 정보 페이지 리다이렉트
					return "redirect:MemberInfo";
				} else { // 수정 실패
					model.addAttribute("msg", "회원 정보 수정 실패");
					return "fail_back";
				}
				
			}
			
		}

	}
	
	// 마이페이지
	@GetMapping(value = "MyPage")
	public String myPage(
			Model model, 
			HttpSession session) {
		
		String id = (String)session.getAttribute("sId");

		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 때 쫓아내기
			model.addAttribute("msg", "로그인이 필요합니다");
			model.addAttribute("url", "/MemberLoginForm");
			return "redirect";
		} else { // 세션 아이디 있을 경우
			MemberVO member = service.getMemberInfo(id); // 회원 정보 조회
			model.addAttribute("member", member); // Model 객체에 회원 정보 저장

			// 구문 따로따로
//			int memberHistoryCount[] = service.getMemberHistoryCount(id);
//			int memberOrderCount = service.getMemberOrderCount(id);
//			int memberCouponCount = service.getMemberCouponCount(id);
//			int memberReviewCount = service.getMemberReviewCount(id);
//			int memberQnaCount = service.getMemberQnaCount(id);
//			
//			model.addAttribute("order_count", memberOrderCount);
//			model.addAttribute("coupon_count", memberCouponCount);
//			model.addAttribute("review_count", memberReviewCount);
//			model.addAttribute("qna_count", memberQnaCount);
			
			// view 테이블을 이용한 멤버 마이페이지 조회(주문, 쿠폰, 리뷰, 질문 수)
			Member_Mypage_ViewVO mypageView = service.getMemberMypage(id);
			
			model.addAttribute("mypageView", mypageView);
			
			return "member/mypage";
		}
		
	}
	
	// 회원 탈퇴
	@GetMapping(value = "MemberWithdrawForm")
	public String withdraw(
			Model model,
			HttpSession session
			) {
		
		String id = (String)session.getAttribute("sId");

		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 때 쫓아내기
			model.addAttribute("msg", "로그인이 필요합니다");
			model.addAttribute("url", "/MemberLoginForm");
			return "redirect";
		} else { // 세션 아이디 있을 경우
			return "member/withdraw"; // 회원 탈퇴 폼으로 포워딩
		}
		
	}
	
	// 회원 탈퇴 (상태 변경)
	@PostMapping(value = "MemberWithdrawPro")
	public String withdrawPro(
			@ModelAttribute MemberVO member,
			Model model,
			HttpSession session
			) {
		
		String id = (String)session.getAttribute("sId");
		
		if(id == null || id.equals("")) { // 세션 아이디가 null 이거나 "" 일 경우
			model.addAttribute("msg", "잘못된 접근입니다");
			return "fail_back";
		} else { // 세션 아이디 있을 경우
			// 비밀번호 암호화
			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder(); // 객체 생성
			String pass = service.getPass(id); // id 에 해당하는 암호화 비밀번호 가져오기

			// 암호화 비밀번호 비교
			if(pass == null || !passwdEncoder.matches(member.getMember_pass(), pass)) { // 불일치(id 에 해당하는 pass 없거나 pass 맞지 X)
				model.addAttribute("msg", "비밀번호가 틀렸댕!");
				return "fail_back"; // "fail_back.jsp" 포워딩
			} else { // 비밀번호 일치
				
				member.setMember_id(id); // MemberVO 객체 아이디 저장
				member.setMember_status("N"); // MemberVO 객체 회원 상태 "N" 으로 설정
				
				int updateCount = service.memberWithdraw(member); // 회원 탈퇴 작업 수행
				
				if(updateCount > 0) { // 탈퇴 작업 성공
					session.invalidate(); // 모든 세션 초기화
					return "redirect:MemberWithdrawResult"; // 탈퇴 결과 페이지 리다이렉트
				} else { // 탈퇴 작업 실패
					model.addAttribute("msg", "회원 탈퇴 실패했댕ㅠㅠ");
					return "fail_back";
				}
				
			}
		}
		
	}
	
	// 회원 탈퇴 결과
	@GetMapping(value = "MemberWithdrawResult")
	public String withdrawResult() {
		return "member/withdraw_result"; // 회원 탈퇴 결과 페이지 포워딩
	}
	
	// 회원 정보 페이지 이메일 인증 상태 변경
	@ResponseBody
	@PostMapping(value = "MemberEmailUpdate")
	public void emailUpdate(
			@RequestParam String id,
			@RequestParam String email,
			Model model,
			HttpSession session,
			HttpServletResponse response
			) {
		
		String sId = (String)session.getAttribute("sId");
		
		if(sId == null || sId.equals("") || !sId.equals(id)) {
			model.addAttribute("msg", "로그인이 필요한 페이지입니다.");
			// redirect 필요
		} else { // 세션 아이디 있을 경우
			
			int updateCount = service.updateMemberEmail(id, email);
			
			try {
				if(updateCount > 0) { // 변경 성공
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("이메일이 변경되었습니다.");
					
				} else { // 실패 시
					
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("이미 사용 중이거나 탈퇴한 회원의 이메일입니다.");
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
	}
	
	// 회원 정보 페이지 이메일 인증
	@ResponseBody
	@PostMapping(value = "MemberEmailAuth")
	public void emailAuth(
			@RequestParam String id,
			@RequestParam String email,
			Model model,
			HttpSession session,
			HttpServletResponse response
			) {
		
		String sId = (String)session.getAttribute("sId");
		
		try {
			if(sId == null || sId.equals("") || !sId.equals(id)) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("false");
//			out.println("<script>");
//			out.println("alert('잘못된 접근입니다.')");
//			out.println("history.back()");
//			out.println("</script>");
			} else { // 세션 아이디 있을 경우
				// 인증 이메일 발송 
				MimeMessage mail = mailSender.createMimeMessage();
				String mailContent = "<h1>[이메일인증]</h1><br>"
										+ "<p>링크 클릭 시 이메일 인증이 완료됩니다.</p>"
										+ "<a href='http://itwillbs4.cafe24.com/DangDangEat/MailCheck'>클릭</a>";
				
				mail.setSubject("댕댕잇 이메일 인증메일입니다.","utf-8");
				mail.setText(mailContent,"utf-8","html");
				mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				mailSender.send(mail);
			}
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 아이디 찾기 페이지
	@GetMapping(value = "MemberFindIdForm")
	public String findId() {
		return "member/find_id";
	}

	// 아이디 찾기 결과
	@PostMapping(value = "MemberFindIdResult")
	public String findIdPro(
			@ModelAttribute MemberVO member
			, Model model
//			, HttpServletResponse response
			) {
		
//		System.out.println(member);
		
		// 아이디 조회
		MemberVO foundMember = service.findMemberId(member);
		
//		System.out.println(foundMember);
		
		if(foundMember != null) { // 조회된 회원이 존재할 경우 (아이디 있음)
			model.addAttribute("member", foundMember);
			return "member/find_id_result";
		} else { // 회원이 존재하지 않을 경우 (아이디 없음)
			model.addAttribute("msg", "입력하신 정보로 가입된 회원이 존재하지 않습니다.");
			return "fail_back";
		}
		
	}
	
	// 비밀번호 찾기 페이지
	@GetMapping(value = "MemberFindPassForm")
	public String findPass() {
		return "member/find_pass";
	}
	
	// 비밀번호
	@PostMapping(value = "MemberFindPassPro")
	public String findPassPro(
			@ModelAttribute MemberVO member
			, Model model
			, HttpServletResponse response
			) {
		
		System.out.println(member);
		
		// 회원 아이디, 이름, 이메일과 일치하는 회원 확인
		MemberVO foundMember = service.findMemberPass(member);
		
		if(foundMember != null) { // 일치하는 회원이 존재할 경우
			
			// 임시 비밀번호 발급
			// 인증코드에 사용될 문자를 배열로 모두 저장
			char[] codeTable = {
					'A', 'B', 'C', 'D', 'C', 'F', 'G', 'H', 'I', 'J', 
					'K', 'L', 'N', 'M', 'O', 'P', 'Q', 'R', 'S', 'T', 
					'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
					'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
					'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
					'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
					'8', '9', '!', '@', '$'
			};
			
			Random r = new Random();
			String randomPass = "";
			int codeLength = 10;
			
			// 원하는 코드 길이만큼 for문을 사용하여 반복문으로 처리
			for(int i = 1; i <= codeLength; i++) {
				// 배열 크기를 난수의 범위로 지정하여 난수 생성
				int index = r.nextInt(codeTable.length);
				
				// 생성된 난수를 배열 인덱스로 활용하여 1개의 코드 가져와서 문자열 결합
				randomPass += codeTable[index];
				
			}
			
			System.out.println("임시 비밀번호 : " + randomPass);
			
			// 패스워드 솔팅 및 임시 비밀번호로 회원 정보 변경
			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder(); // 객체 생성
			foundMember.setMember_pass(passwdEncoder.encode(randomPass)); // MemberVO 객체에 암호화된 임시 비밀번호 저장
			
			System.out.println(foundMember);
			
			int updateCount = service.updateMemberPass(foundMember); // 임시 비밀번호 변경
			
			if(updateCount > 0) { // 임시 비밀번호 변경 성공 시
				
				try {
					// 임시 비밀번호 이메일 발송
					MimeMessage mail = mailSender.createMimeMessage();
					String mailContent = "<b>" + foundMember.getMember_name() + "</b> 님의 비밀번호가 요청되었습니다.<br>"
							+ "회원 아이디 : " + foundMember.getMember_id() + "<br>"
							+ "임시 비밀번호 : <strong>" + randomPass + "</strong><br>"
							+ "비밀번호는 정보관리시스템에서 매우 중요하게 사용되므로 안전하게 관리하시기 바랍니다.<br>"
							+ "해당 비밀번호로 로그인 후 비밀번호 재설정하세요.<br>"
							+ "<font color='#ff0000'>본 메일은 발신 전용이므로 회신이 되지 않습니다.</font><br>"
							+ "<a href='http://itwillbs4.cafe24.com/DangDangEat/MemberLoginForm'>댕댕잇 로그인</a>";
					
					mail.setSubject("댕댕잇 임시 비밀번호입니다.","utf-8");
					mail.setText(mailContent,"utf-8","html");
					mail.addRecipient(Message.RecipientType.TO, new InternetAddress(foundMember.getMember_email()));
					mailSender.send(mail);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				model.addAttribute("msg", "회원 이메일로 임시 비밀번호가 발송되었습니다.");
				model.addAttribute("url", "/MemberLoginForm");
				return "redirect";
				
			} else { // 변경 실패 시
				model.addAttribute("msg", "회원 정보를 다시 입력바랍니다.");
				return "fail_back";
			}
			
		} else { // 일치하는 회원이 존재하지 않을 경우
			model.addAttribute("msg", "입력하신 정보로 가입된 회원이 존재하지 않습니다.");
			return "fail_back";
		}
		
	}
//====================================hawon 마이페이지_리뷰=============================================
	@GetMapping(value = "/GetMyReviewList.ajax")
	public String getReview(Model model,
							HttpSession session) {
		String id = (String)session.getAttribute("sId");
		
		//내가 쓴 리뷰 가져오기
		List<Review_product_viewVO> reviewList = service.getMyReview(id);
		
		//줄바꿈처리할 후 저장할 객체 선언
		List<Review_product_viewVO> convertedReviewList = new ArrayList<Review_product_viewVO>();
		
		//줄바꿈처리
		for(Review_product_viewVO review : reviewList) {
			review.setReview_content(review.getReview_content().replace("\r\n", "<br>")); // 출력 시 줄바꿈 처리
			convertedReviewList.add(review);
		}
		model.addAttribute("reviewList",convertedReviewList);
		
		
		return "member/mypage_reviewAjax";
	}

}













