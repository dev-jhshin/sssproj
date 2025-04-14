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
	<link href="<c:url value='/css/question_list_page.css?<%=new Date()%>' />" rel="stylesheet" type="text/css">
	<title>SooP GonG 고객센터</title>
</head>
<body>
	<div class="wrap">
		<header>
			<div class="page_logo">
				<img src="../../img/header_logo.svg" alt="로고" class="home">
			</div>
			<jsp:include page="supMenu.jsp?<%=new Date() %>"/>
			<jsp:include page="supProfile.jsp?<%=new Date() %>"/>
		</header>
		<form class="search_cont" action="list.do" method="get">
			<div class="search_box">
				<div class="search_option2">
					<p>검색명</p>
					<select id="select_qu" name="search_category">
						<!-- <option value="qu0">전체</option> -->
						<option value="inquiryTitle" <c:if test="${pMap.searchCategory eq 'inquiryTitle' }">selected</c:if>>제목</option>
						<option value="inquiryContent" <c:if test="${pMap.searchCategory eq 'inquiryContent' }">selected</c:if>>내용</option>
					</select>
					<div class="ser2_input">
						<input type="text" class="serch_in" placeholder="검색어를 입력해주세요." name="search_word" value="${pMap.searchWord }" required>
						<input type="submit" class="serch_btn" name="serch_btn" value="검색">
					</div>
				</div>		
			</div>
		</form>
		<div id="content">
			<div class="board_wrap">
				<div class="board_title">
					<h1>문의 내역</h1>
					<p>문의 주셨던 답변을 확인할 수 있어요.</p>
				</div>
				
				<div class="board_list_wrap">
					<div class="category-container">
					<select class="pagedropdown" onchange="updatePageSize(this)">
						<option value="5"
							<c:if test="${pMap.pageSize == 5 }">selected</c:if>>5</option>
						<option value="10"
							<c:if test="${pMap.pageSize == 10 }">selected</c:if>>10</option>
						<option value="20"
							<c:if test="${pMap.pageSize == 20 }">selected</c:if>>20</option>
						<option value="30"
							<c:if test="${pMap.pageSize == 30 }">selected</c:if>>30</option>
						<option value="50"
							<c:if test="${pMap.pageSize == 50 }">selected</c:if>>50</option>
					</select>&nbsp;&nbsp;<p class="catep">개씩 보기</p>
				</div>
					<!-- 문의 내역 리스트 -->
					<div class="board_list">
						<div class="top">
							<div class="num">번호</div>
							<div class="title">제목</div>
							<div class="content">내용</div>
							<div class="writer">글쓴이</div>
							<div class="date">작성일</div>
							<div class="count">처리여부</div>
						</div>
						<c:forEach items="${pMap.inquiries }" var="inquiry">
							<div>
								<div class="num">${inquiry.idx }</div>
								<div class="title"><a href="view.do?idx=${inquiry.idx }">${inquiry.inquiryTitle }</a></div>
								<div class="content">${inquiry.inquiryContent }</div>
								<div class="writer">${inquiry.memberId }</div>
								<div class="date">${fn:split(inquiry.createdAt, 'T')[0] }</div>
								<div class="count">
									<c:choose>
										<c:when test="${inquiry.inquiryStatus > 0 }">Y</c:when>
										<c:otherwise>N</c:otherwise>
									</c:choose>
								</div>
							</div>
						</c:forEach>
						<c:if test="${ empty pMap.inquiries }">
						<div>
							<p>작성하신 문의 글이 아직 없어요..</p>
						</div>
						</c:if>
					</div>
					<!-- 페이징 영역 -->
					<div class="board_page">
						${pMap.paging }
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		function updatePageSize(e) {
			
			let pageSize = e.value;
			// URL에 쿼리 파라미터를 추가하거나 수정
			let currentUrl = window.location.href;
			let newUrl = currentUrl.split('?')[0]; // 기존 URL에서 쿼리 스트링 제거
			let newLocation = newUrl + "?page_size=" + pageSize; // 원하는 파라미터 추가
			newLocation += "&category=${pMap.category}";
			// 페이지 이동
			window.location.href = newLocation;
			}
		//모두의 학습 페이지 이동	
		document.querySelector('.home').addEventListener('click', function() {
			window.location.href = "/sssproj/learning/list.do";
		});
	</script>
</body>
</html>