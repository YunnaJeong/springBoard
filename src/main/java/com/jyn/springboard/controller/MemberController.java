package com.jyn.springboard.controller;


import javax.servlet.http.HttpSession;

// import org.mybatis.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyn.springboard.service.member.MemberService;
import com.jyn.springboard.vo.MemberVO;

@Controller
@RequestMapping("/member")

public class MemberController {
	
	//private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	// private final Logger logger = (Logger)LogManager.getLogger(this.getClass());
	//Logger logger = Logger.getLogger("logger");
	private  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private MemberService memberService;
	
	//해당 화면으로 이동
	@GetMapping("/join.do")
	public String joinView() {
		return "member/join";
	}
	
	
	//회원가입 진행
	@SuppressWarnings("finally")
	@PostMapping(value="/join.do", produces="application/text; charset=UTF-8")
	public String insertMember(MemberVO memberVO, Model model) {
		
		String resMsg ="회원가입에 성공했습니다. 로그인해주세요."; 
		String returnUrl ="member/login";
		try {
			int insertCnt = memberService.insertMember(memberVO);
			logger.info("insertCnt:"+insertCnt);
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
			resMsg = "회원가입에 실패했습니다. 문의해주세요.";
			returnUrl= "member/join";
		} finally {
			model.addAttribute("joinMsg", resMsg);
			return returnUrl;
		}
	}
	
	
//	@PostMapping(value="/join.do", produces="application/text; charset=UTF-8")
//	public String insertMember(MemberVO memberVO, Model model) {
//		
//		String resMsg ="회원가입에 성공했습니다. 로그인해주세요."; 
//		try {
//			memberService.insertMember(memberVO);
//		} catch(Exception e) {
//			resMsg = "회원가입에 실패했습니다. 문의해주세요.";			
//			return "member/join";
//			
//		} 
//		model.addAttribute("joinMsg", resMsg);
//		return "member/login";    // -> finally 쓰라고 하셨는데 어떻게 해결해야할지...?
//	}
	

	
	@PostMapping("/idCheck.do")
	@ResponseBody  // view를 리턴하지 않고 데이터를 리턴할수있다
	public String idCheck(MemberVO memberVO) {

		String returnStr = "";
		
		int selectedIdCnt = memberService.selectMemberById(memberVO.getMemberId());
		
		if(selectedIdCnt > 0) {
			returnStr = "duplicatedId";
		} else {
			returnStr = "idOk";
		}
		
		return returnStr;
	}

	
	//로그인 화면으로 이동
	@GetMapping("/login.do")
	public String loginView() {
		return "member/login";
	}
	
	//로그인 처리
		@PostMapping("/login.do")
		@ResponseBody
		
		public String login(MemberVO memberVO, HttpSession session) {
			
			//1. 아이디 체크
			int idCheck = memberService.selectMemberById(memberVO.getMemberId());
			
			if(idCheck < 1) {
				return "idFail";
			} else {
				MemberVO loginUser = memberService.login(memberVO);
				
				//2. 비밀번호 체크
				if(loginUser == null) {
					return "pwFail";
				}
				
				//3. 로그인 성공
				
				session.setAttribute("loginUser", loginUser);
				return "loginSuccess";
			}
		}
	    
	    // 회원정보 조회
		@GetMapping("/mypage.do")
		public String myInfoView(HttpSession session) {
			return "member/mypage";
		}
		
		@PostMapping("/mypage.do")
		public String myPage(MemberVO memberVO) {
			memberService.myPage(memberVO);
			return "redirect:/member/mypage";
		}
		
		
		// 회원정보 수정
		@PostMapping("/updateMember.do")
		
		public String updateMember(HttpSession session, MemberVO memberVO) {
			session.invalidate();
			memberService.updateMember(memberVO);

			return "redirect:/member/login.do";
		}

		
		
	
	
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/index.jsp";
	}

	
	
}
