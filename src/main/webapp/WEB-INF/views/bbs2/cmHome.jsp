<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@page import="net.fullstack10.common.CommonDateUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/css/cmHome.css' />?v=<%=System.currentTimeMillis()%>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?<%=new Date()%>' />" rel="stylesheet" type="text/css">
<title>커뮤니티</title>
</head>
<body>
	<c:set var="dUtil" value="<%=new CommonDateUtil()%>" scope="request" />
	<div class="page-container">
		<!-- 사이드바 -->
		<c:import url="../sidebar.jsp" />
		<!-- 메인콘텐츠 -->
		<div class="main-content div860">
			<div class="content-header">커뮤니티</div>
			<!-- 커뮤니티 카테고리 -->
			<div class="category-section">
				<c:choose>
					<c:when test="${empty pMap.category || pMap.category eq ''}">
						<div class="category" style="background-color: #F2F7EA;">
							<input type="button" class="category-btn"
								style="background-color: #F2F7EA;" value="전체" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="category">
							<input type="button" class="category-btn" value="전체" />
						</div>
					</c:otherwise>
				</c:choose>

				<c:forEach items="${pMap.categories }" var="category">
					<c:choose>
						<c:when test="${category eq pMap.category }">
							<div class="category" style="background-color: #F2F7EA;">
								<input type="button" style="background-color: #F2F7EA;" class="category-btn" value="${category }" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="category">
								<input type="button" class="category-btn" value="${category }" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>

			<!-- 서치섹션 -->
			<form name="search-form" id="search-form" method="get"
				action="list.do">
				<input type="hidden" name="category" id="category" value="${pMap.category }" />
				<div class="search-section">
					<div class="search-row">
						<div class="search-name">기간</div>
						<div class="search-content">
							<div class="date-container">
								<input type="date" class="date-input" name="search_start" value="${pMap.searchStart }"> 
								<span>~</span> 
								<input type="date" class="date-input" name="search_end" value="${pMap.searchEnd }">
							</div>
						</div>
					</div>
					<div class="search-row">
						<div class="search-name">구분</div>
						<div class="search-content">
						  <div class="dropwdown-part">
							<select class="dropdown" name="search_category">
								<option value="bbsTitle" ${pMap.searchCategory eq 'bbsTitle' ? 'selected="selected"' : ''}>제목</option>
								<option value="bbsContent" ${pMap.searchCategory eq 'bbsContent' ? 'selected="selected"' : ''}>글내용</option>
								<option value="memberId" ${pMap.searchCategory eq 'memberId' ? 'selected="selected"' : ''}>작성자</option>
							</select> 
							<input type="text" class="search-input" name="search_word" autocomplete="off"
								placeholder="검색할 키워드 입력" value="${pMap.searchWord}">
								</div>
								<div class="search-btn-set">
         							<button class="search-btn">검색</button>
   							        <input type="button" class="search-btn search-init" value="초기화"
								     onclick="searchInit()">
								</div>
						</div>
					</div>
				</div>
			</form>

			<div class="category-section-order">
				<div class="category-list-part">
					<c:choose>
						<c:when test="${pMap.searchOrder eq 'orderByViewCnt' }">
							<div class="category-list-active" id="orderByViewCnt">조회수 순</div>
							<div class="category-list" id="orderByLikeCnt">좋아요 순</div>
						</c:when>
						<c:when test="${pMap.searchOrder eq 'orderByLikeCnt' }">
							<div class="category-list" id="orderByViewCnt">조회수 순</div>
							<div class="category-list-active" id="orderByLikeCnt">좋아요 순</div>
						</c:when>
						<c:otherwise>
						<div class="category-list" id="orderByViewCnt">조회수 순</div>
						<div class="category-list" id="orderByLikeCnt">좋아요 순</div>
						</c:otherwise>
					</c:choose>
				</div>
				<span>
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
				</select>개씩 보기</span>
			</div>

			<!-- 리스트 테이블 -->
			<div class="table-container">
				<table>
					<thead>
						<tr>
							<c:if test="${empty pMap.category }">
								<th class="th80">카테고리</th>
							</c:if>
							<c:if test="${not empty pMap.category }">
								<th class="th80">No</th>
							</c:if>
							<th>제목</th>
							<th class="th20">작성자</th>
							<th class="th20">작성일</th>
							<th class="th65">조회수</th>
							<th class="th65">좋아요</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pMap.bbsList }" var="bbs">
							<tr>
								<c:if test="${empty pMap.category }">
									<td><a href="list.do?category=${bbs.bbsCategory }" >${bbs.bbsCategory }</a></td>
								</c:if>
								<c:if test="${not empty pMap.category }">
									<td>${bbs.idx }</td>
								</c:if>

								<td class="td-title"><a href="view.do?idx=${bbs.idx}">${bbs.bbsTitle }</a></td>
								<td><a href="list.do?category=&search_start=&search_end=&search_category=memberId&search_word=${bbs.memberId }">${bbs.memberId }</a></td>
								<td>${dUtil.toString(bbs.createdAt) }</td>
								<td>${bbs.viewCnt }</td>
								<td>${bbs.likeCnt }</td>
							</tr>
						</c:forEach>
						<c:if test="${empty pMap.bbsList }">
							<tr>
								<td colspan="6">등록된 게시글이 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>


			<!-- 페이징 -->
			<div class="paging">${pMap.paging }</div>
			<hr>
			
			<!-- 글 작성  -->
			<c:if test="${not empty sessionScope.memberId }">
				<button class="regist-btn" id="registButton">등록</button>
			</c:if>
		</div>
	</div>

	<script>
	
     // 등록 버튼 클릭 이동
     const registButton = document.getElementById('registButton');
     if(registButton) {
	     registButton.addEventListener('click', function() {
	    	 window.location.href = 'regist.do';
	     });
     }

     const categoryBtns = document.querySelectorAll('.category-btn');
     console.log(categoryBtns);
     categoryBtns.forEach((categoryBtn)=> {
    	 categoryBtn.addEventListener('click', () => {
    		 console.log("clicked");
    		 if(categoryBtn.value !== "전체") {
        	 	document.getElementById('category').value = categoryBtn.value;
    		 } else {
    			 document.getElementById('category').value = "";
    		 }
        	 document.querySelector(".category-btn").value = "";
        	 document.querySelector(".search-input").value = "";
        	 document.querySelector('#search-form').submit();
    	 })
     })
     
	function searchInit() {
    	 window.location.href='list.do';
     }
     
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
     const orderByLikeCnt = document.getElementById('orderByLikeCnt');
     orderByLikeCnt.addEventListener('click', () => {
    	 const params = new URLSearchParams(window.location.search);
    	 params.set("searchOrder", "orderByLikeCnt");
    	 window.location.href='./list.do?'+params.toString();
     })
     const orderByViewCnt = document.getElementById('orderByViewCnt');
     orderByViewCnt.addEventListener('click', () => {
    	 const params = new URLSearchParams(window.location.search);
    	 params.set("searchOrder", "orderByViewCnt");
    	 window.location.href='./list.do?'+params.toString();
     })
</script>
</body>
</html>