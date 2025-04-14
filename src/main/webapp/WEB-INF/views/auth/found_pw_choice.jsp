<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="./../css/style.css" rel="stylesheet" type="text/css">
<title>Document</title>
</head>
<body>
	<div class="wrap">
		<header>
			<img src="./../img/header_logo.svg" alt="로고" id="home">
			<p>비밀번호를 찾을 방법을 선택해주세요.</p>
		</header>
		<main>
			<div class="emailChoicebox">
				<a href="./findPwdEmail.do" class="emailChoice">이메일로 비밀번호 찾기</a>
			</div>
			<div class="questionChoicebox">
				<a href="./memberVerification.do" class="questionChoice">질문으로 비밀번호 찾기</a>
			</div>
		</main>
	</div>	
	</body>
	<script>
		document.getElementById('home').addEventListener('click', function() {
			window.location.href = "./login.do";
		});
	</script>
</html>