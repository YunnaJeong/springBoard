package com.jyn.springboard.service.member.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jyn.springboard.vo.MemberVO;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int selectMemberById(String memberId) {
		return mybatis.selectOne("MemberDAO.selectMemberById", memberId);
	}
	
	public int insertMember(MemberVO memberVO) {
		return mybatis.insert("MemberDAO.insertMember", memberVO);
	}
	
	public MemberVO login(MemberVO memberVO) {
		return mybatis.selectOne("MemberDAO.login", memberVO);
	}	
	
	public void myPage(MemberVO memberVO) {
		mybatis.selectOne("MemberVO.selectUser", memberVO);
		}
	
	public void updateMember(MemberVO memberVO) {
		mybatis.update("MemberDAO.updateMember", memberVO);

	}
}
