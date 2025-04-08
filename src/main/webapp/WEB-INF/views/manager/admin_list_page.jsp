<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% Date d = new Date(); %>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
    <link href="./../css/admin_list_page.css?<%=d.getTime() %>" rel="stylesheet" type="text/css">
    <title>SooP GonG 관리자 페이지</title>
</head>
<body>
<div id="modal">
	<div class="modalwrap">
		<div class="modalheader">
			<div class="head_img">
				<img src="./../img/header_logo.svg">
			</div>
			<div class="head_noti">
				<p>숲공 - 관리자 추가</p>
			</div>
		</div>
		<div class="modalcontent">
			<div class="board">
				<div class="boardContent">
					<p>이름</p>
					<p>아이디</p>
					<p>비밀번호</p>
					<p>이메일</p>
					<p>직급</p>
				</div>
			</div>
			<form class="modalForm" action="managerAdd.do" method="post">
				<div class="titleMain">
					<input type="text" name="managerName" class="adminName">
					<input type="text" name="managerId" class="adminId">
					<input type="text" name="managerPwd" class="adminPwd">
					<input type="text" name="managerEmail" class="adminMail">
					<select name="managerStatus">
						<option>1</option>
						<option>2</option>
						<option>3</option>
					</select>
				</div>
				<div class="btmwrap">
					<input class="endBtn" type="submit" value="작성완료">
					<a class="endBtn" href="#">취소</a>
				</div>
			</form>
		</div>
	</div>
</div>
<div id="wrap">
    <header>
        <div class="page_logo">
            <img src="./../img/header_logo.svg" alt="로고">
        </div>
        <nav id="divMenu">
            <ul id="ulMenu">
                <li><a href="./memberList.do">회원 목록</a></li>
                <li class="selBtn"><a href="./managerList.do">관리자 목록</a></li>
                <li><a href="./reportList.do">신고 내역</a></li>
                <li><a href="./inquiryList.do">문의 내역</a></li>
            </ul>
        </nav>
        <div class="profile">
            <div class="pro_cont">
                <h3>${sessionScope.managerId }</h3>
                <p>어서오세요. 오늘도 좋은하루 되세요.</p>
            </div>
			<div class="pro_btn">
				<a href="./logout.do"><button>로그아웃</button></a>
			</div>
        </div>
    </header>
    <div id="content">
        <div class="listTitle">
            <p class="studentTitle">관리자 목록</p>
            <p class="studentCnt">전체 <span style="color:#006400; font-size:18px;">${map.totalManager }</span>건</p>
			<div class="contentHead">
				<div class="search_cont">
					<div class="search_box">
						<form name="frmSearch" id="frmSearch" method="get">
							<div class="search_option2">
								<select id="select_qu" name="search_category" class="search_category">
									<option value="" disabled selected>선택</option>
									<option value="managerId">관리자 아이디</option>
									<option value="managerName">관리자 이름</option>
								</select>
								<div class="ser2_input">
									<input type="text" class="search_word" name="search_word"  placeholder="검색어를 입력해주세요.">
									<button type="submit" class="serch_btn" name="serch_btn" value="검색"><i class="fa-solid fa-magnifying-glass"></i></button>
								</div>
							</div>
						 </form>        
					</div>
				</div>
			</div>
        </div>
        <div class="listMain">
            <div class="mainTitle">
                <p class="mainTitle1">번호</p>
                <p class="mainTitle2">이름</p>
                <p class="mainTitle3">아이디</p>
                <p class="mainTitle4">이메일</p>
                <p class="mainTitle5">직급</p>
                <p class="mainTitle6">변경</p>               
                <p class="mainTitle7">삭제</p>
            </div>
            <c:choose>
            	<c:when test = "${not empty map.managerList }">
            		<c:forEach var="list" items="${map.managerList}" varStatus="loop">
		        		<div class="listCon">
		                	<p class="mainTitle1">${(map.totalManager - (map.page_no-1)*map.page_size) - (loop.count-1) }</p>
		                	<p class="mainTitle2">${list.managerName}</p>
		                	<p class="mainTitle3">${list.managerId}</p>
		                	<p class="mainTitle4">${list.managerEmail}</p>
		                	<p class="mainTitle5">${list.managerStatus}</p>
		                	<a href="./managerStatusChange.do?manager_id=${list.managerId}&&manager_status=${list.managerStatus}" class="mainTitle6"><button class="listBtn2"  type="button"><i class="fa-solid fa-pen-to-square"></i></button></a>
		                	<a href="./managerDelete.do?manager_id=${list.managerId}&&manager_status=${list.managerStatus}" class="mainTitle7"><button class="listBtn" type="button"><i class="fa-solid fa-trash-can"></i></button></a>
		            	</div>
	            	</c:forEach>
	         	</c:when>
	         	<c:otherwise>
	         		<div>
	         			매니저 정보가 없습니다.
	         		</div>
	         	</c:otherwise>
	        </c:choose>
        </div>
        <div class="board_page">
			<div class="pages">
				${map.paging }
			</div>
            <a href="#modal" class="createAdmin">추가</a>
        </div>  
	</div>
</div>
<script>
	const btnSearch = document.querySelector(".serch_btn");
	btnSearch.addEventListener("click", function(e){
		e.preventDefault();
		e.stopPropagation();
		const search_category = document.querySelector(".search_category");
		const search_word = document.querySelector(".search_word");
		
		const frm = document.querySelector("#frmSearch");
		if ( search_category.value == "" && search_word.value == "" ){
			search_category.value = "";
			search_word.value = "";
			frm.action = "./managerList.do";
			frm.submit();
	
		} 
		else if ( search_category.value != "" && search_word.value != "" ){
				frm.action = "./managerList.do";
				frm.submit();
		}
		else {
			alert("search_category : "+ search_category.value);
		 	if(search_category.value.length < 1 || search_category.value == "" || search_category.value == null) {
				alert("검색 구분을 선택하세요.");
				search_category.focus();
				return false;
			}
			alert("search_word : "+ search_word.value);
		 	if(search_word.value.length<1 || search_word.value == "" || search_word.value == null) {
				alert("검색어를 입력하세요.");
				search_word.focus();
				return false;
			}
		}	return;}
		, false);
</script>
</body>
</html>