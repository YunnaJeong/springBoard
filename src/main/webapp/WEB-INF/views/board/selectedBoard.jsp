<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<style>
#reply {
border: 1px solid black;

}

#replytable {
   width: 800px;
}



</style>
<body>
   <jsp:include page="${pageContext.request.contextPath }/header.jsp"></jsp:include>
   <div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
      <h3>게시글 상세</h3>
      <form action="/board/updateBoard.do" method="post" id="updateForm" enctype="multipart/form-data">
      
         <input type="hidden" name="boardNo" id="boardNo" value="${board.boardNo }">
         <input type="hidden" name="originFiles" id="originFiles">
         <table border="1" style="border-collapse: collapse;">
            <tr>
               <td style="background: #0A82FF; width: 70px">제목</td>
               <td style="text-align: left">
               <input type="text" name="boardTitle" id="boardTitle" value="${board.boardTitle}">
               </td>
            </tr>
            <tr>
               <td style="background: #0A82FF;">작성자</td>
               <td style="text-align: left">
               <input type="text" name="boardWriter" id="boardWriter" value="${board.boardWriter}" readonly></td>
            </tr>
            <tr>
               <td style="background: #0A82FF;">내용</td>
               <td style="text-align: left">
               <textarea name="boardContent" id="boardContent" cols="60" rows="10">${board.boardContent}</textarea>
               </td>
            </tr>
            <tr>
               <td style="background: #0A82FF;">작성일</td>
               <td style="text-align: left">
               <fmt:formatDate   value="${board.boardRegdate}" pattern="yyyy-MM-dd" /></td>
            </tr>
            <tr>
               <td style="background: #0A82FF;">조회수</td>
               <td style="text-align: left">${board.boardCnt}</td>
            </tr>
            <tr>
					<td style="background: #0A82FF; width: 70px;">
						파일첨부
					</td>
					<td style="text-align: left;">
						<div id="image_preview">
							<input type="file" id="btnAtt" name="uploadFiles" multiple="multiple">
							<input type="file" id="changedFiles" name="changedFiles"
							style="display: none;" multiple="multiple">
							<p style="color: red; font-size: 0.9rem;">파일 변경 사진 클릭, 파일 다운로드 파일명 클릭.</p>
							<div id="attZone">
								<c:forEach items="${boardFileList }" var="boardFile" varStatus="status">
									<div style="display: inline-block; position: relative; 
									width: 150px; height: 120px; margin: 5px; border: 1px solid #00f; z-index: 1;">
										<input type="hidden" id="boardFileNo${status.index }" name="boardFileNo${status.index }" 
												value="${boardFile.boardFileNo }">
										<input type="hidden" id="boardFileNm${status.index }" name="boardFileNm${status.index }" 
												value="${boardFile.boardFileNm }">
										<input type="file" id="changedFile${boardFile.boardFileNo }" name="changedFile${boardFile.boardFileNo }"
										style="display: none;" onchange="fnGetChangedFileInfo(${boardFile.boardFileNo }, event)">
										<c:if test="${status.last }">
											<input type="hidden" id="boardFileCnt" name="boardFileCnt" value="${status.count }">
										</c:if>
										<c:choose>
											<c:when test="${boardFile.boardFileCate eq 'img' }">
												<img id="img${boardFile.boardFileNo }" src="/uploads/${boardFile.boardFileNm }"
												style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
												class="fileImg" onclick="fnImgChange(${boardFile.boardFileNo})">
											</c:when>
											<c:otherwise>
												<img id="img${boardFile.boardFileNo }" src="/images/defaultFileImg.png"
												style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
												class="fileImg" onclick="fnImgChange(${boardFile.boardFileNo})">
											</c:otherwise>					
										</c:choose>
										<input type="button" class="btnDel" value="x" delFile="${boardFile.boardFileNo }"
										style="width: 30px; height: 30px; position: absolute; right: 0px; bottom: 0px; 
										z-index: 999; background-color: rgba(255, 255, 255, 0.1); color: #f00;"
										onclick="fnImgDel(event)">
										<p id="fileNm${boardFile.boardFileNo }" style="display: inline-block; font-size: 8px;
										cursor: pointer;" onclick="fnFileDown(${boardFile.boardNo}, ${boardFile.boardFileNo})">
											${boardFile.boardOriginFileNm }
										</p>
									</div>
								</c:forEach>
							</div>
						</div>
					</td>
				</tr>
            <tr id="btnWrap">
               <td colspan="2" align="center">
                  <button type="submit" id="btnUpdate">수정</button>
               </td>
            </tr>
         </table>
         
      </form>
      
      <hr/>
      <a href="/board/insertBoard.do">글 등록</a>
      <a   href="/board/deleteBoard.do?boardNo=${board.boardNo}" id="deleteBtn">글 삭제</a>
      <a href="/board/selectedBoardList.do">글 목록</a> <br>
      <br>
      <br>
      
      <br><br>



      <div class="form-wrapper">
         <form action="/comment/insertComment.do" method="post" id="reply" name="reply">

            <p>댓글 작성</p>
            <ul>
               <li>작성자&ensp;<input type="text" name="commentWriter"
                  value="${loginUser.memberId}" readonly>
               </li>
               <li>댓글&emsp;&ensp;<textarea name="commentContent" id="commentContent" cols="35"
                     rows="5"></textarea></li>
               <li><button type="button" id="btnCmtReg">등록</button></li>
            </ul>

         </form>
      </div>



      <br><br><br>

      <div id="replylist">
         <table id="replytable" name="replytable">
            <tr style="background-color: lightgray;">
               <td width="10%">작성자</td>
               <td width="55%">댓글</td>
               <td width="15%">작성일</td>
               <td width="10%">수정</td>
               <td width="10%">삭제</td>
            </tr>

         <c:forEach items="${CommentList}" var="comment">
               <tr>
                  <td>${comment.commentWriter}</td>
                  
                  <c:if test="${loginUser.memberId == comment.commentWriter }">
                 <td>
                        <input type="text" style="border:0 solid black; width:440px;" name="commentWriter" id="${comment.commentNo}"class="commentContent" value="${comment.commentContent}" >
                     </td>
              </c:if>
              <c:if test="${loginUser.memberId != comment.commentWriter }">
                <td>${comment.commentContent}</td>
              </c:if>              
                  
                  <td>${comment.commentRegdate}</td>
                  
              <c:if test="${loginUser.memberId == comment.commentWriter }">
                 <td style="text-align:center">
                        <button type="button" onclick= "updatecmt(this)" class="btnUpdateComment" name="btnUpdateComment" value="${comment.commentNo}">수정</button>
                      </td>
              </c:if>
              <c:if test="${loginUser.memberId != comment.commentWriter }">
               <td>수정불가</td>
              </c:if>
              
              <c:if test="${loginUser.memberId == comment.commentWriter }">
                 <td style="text-align:center">
                        <button type="button" onclick= "deletecmt(this)" class="btnDeleteComment" name="btnDeleteComment" value="${comment.commentNo}">삭제</button>
                      </td>
              </c:if>
              <c:if test="${loginUser.memberId != comment.commentWriter }">
               <td>삭제불가</td>
              </c:if>                 
                  
               </tr>
            </c:forEach>
         </table>
      </div>


   </div>
   <jsp:include page="${pageContext.request.contextPath }/footer.jsp"></jsp:include>
	<script>
		$(function() {
			//세션, 리퀘스트 스코프에 담겨진 데이터를 빼오는 방식
		    const loginUserId = "${loginUser.memberId}";
		    const boardWriter = '${board.boardWriter}';
			

		    //게시글 작성자와 로그인 유저가 다르면 게시글 수정 못하게 설정
		    if (loginUserId !== boardWriter) {
		        document.getElementById("btnWrap").style.display = "none";
		        document.getElementById("deleteBtn").style.display = "none";
		        document.getElementById("boardTitle").readOnly = true;
		        document.getElementById("boardContent").readOnly = true;
		    }
		    
		    
		});
		
		function deletecmt(e) {
			   
	         let commentNoVal = e.value;
	   
	         $.ajax({
	               url : "/comment/deleteComment.do",
	               type : "POST",
	               data : { commentNo : commentNoVal },
	               success : function(deletecmt) {
	                  if(deletecmt == "success"){
	                     alert("삭제 성공");
	                  location.reload();
	                  } else {
	                     console.log(deletecmt);
	                  }
	                  },
	                  error : function(e) {
	                  console.log(e);
	                  alert("삭제 실패")
	                  }
	                  
	                                 
	            });                     
	         };
	         
	         function updatecmt(e) {
	             
	            let comment = e.value;
	            
	            let commentContentVal = document.getElementById(comment).value;
	            $.ajax({
	                  url : "/comment/updateComment.do",
	                  type : "POST",
	                  data : {
	                      commentNo : comment,
	                      commentContent : commentContentVal
	                      },
	                  success : function(updatecmt) {
	                     if(updatecmt == "success"){
	                        alert("수정 성공");
	                     location.reload();
	                     } 
	                     },
	                     error : function(e) {
	                     console.log(e);
	                     alert("수정 실패")
	                     }
	                     
	                                    
	               });                     
	            };

	</script>
  
	<script src="${pageContext.request.contextPath }/js/getBoard.js?ver=3"></script>
</body>

</html>
 