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
	
	#joinForm {
		width: 250px;
		text-align: center;
	}
	
	#joinForm .label-wrapper {
		margin-top: 20px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	
	#joinForm input {
		width: 100%;
	}
	
	#joinForm div {
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
	
	<div class="form-wrapper">
		<form id="joinForm" action="/member/join.do" method="post">
			<input type="hidden" id="joinMsg" value="${joinMsg}">
			<h3>회원가입</h3>
			<div class="label-wrapper">
				<label for="memberId">아이디</label>
			</div>
			<div>
				<input type="text" id="memberId" name="memberId" required style="width: auto;">
				<button type="button" id="btnIdCheck" style="width: 70px">중복체크</button>
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
			<input type="text" id="memberNm" name="memberNm" required>
			<div class="label-wrapper">
				<label for="memberMail">이메일</label>
			</div>
			<input type="email" id="memberMail" name="memberMail" required>
			<div class="label-wrapper">
				<label for="memberTel">전화번호</label>
			</div>
			<input type="text" id="memberTel" name="memberTel" placeholder="숫자만 입력하세요.">
			<div style="display: block; margin: 20px auto;">
				<button type="submit">회원가입</button>
			</div>
		</form>
	</div>
	<jsp:include page="${pageContext.request.contextPath }/footer.jsp"></jsp:include>
	
	<script>
		$(function() {
			//회원가입 실패 시 메시지 출력
			if($("#joinMsg").val() != "" && $("#joinMsg").val() != null) {
				alert($("#joinMsg").val());
			}
			
			//id 중복체크 했는지 확인하는 플래그
			var checkId = false;
			//password가 형식에 맞게 작성됐는지(특수문자 + 영문자 + 숫자 8자리)
			var pwValidation = false;
			//password가 확인란과 일치하는지
			var pwCheck = false;
			
			$("#pwValidation").hide();
			$("#pwCheckResult").hide();
			
			//id 중복체크
			$("#btnIdCheck").on("click", function() {
			
				
				$.ajax({
					url: "/member/idCheck.do",
					type: "post",
					data: $("#joinForm").serialize(),
					success: function(obj) {
						console.log(obj);
						
						if(obj == 'duplicatedId') {
							alert("중복된 아이디입니다.");
							$("#memberId").focus();
						} else {
							if(confirm("사용가능한 아이디입니다. " + $("#memberId").val() + 
									"를(을) 사용하시겠습니까?")) {
								checkId = true;
								$("#btnIdCheck").attr("disabled", true);
							}
						}
					},
					error: function(e) {
						console.log(e);
					}
				})
			});
			
			//id 중복체크 후 다시 id를 변경했을 때
			$("#memberId").on("change", function() {
				checkId = false;
				$("#btnIdCheck").attr("disabled", false);
			});
			
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
			
			//회원가입 진행
			$("#joinForm").on("submit", function(e) {
				
				//아이디 중복체크가 안됐거나 중복된 아이디를 사용했을 때
				if(!checkId) {
					alert("아이디 중복체크를 진행하세요.");
					$("#memberId").focus();
					e.preventDefault();
					return;
				}
				
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
			});
		});
		
		
		
	
	</script>
</body>
</html>