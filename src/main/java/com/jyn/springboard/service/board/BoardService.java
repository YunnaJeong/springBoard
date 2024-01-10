package com.jyn.springboard.service.board;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jyn.springboard.vo.BoardFileVO;
import com.jyn.springboard.vo.BoardVO;
import com.jyn.springboard.vo.Criteria;

public interface BoardService {
	//게시글 등록
	void insertBoard(BoardVO boardVO, MultipartFile[] uploadFiles, String attachPath);
	
	//게시글 목록 조회
	List<BoardVO> selectedBoardList(Map<String, String> paramMap, Criteria cri);
	
	//게시글 상세 조회
	BoardVO selectedBoard(int boardNo);
	
	//조회수 증가
	void updateBoardCnt(int boardNo);
	
	//게시글 수정
	void updateBoard(BoardVO boardVO, MultipartFile[] uploadFiles, 
			MultipartFile[] changedFiles, String originFiles);
	
	//게시글 삭제
	void deleteBoard(int boardNo);
	
	//게시글 총 개수 조회
	//검색했을 때는 검색된 게시글의 총 개수를 조회
	int getBoardTotalCnt(Map<String, String> paramMap);
	
	//첨부파일 리스트 조회
	List<BoardFileVO> getBoardFileList(int boardNo);
	
	//게시글 파일정보 한 건 조회
	BoardFileVO getBoardFile(BoardFileVO boardFileVO);
	
	//파일 삭제
	void deleteBoardFileList(int boardNo);
	
	// 댓글 삭제 
	void deleteBoardComment(int boardNo);
	

	
	
	
	
	
	
	
	
	
	
}
