<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:set var="date" value="<%=new Date().getTime() %>" />
<link href="<c:url value='/css/everyStudy.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<title>모두의 학습</title>
<style>

</style>
</head>

<body>
	<c:set var="dUtil" value="<%=new CommonDateUtil() %>" />
    <div class="page-container">
    
         <!-- 사이드바 -->
        <c:import url="../sidebar.jsp"/>

        <!-- 메인콘텐츠 -->
        <div class="main-content">
         <div class="content-header">모두의 학습</div>
         
             <!-- 서치섹션 -->
            <div class="search-section">
                <div class="search-row">
                    <div class="search-name">기간</div>
                    <div class="search-content">
                        <div class="date-container">
                            <input type="date" class="date-input" id="startDate" value="${ map.startDate }">
                            <span>~</span>
                            <input type="date" class="date-input" id="endDate" value="${ map.endDate }">
                        </div>
                    </div>
                </div>
                <div class="search-row">
                    <div class="search-name">구분</div>
                    <div class="search-content">
                      <div class="dropdown-part">
                         <select class="dropdown" id="searchCategory">
                            <option value="TITLE" ${map.searchCategory eq 'TITLE' ? 'selected' : ''}>제목</option>
						    <option value="CONTENT" ${map.searchCategory eq 'CONTENT' ? 'selected' : ''}>내용</option>
						    <option value="TOPIC" ${map.searchCategory eq 'TOPIC' ? 'selected' : ''}>분야</option>
						    <option value="HASHTAG" ${map.searchCategory eq 'HASHTAG' ? 'selected' : ''}>해시태그</option>
                        </select>
                        <input type="text" class="search-input" id="searchValue" placeholder="검색어" value="${ map.searchValue }"/>
                      </div>
                         <div class="search-btn-set">
                            <button class="search-btn" id="searchButton">조회</button>
                            <button class="delete-btn" id="cancelButton">초기화</button>
                         </div>       
                    </div>
                </div>
            </div>
            
            <div class="category-container">
                <select class="pagedropdown" id="pageSize">
                    <option value="5" ${ map.pageSize eq 5 ? 'selected' : '' }>5</option>
                    <option value="10" ${ map.pageSize eq 10 ? 'selected' : '' }>10</option>
                    <option value="20" ${ map.pageSize eq 20 ? 'selected' : '' }>20</option>
                    <option value="30" ${ map.pageSize eq 30 ? 'selected' : '' }>30</option>
                    <option value="40" ${ map.pageSize eq 40 ? 'selected' : '' }>50</option>
                </select>
            </div>
            
            <!-- 리스트 테이블 -->
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>제목</th>
                            <th>등록일</th>
                            <th>작성자</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:choose>
					        <c:when test="${ not empty learningList }">
					            <c:forEach var="post" items="${ learningList }">
					                <tr class="viewButton" data-href="./view.do?idx=${ post.idx }">
					                    <td>${ post.idx }</td>
					                    <td>${ post.learningTitle }</td>
					                    <td>${ dUtil.localDateTimeToString(post.createdAt) }</td>
					                    <td>${ post.memberId }</td>
					                    <td>${ post.likeCnt }</td>
					                </tr>
					            </c:forEach>
					        </c:when>
					        <c:otherwise>
					            <tr>
					                <td colspan="6">등록된 게시글이 없습니다.</td>
					            </tr>
					        </c:otherwise>
					    </c:choose>
                    </tbody>
                </table>
            </div>
            <!-- 페이징 -->
            <div class="paging">
                ${ paging }
            </div>
        </div>
    </div>
    <script>
	// 조회 버튼
	document.getElementById('searchButton').addEventListener('click', () => {
		const startDate = document.getElementById('startDate').value;
		const endDate = document.getElementById('endDate').value;
		const searchCategory = document.getElementById('searchCategory').value;
		const searchValue = document.getElementById('searchValue').value;
		
		const params = new URLSearchParams();
		
		if (startDate && endDate) { 
			params.append("startDate", startDate);
			params.append("endDate", endDate);
		}
	    if (searchCategory && searchValue) {
	    	params.append("searchCategory", searchCategory);
	    	params.append("searchValue", encodeURIComponent(searchValue));
	    }
		
	    window.location.href = "./list.do?"+ params.toString();
	});
	
	// 초기화 버튼
	document.getElementById('cancelButton').addEventListener('click', () => {
		document.getElementById('startDate').value = null;
		document.getElementById('endDate').value = null;
		document.getElementById('searchCategory').value = null;
		document.getElementById('searchValue').value = null;
		
		window.location.href = "./list.do";
	});
	
	// 게시글 출력 개수
	document.getElementById('pageSize').addEventListener('change', () => {
		const params = new URLSearchParams(window.location.search);
		params.set("page_size", this.value);
		window.location.href = "./list.do?"+ params.toString();
	});
	
	// 게시글 상세 페이지 이동
	document.querySelectorAll(".viewButton").forEach((e) => {
		e.addEventListener("click", function () {
			window.location.href = this.dataset.href;
		});
	});
    
</script>
</body>
</html>