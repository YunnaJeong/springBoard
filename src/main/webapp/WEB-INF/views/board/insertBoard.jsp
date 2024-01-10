<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		#attZone {
			width: 660px;
			min-height: 150px;
			padding: 10px;
			border: 1px dotted #00f;
		}
		
		#attZone:empty:before {
			content: attr(data-placeholder);
			color: #999;
			font-size: 0.9em;
		}
	</style>
</head>
<body>
	<jsp:include page="${pageContext.request.contextPath }/header.jsp"></jsp:include> 
	<div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
		<h3>새 글 등록</h3>
		<form id="insertForm" action="/board/insertBoard.do" method="post" enctype="multipart/form-data">
			<table border="1" style="border-collapse: collapse">
				<tr>
					<td style="background: #0A82FF; width: 70px;">
						제목
					</td>
					<td style="text-align: left;">
						<input type="text" name="boardTitle" required>
					</td>
				</tr>
				<tr>
					<td style="background: #0A82FF;">
						작성자
					</td>
					<td style="text-align: left;">
						<input type="text" name="boardWriter" value="${loginUser.memberId }" readonly>
					</td>
				</tr>
				<tr>
					<td style="background: #0A82FF; width: 70px;">
						내용
					</td>
					<td style="text-align: left;">
						<textarea name="boardContent" cols="100" rows="10" required></textarea>
					</td>
				</tr>
				<tr>
					<td style="background: #0A82FF; width: 70px;">
						파일첨부
					</td>
					<td>
						<div id="image_preview">
							<input type="file" id="btnAtt" name="uploadFiles" multiple="multiple">
							<div id="attZone"
								 data-placeholder="파일을 첨부하려면 파일선택 버튼을 누르세요."></div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">새 글 등록</button>
					</td>
				</tr>
			</table>
		</form>
		<hr/>
		<a href="/board/getBoardList.do">글 목록</a>
	</div>
	<jsp:include page="${pageContext.request.contextPath }/footer.jsp"></jsp:include>
	
	
		<script>
			/*$("input[name=uploadFiles]").off().on("change", function(){
			
			    if (this.files && this.files[0]) {
			
			        var maxSize = 10 * 1024 * 1024;
			        var fileSize = this.files[0].size;
			        var maxImageWidth = 1024;
			        var maxImageHeight = 1024;
			
			        if(fileSize > maxSize){
			            alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
			            $(this).val('');
			            return false;
			        }
			
			        var img = new Image();
			        var _URL = window.URL || window.webkitURL;
			        img.src = _URL.createObjectURL(this.files[0]);
			        img.onload = function() {
			            var width = img.width;
			            var height = img.height;
			
			            if (width > maxImageWidth || height > maxImageHeight) {
			                alert("이미지의 가로, 세로 사이즈는 1024px x 1024px 이내로 등록 가능합니다.");
			                $(this).val('');
				            return false;
			            }
			        };			        
			    }
			});*/
		</script>
		
		<!--  
		<script>
			$("input[name=uploadFiles]").off().on("change", function(){
		
				if (this.files && this.files[0]) {
		
					var maxSize = 10 * 1024 * 1024;
					var fileSize = this.files[0].size;
		
					if(fileSize > maxSize){
						alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
						$(this).val('');
						return false;
					}
				}
			});
			-->
			
	
		
	
	
	
	<script src="${pageRequest.request.contextPath }/js/insertBoard.js?ver=3"></script>
</body>
</html>
 