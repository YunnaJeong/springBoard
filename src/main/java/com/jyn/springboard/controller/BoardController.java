package com.jyn.springboard.controller;

//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jyn.springboard.service.board.BoardService;
import com.jyn.springboard.service.comment.CommentService;
import com.jyn.springboard.vo.BoardFileVO;
import com.jyn.springboard.vo.BoardVO;
import com.jyn.springboard.vo.CommentVO;
import com.jyn.springboard.vo.Criteria;
import com.jyn.springboard.vo.MemberVO;
import com.jyn.springboard.vo.PageVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Value("${file.path}")
	private String attachPath;
	
	
	private  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	//게시글 목록 화면으로 이동
	@RequestMapping("/selectedBoardList.do")
	public String selectedBoardList(Model model, @RequestParam Map<String, String> paramMap
			, Criteria cri) {
		List<BoardVO> boardList = boardService.selectedBoardList(paramMap, cri);
		
		model.addAttribute("boardList", boardList);
		
		if(paramMap.get("searchCondition") != null && !paramMap.get("searchCondition").equals(""))
			model.addAttribute("searchCondition", paramMap.get("searchCondition"));
		
		if(paramMap.get("searchKeyword") != null && !paramMap.get("searchKeyword").equals(""))
			model.addAttribute("searchKeyword", paramMap.get("searchKeyword"));
		
		int total = boardService.getBoardTotalCnt(paramMap);
		
		System.out.println(total);
		model.addAttribute("pageVO", new PageVO(cri, total));

		
		return "board/selectedBoardList";
	}
	
	
	

	//게시글 등록 화면으로 이동
	@GetMapping("/insertBoard.do")
	public String insertBoardView(HttpSession session) {
		//로그인한 유저의 정보가 세션에 없을 때 로그인 화면으로 이동
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/member/login.do";
		}
		
		return "board/insertBoard";
	}
	
	
	//게시글 등록
	@PostMapping("/insertBoard.do")
	public String insertBoard(BoardVO boardVO, MultipartFile[] uploadFiles)  {
		//첨부파일에 boardNo를 매핑하기 위해서
		//insert후 boardNo(insert된 게시글의 boardNo)를 리턴하여 사용한다.
		boardService.insertBoard(boardVO, uploadFiles, attachPath);
		
		return "redirect:/board/selectedBoardList.do";
	}
		
	
	
	@RequestMapping("/selectedBoard.do")
	public String selectedBoard(@RequestParam("boardNo") int boardNo, Model model) {
	    // 조회수 증가
	    boardService.updateBoardCnt(boardNo);

	    BoardVO board = boardService.selectedBoard(boardNo);
	    // 첨부파일 리스트 조회
	    List<BoardFileVO> fileList = boardService.getBoardFileList(boardNo);      

	    model.addAttribute("board", board);
	    model.addAttribute("boardFileList", fileList);

	    List<CommentVO> CommentList = commentService.getCommentList(boardNo);
	    model.addAttribute("CommentList", CommentList);

	    return "board/selectedBoard";
	}



	
	@PostMapping("/updateBoard.do")  
	public String updateBoard(BoardVO boardVO, MultipartFile[] uploadFiles,
			MultipartFile[] changedFiles, @RequestParam("originFiles") String originFiles) {
		
		
		boardService.updateBoard(boardVO, uploadFiles, changedFiles, originFiles);
		
		return "redirect:/board/selectedBoard.do?boardNo=" + boardVO.getBoardNo();
	}
	
	//게시글 삭제
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(@RequestParam("boardNo") int boardNo) {
		boardService.deleteBoard(boardNo);
		boardService.deleteBoardComment(boardNo);
		boardService.deleteBoardFileList(boardNo);
		return "redirect:/board/selectedBoardList.do";
	}
	
	
	//게시글 삭제
//	@RequestMapping("/deleteBoard.do")
//	public String deleteBoard(@RequestParam("boardNo") int boardNo) {
//	    // 댓글 삭제
//	    boardService.deleteComment(boardNo);
//
//	    // 파일 삭제
//	    List<BoardFileVO> fileList = boardService.getBoardFileList(boardNo);
//	    for (BoardFileVO file : fileList) {
//	        // 실제 파일 삭제 처리
//	        File dFile = new File(attachPath + file.getBoardFileNm());
//	        dFile.delete();
//	        
//	        // DB에서 파일 정보 삭제
//	        boardService.deleteBoardFile(file.getBoardFileNo());
//	    }
//
//	    // 게시글 삭제
//	    boardService.deleteBoard(boardNo);
//
//	    return "redirect:/board/selectedBoardList.do";
//	}
//	
	
	
	
	//파일 다운로드
	@RequestMapping("/fileDown.do")
	@ResponseBody
	public ResponseEntity<Resource> fileDown(BoardFileVO boardFileVO) {
		BoardFileVO boardFile = boardService.getBoardFile(boardFileVO);
		
		Resource resource = new FileSystemResource(
				boardFile.getBoardFilePath() + boardFile.getBoardFileNm());
		
		String resourceName = resource.getFilename();
		
		HttpHeaders header = new HttpHeaders();
		
		try {
			//패킷 헤더에 다운받을 파일에 대한 정보를 추가
			header.add("Content-Disposition", "attachment; filename=" 
			+ new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	

	
	
}
