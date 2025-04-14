<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<!DOCTYPE html>
<html>
<head>	
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
<link href="<c:url value='/css/myinfoStyle.css?<%=new Date() %>'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?<%=new Date() %>'/>" rel="stylesheet" type="text/css">

<title>SG - 나의 정보</title>
</head>
<body>
	<jsp:include page="../sidebar.jsp"/>
	<div class="myinfo_content">
		<div class="profile">
			<p style="color:rgb(8, 111, 41)">반가워요! ${member.memberName }님</p>
			<p>오늘도 좋은하루 되세요!</p>
		</div>
		<div class="ment">
			<p id="randomText">"개발자는 해결사이자 발견자이다." - 마이클 페더스</p>
			<button id="changeTextButton"><span>다른명언 보기</span></button>
			
		</div>
		<p class="myinfo_title">나의 정보</p>
		<div class="myinfo">
			<p><i class="fa-regular fa-user"></i></i>이름 : ${member.memberName }</p>
			<p><i class="fa-regular fa-address-card"></i>아이디 : ${ member.memberId }</p>
			<p><i class="fa-solid fa-chalkboard"></i>이메일 : ${member.memberEmail }</p>
			<p><i class="fa-regular fa-calendar-days"></i>생일 : ${member.memberBirthDate }</p>
			<p><i class="fa-solid fa-venus-mars"></i>성별 : ${member.memberGender }</p>
		</div>
		<div class="myinfo_bottom">
			<img src="../img/bottom_logo.svg">
		</div>
	</div>
	<script>
		const texts = [
			"'소프트웨어는 동서고금을 막론하고 꽤 다루기 어렵다.' - 익명",
			"'컴퓨터는 쓸모가 없다. 그것은 그냥 대답만 할 수 있다.' - 파블로 피카소",
			"'소프트웨어 개발은 예술이다.' - 익명",
			"'프로그래밍은 문제 해결의 예술이다.' - 익명",
			"'버그는 항상 존재한다. 중요한 것은 그것을 어떻게 처리하느냐이다.' - 익명"
		];
		
		const button = document.getElementById('changeTextButton');
		const textDisplay = document.getElementById('randomText');
		
		button.addEventListener('click', () => {
			const randomIndex = Math.floor(Math.random() * texts.length);
			textDisplay.textContent = texts[randomIndex];
		});
	</script>
</body>
</html>