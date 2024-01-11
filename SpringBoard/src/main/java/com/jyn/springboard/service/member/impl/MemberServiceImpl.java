package com.jyn.springboard.service.member.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyn.springboard.service.member.MemberService;
import com.jyn.springboard.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public int selectMemberById(String memberId) {
		return memberDAO.selectMemberById(memberId);
	}
	
	@Override
	public int insertMember(MemberVO memberVO) {
		return memberDAO.insertMember(memberVO);
	}
	
	
	@Override
	public MemberVO login(MemberVO memberVO) {
		return memberDAO.login(memberVO);
	}

	
	@Override
	public void myPage(MemberVO memberVO) {
		memberDAO.myPage(memberVO);
	}
	
	
	@Override
	public void updateMember(MemberVO memberVO) {
		memberDAO.updateMember(memberVO);
	}
	
	
	
	
	
	
	
	
	
}
