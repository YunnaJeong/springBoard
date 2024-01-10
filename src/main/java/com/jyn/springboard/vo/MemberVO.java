package com.jyn.springboard.vo;

import java.sql.Date;

public class MemberVO {
	private String memberId;
	private String memberPw;
	private String memberNm;
	private String memberTel;
	private String memberMail;
	private String memberRole;
	private Date memberRegdate;

	
	
	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getMemberPw() {
		return memberPw;
	}



	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}


	public String getMemberNm() {
		return memberNm;
	}



	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}


	public String getMemberTel() {
		return memberTel;
	}

	
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}

	
	public String getMemberMail() {
		return memberMail;
	}


	public void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}


	public String getMemberRole() {
		return memberRole;
	}


	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}


	public Date getMemberRegdate() {
		return memberRegdate;
	}

	
	public void setMemberRegdate(Date memberRegdate) {
		this.memberRegdate = memberRegdate;
	}



	@Override
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", memberPw=" + memberPw + ", memberNm=" + memberNm + ", memberMail=" + memberMail
				+ ", memberTel=" + memberTel + ", memberRegdate=" + memberRegdate + ", memberRole=" + memberRole + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
