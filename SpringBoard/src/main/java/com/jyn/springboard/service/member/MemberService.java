package com.jyn.springboard.service.member;


import com.jyn.springboard.vo.MemberVO;


public interface MemberService {
	
	
	//id중복체크
	int selectMemberById(String memberId);
	
	//회원가입
	int insertMember(MemberVO memberVO);
	
	//로그인
	MemberVO login(MemberVO memberVO);
	
	//회원정보 이동
	void myPage(MemberVO memberVO);
	
	//회원정보 수정
	void updateMember(MemberVO memberVO);
		
}
