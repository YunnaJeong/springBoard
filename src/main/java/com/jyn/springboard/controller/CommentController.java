package com.jyn.springboard.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyn.springboard.service.comment.CommentService;
import com.jyn.springboard.vo.CommentVO;

@Controller
@RequestMapping("/comment")
public class CommentController {
   @Autowired
   private CommentService commentService;
   
    //댓글 목록   
//   @RequestMapping("/getCommentList.do")
//   public String getBoardList(Model model) {
//
//      List<CommentVO> CommentList = commentService.getCommentList();
//
//      model.addAttribute("CommentList", CommentList);
//
//      return "comment/getCommentList";
//   }
//   

   
   
   
   //댓글 등록
   @ResponseBody
   @PostMapping("/insertComment.do")
   public String insertComment(CommentVO commentVO) {
         commentService.insertComment(commentVO);
         
         return "success";
   
   }
   
   //댓글 수정
   @ResponseBody
   @PostMapping("/updateComment.do")
   public String updateComment(CommentVO commentVO) {


      commentService.updateComment(commentVO);
      
      return "success";
   }
   
   //댓글 삭제
   @ResponseBody
   @RequestMapping("/deleteComment.do")
   public String deleteComment(@RequestParam("commentNo") int commentNo) {

      commentService.deleteComment(commentNo);
      
      return "success";
      }
   

   
   


   
   
}