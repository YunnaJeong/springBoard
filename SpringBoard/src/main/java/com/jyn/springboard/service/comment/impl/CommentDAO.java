package com.jyn.springboard.service.comment.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jyn.springboard.vo.CommentVO;

@Repository
public class CommentDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertComment(CommentVO commentVO) {
		mybatis.insert("CommentDAO.insertComment", commentVO);
	}
	

	public List<CommentVO> getCommentList(int boardNo) {		
		return mybatis.selectList("CommentDAO.getCommentList", boardNo);
	}
	
	public void updateComment(CommentVO commentVO) {
		mybatis.update("CommentDAO.updateComment", commentVO);
	}
	
	public void deleteComment(int commentNo) {
		mybatis.delete("CommentDAO.deleteComment", commentNo);
	}
	


	
	
}
