<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.form-wrapper {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	
	#updateForm {
		width: 250px;
		text-align: center;
	}
	 
	#updateForm .label-wrapper {
		margin-top: 20px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	
	#updateForm input {
		width: 100%;
	}
	
	#updateForm div {
		display: flex;
		align-items: center;
	}
	
	#btnIdCheck {
		width: 50px;
	}
</style>


</head>
<body>
	<jsp:include page="${pageContext.request.contextPath }/header.jsp"></jsp:include>
	<div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
	
		<h3>회원정보</h3>
		<!-- <form id="updateForm" action="/member/mypage.do" method="post">  -->
		<form id="updateForm" name="updateForm" method="post">
		
			<input type="hidden" id="updateMsg" value="${updateMsg}">			
			
			<div class="label-wrapper">
				<label for="memberId">아이디</label>
			</div>
			<div>
				<input type="text" id="memberId" name="memberId" value="${loginUser.memberId}" readonly >
			</div>
			<div class="label-wrapper">
				<label for="memberPw">비밀번호</label>
			</div>
			<input type="password" id="memberPw" name="memberPw" required>
			<p id="pwValidation" style="color: red; font-size: 0.8rem;">
				비밀번호는 영문자, 숫자, 특수문자 조합의 9자리 이상으로 설정해주세요.
			</p>
			<div class="label-wrapper">
				<label for="memberPwCheck">비밀번호 확인</label>
			</div>
			<input type="password" id="memberPwCheck" name="memberPwCheck" required>
			<p id="pwCheckResult" style="font-size: 0.8rem;"></p>
			<div class="label-wrapper">
				<label for="memberNm">이름</label>
			</div>
			<input type="text" id="memberNm" name="memberNm" value="${loginUser.memberNm}" required>
			<div class="label-wrapper">
				<label for="memberMail">이메일</label>
			</div>
			<input type="email" id="memberMail" name="memberMail" value="${loginUser.memberMail}" required >
			<div class="label-wrapper">
				<label for="memberTel">전화번호</label>
			</div>
			<input type="text" id="memberTel" name="memberTel" value="${loginUser.memberTel}" required >
			
			<div style="display: block; margin: 20px auto;">
				<button type="submit">수정</button>
			</div>
		</form>
		
		
		<hr/>
		
		
	</div>
	<jsp:include page="${pageContext.request.contextPath }/footer.jsp"></jsp:include>
	
		<script>
		$(function() {
			//회원정보 수정 실패 시 메시지 출력
			if($("#updateMsg").val() != "" && $("#updateMsg").val() != null) {
				alert($("#jupdateMsg").val());
			}
			
			//password가 형식에 맞게 작성됐는지(특수문자 + 영문자 + 숫자 8자리)
			var pwValidation = false;
			//password가 확인란과 일치하는지
			var pwCheck = false;
			
			$("#pwValidation").hide();
			$("#pwCheckResult").hide();
			
			
			//비밀번호 유효성 검사
			function validatePassword(character) {
				return /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{9,}$/.test(character);
			}
			
			//비밀번호 입력될때마다 유효성 검사
			$("#memberPw").on("change", function() {
				//비밀번호 유효성 처리
				if(!validatePassword($("#memberPw").val())) {
					pwValidation = false;
					$("#pwValidation").show();
					$("#memberPw").focus();
				} else {
					pwValidation = true;
					$("#pwValidation").hide();
				}
				
				//비밀번호 확인까지 입력한 후 다시 비밀번호 재설정
				if($("#memberPw").val() == $("#memberPwCheck").val()) {
					pwCheck = true;
					$("#pwCheckResult").css("color", "green");
					$("#pwCheckResult").text("비밀번호가 일치합니다.");
				} else {
					pwCheck = false;
					$("#pwCheckResult").css("color", "red");
					$("#pwCheckResult").text("비밀번호가 일치하지 않습니다.");
				}
			});
			
			//비밀번호 확인란 입력할 때 일치여부 체크
			$("#memberPwCheck").on("change", function() {
				$("#pwCheckResult").show();
				
				if($("#memberPw").val() == $("#memberPwCheck").val()) {
					pwCheck = true;
					$("#pwCheckResult").css("color", "green");
					$("#pwCheckResult").text("비밀번호가 일치합니다.");
				} else {
					pwCheck = false;
					$("#pwCheckResult").css("color", "red");
					$("#pwCheckResult").text("비밀번호가 일치하지 않습니다.");
				}
			});
			
			//수정 진행
			
			// 내정보수정 진행
			$("#btnUpdateUser").click("submit", function(e) {
				//비밀번호 유효성 검사가 틀렸을 때
				if (!pwValidation) {
					alert("비밀번호는 영문자, 숫자, 특수문자 조합의 9자리 이상으로 설정하세요.");
					$("#userPw").focus();
					e.preventDefault();
					return;
				}

				//비밀번호와 비밀번호 확인이 일치하지 않을 때
				if (!pwCheck) {
					alert("비밀번호가 일치하지 않습니다.");
					$("#userPw2").focus();
					e.preventDefault();
					return;
				}
				
				document.myInfoForm.action = " ${path}/user/updateUser.do";
				document.myInfoForm.submit();
				if (pwValidation && pwCheck) {
					alert("내정보를 수정하였습니다. 다시 로그인해주시길 바랍니다.");
				}
				
				
			});

			
			
			$("#updateForm").on("submit", function(e) {			
				
				//비밀번호 유효성 검사가 틀렸을 때
				if(!pwValidation) {
					alert("비밀번호는 영문자, 숫자, 특수문자 조합의 9자리 이상으로 설정하세요.");
					$("#memberPw").focus();
					e.preventDefault();
					return;
				}
				
				//비밀번호와 비밀번호 확인이 일치하지 않을 때
				if(!pwCheck) {
					alert("비밀번호가 일치하지 않습니다.");
					$("#memberPwCheck").focus();
					e.preventDefault();
					return;
				}
				
				document.updateForm.action = " ${path}/member/updateMember.do";
				document.updateForm.submit();
				if (pwValidation && pwCheck) {
					alert("내정보를 수정하였습니다. 다시 로그인해주시길 바랍니다.");
				}
				
				
				
			});
		});
		
		
		
	
	</script>
	
</body>
</html>