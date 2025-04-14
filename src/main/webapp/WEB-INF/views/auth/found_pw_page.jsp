<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="./../css/found_pw_page.css" rel="stylesheet" type="text/css">
	<title>SooP GonG 비밀번호 찾기</title>
</head>
<body>	
	<div class="wrap">
		<header>
			<a href="./findPwdChoice.do"><img class="header_logo" src="./../img/header_logo.svg" alt="메인로고"></a>
			<p class="header_comment">"비밀번호 찾기 페이지입니다."</p>
		</header>
		<main>
			<form id="fpw_form"class="fpw_form" action="./memberVerification.do" method="post">
				<div class="fpw_input_box"> 
					<p class="fpw_tit">가입 한 아이디</p>
					<input type="text" name="memberId" id="username"  class="fpw_input" placeholder="가입하신 아이디를 입력해주세요." autocomplete="off">
				</div>
				<div class="fpw_input_box"> 
					<p class="fpw_tit">이름</p>
					<input type="text" name="memberName" class="fpw_input" maxlength="10" autocomplete="off" required> 
				</div>
				<div class="fpw_input_box2"> 
					<p class="fpw_tit">비밀번호 찾기 질문</p>
					<select id="select_qu" name="security_question">
						<option value="" disabled selected>질문을 선택해주세요</option>
						<option value="1">당신의 학창시절 별명은 무엇인가요?</option>
						<option value="2">기억의 남는 게임 한 가지</option>
						<option value="3">출신 고등학교 이름은 무엇인가요?</option>
						<option value="4">음식을 무엇을 제일 좋아하나요?</option>
						<option value="5">살면서 처음 해 본 게임 이름</option>
						<option value="6">제일 재밌게 봤었던 영화 이름 한 가지</option>
						<option value="7">출신 초등학교 이름은 무엇인가요?</option>
						<option value="8">제일 행복했었던 나이는 몇 살인가요?</option>
						<option value="9">살면서 제일 힘들었었던 나이는 몇 살인가요?</option>
						<option value="10">기억에 남는 버스 번호는 몇 번인가요?</option>
					</select>
					<input type="text" class="fpw_input2" name="answer" placeholder="답변 입력" autocomplete="off" required>
				</div>
				<div class="btm_line"></div>
				<input id="checkKey" class="fpw_btn" type="submit" name="fpw_btn" autocompletete="off" value="비밀번호 변경">
			</form>
		</main>
		<footer>
			<a href="#"><img class="bottom_logo" src="./../img/bottom_logo.svg" alt="푸터로고"></a>
		</footer>
	</div>
</body>
</html>