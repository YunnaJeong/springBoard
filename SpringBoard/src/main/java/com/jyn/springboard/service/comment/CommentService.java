package com.jyn.springboard.service.comment;

import java.util.List;

import com.jyn.springboard.vo.CommentVO;

public interface CommentService {
	
	//댓글 등록
	void insertComment(CommentVO commentVO);
	

	// 목록
	List<CommentVO> getCommentList(int boardNo);
	
	
	//댓글 수정
	void updateComment(CommentVO commentVO);
		
	//댓글 삭제
	void deleteComment(int commentNo);
	 

	

	
	
	
}
