<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
	<link href="<c:url value='/css/listPage.css?<%=new Date()%>' />" rel="stylesheet" type="text/css">
	<title>SooP GonG 고객센터</title>
</head>	
<body>
	<header>
		<div class="page_logo">
			<img src="../../img/header_logo.svg" alt="로고">
		</div>
		<jsp:include page="supMenu.jsp?<%=new Date() %>"/>
		<jsp:include page="supProfile.jsp"/>
	</header>
	<div id="content">
		<div class="listMain">
			<div class="mainTitle">
				<p class="mainTitle1"><i class="fa-solid fa-tree"></i>제목</p>
				<p class="mainTitle2">${inquiry.inquiryTitle }</p>
			</div>
			<div class="mainInfo">
				<div class="mainWriter1">
					<p class="mainWriterName1"><i class="fa-solid fa-tree"></i>작성자</p>
					<p class="mainWriterName2">${inquiry.memberId }</p>
				</div>
			<div class="mainWriter2">
				<p class="mainWriterDate1"><i class="fa-solid fa-tree"></i>작성날짜</p>
				<p class="mainWriterDate2">${fn:split(inquiry.createdAt, 'T')[0] }</p>
			</div>
			<div class="mainContent">
				<p class="mainContent1"><i class="fa-solid fa-tree"></i>내용</p>
				<p class="mainContent2">${inquiry.inquiryContent}</p>
			</div>
			<c:if  var="isDone" test="${ not empty inquiry.iResolutionContent }">
				<div class="mainAnsewr">
					<p class="mainAnsewr1"><i class="fa-solid fa-tree"></i>관리자 답변</p>
					<p class="mainAnsewr2">${inquiry.iResolutionContent } <br>by ${inquiry.managerId }
					</p>
				</div>
			</c:if>
			<c:if test="${ not isDone }">
			 <div class="mainAnsewr">
					<p class="mainAnsewr1"><i class="fa-solid fa-tree"></i>관리자 답변</p>
					<p class="mainAnsewr2">아직 관리자가 답변을 남기지 않았어요..🥲
					</p>
				</div>
			</c:if>
		</div>
			<form name="frmDelete" method="post" action="delete.do">
				<div class="contentBtn">
					<button class="listBtn1" type="button"><i class="fa-solid fa-list"></i>목록</button>
					<input type="submit" class="listBtn2" id="deleteBtn" value="삭제"/>
					<input type="hidden" name="inquiryIdx" value="${inquiry.idx }"/>
					<input type="hidden" name="memberId" value="${inquiry.memberId }"/>
				</div>
			</form>
		</div>
	</div>
	
	<script>
	const listBtn1 = document.querySelector('.listBtn1');
	listBtn1.addEventListener('click', () => {
		window.location.href = 'list.do';
	})
	const deleteBtn = document.querySelector('#deleteBtn');
	deleteBtn.addEventListener('click', () => {
		if(confirm('정말 문의 글을 삭제할까요?\n삭제된 글은 복구되지 않습니다.')) {
			const form = document.getElementById('frmDelete');
			form.submit();
		}
	})
	</script>
</body>
</html>