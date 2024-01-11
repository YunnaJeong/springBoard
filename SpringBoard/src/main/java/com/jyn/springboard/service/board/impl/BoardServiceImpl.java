package com.jyn.springboard.service.board.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyn.springboard.common.FileUtils;
import com.jyn.springboard.service.board.BoardService;
import com.jyn.springboard.vo.BoardFileVO;
import com.jyn.springboard.vo.BoardVO;
import com.jyn.springboard.vo.Criteria;

@Service
public class BoardServiceImpl implements BoardService {
	private  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardDAO boardDAO;
	
	private String attachPath;
	
	@Override
	public void insertBoard(BoardVO boardVO, MultipartFile[] uploadFiles, String attachPath) {
		List<BoardFileVO> fileList = new ArrayList<BoardFileVO>();
		
		//파일업로드 기능 
		if(uploadFiles.length > 0) {
			
			
			//multipartFile 배열에서 파일들을 꺼내 DB 형식에 맞게 변경
			for(int i = 0; i < uploadFiles.length; i++) {
				MultipartFile file = uploadFiles[i];
				
				//getOriginalFileName() : 업로드한 파일의 파일명 
				if(!file.getOriginalFilename().equals("") &&
				   file.getOriginalFilename() != null) {
					BoardFileVO boardFile = new BoardFileVO();
					
					boardFile = FileUtils.parseFileInfo(file, attachPath);
					
					fileList.add(boardFile);
				}
			}
		}
		
		boardDAO.insertBoard(boardVO, fileList);
	}
	
	
	@Override
	public List<BoardVO> selectedBoardList(Map<String, String> paramMap, Criteria cri) {
		return boardDAO.selectedBoardList(paramMap, cri);
	}
	
	@Override
	public BoardVO selectedBoard(int boardNo) {
		return boardDAO.selectedBoard(boardNo);
	}
	
	@Override
	public void updateBoardCnt(int boardNo) {
		boardDAO.updateBoardCnt(boardNo);
	}
	
	@Override
	public void updateBoard(BoardVO boardVO, MultipartFile[] uploadFiles,
		MultipartFile[] changedFiles, String originFiles) {
		
		//JSON String 데이터를 List로 변경
		List<BoardFileVO> originFileList = new ArrayList<BoardFileVO>();
		try {
			originFileList = new ObjectMapper().readValue(originFiles, 
					new TypeReference<List<BoardFileVO>>(){});
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(),e);
		}
		
		//수정되거나 삭제되거나 추가된 파일정보가 담기는 List
		List<BoardFileVO> uFileList = new ArrayList<BoardFileVO>();
		
		
		for(int i = 0; i < originFileList.size(); i++) {
			if(originFileList.get(i).getBoardFileStatus().equals("U")) {
				for(int j = 0; j < changedFiles.length; j++) {
					if(originFileList.get(i).getNewFileNm().equals(changedFiles[j].getOriginalFilename())) {
						BoardFileVO boardFileVO = new BoardFileVO();
						
						MultipartFile file = changedFiles[j];
							
						boardFileVO = FileUtils.parseFileInfo(file, attachPath);
						
						//수정될 내용 추가하는 부분은 따로 작성
						boardFileVO.setBoardNo(originFileList.get(i).getBoardNo());
						boardFileVO.setBoardFileNo(originFileList.get(i).getBoardFileNo());
						boardFileVO.setBoardFileStatus("U");
						
						uFileList.add(boardFileVO);
					}
				}
			} else if(originFileList.get(i).getBoardFileStatus().equals("D")) {
				BoardFileVO boardFileVO = new BoardFileVO();
				
				boardFileVO.setBoardNo(originFileList.get(i).getBoardNo());
				boardFileVO.setBoardFileNo(originFileList.get(i).getBoardFileNo());
				boardFileVO.setBoardFileStatus("D");
				
				//실제 파일 삭제 처리
				File dFile = new File(attachPath + originFileList.get(i).getBoardFileNm());
				dFile.delete();
				
				uFileList.add(boardFileVO);
			}
		}
		
		if(uploadFiles.length > 0) {
			for(int i = 0; i < uploadFiles.length; i++) {
				MultipartFile file = uploadFiles[i];
				
				if(!file.getOriginalFilename().equals("") &&
					file.getOriginalFilename() != null) {
					BoardFileVO boardFileVO = new BoardFileVO();
					
					boardFileVO = FileUtils.parseFileInfo(file, attachPath);
					
					boardFileVO.setBoardNo(boardVO.getBoardNo());
					boardFileVO.setBoardFileStatus("I");
					
					uFileList.add(boardFileVO);
				}
			}
		}
		
		boardDAO.updateBoard(boardVO, uFileList);
	}
	
	@Override
	public void deleteBoard(int boardNo) {
		boardDAO.deleteBoard(boardNo);
	}
	
	@Override
	public int getBoardTotalCnt(Map<String, String> paramMap) {
		return boardDAO.getBoardTotalCnt(paramMap);
	}
	
	
	@Override
	public List<BoardFileVO> getBoardFileList(int boardNo) {
		return boardDAO.getBoardFileList(boardNo);
	}
	
	@Override
	public BoardFileVO getBoardFile(BoardFileVO boardFileVO) {
		return boardDAO.getBoardFile(boardFileVO);
	}


	@Override
	public void deleteBoardFileList(int boardNo) {
		// TODO Auto-generated method stub
		boardDAO.deleteBoardFileList(boardNo);
	}


	@Override
	public void deleteBoardComment(int boardNo) {
		// TODO Auto-generated method stub
		boardDAO.deleteBoardComment(boardNo);
	}
	
	
	
	
	
	
	
	
}
