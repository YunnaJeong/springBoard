package com.jyn.springboard.service.comment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyn.springboard.service.comment.CommentService;
import com.jyn.springboard.vo.CommentVO;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public void insertComment(CommentVO commentVO) {
		commentDAO.insertComment(commentVO);
	}
	
	
	@Override
	public List<CommentVO> getCommentList(int boardNo) {
		return commentDAO.getCommentList(boardNo);
	}

	@Override
	public void updateComment(CommentVO commentVO) {
		commentDAO.updateComment(commentVO);
	}
	
	@Override
	public void deleteComment(int commentNo) {
		commentDAO.deleteComment(commentNo);
	}

	
	
}
