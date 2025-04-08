<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib  prefix="c" uri="jakarta.tags.core" %>
<% Date d = new Date(); %>
<%@page import="java.util.Date"%>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
    <link href="./../css/adStuListPgStyle.css?<%=d.getTime() %>" rel="stylesheet" type="text/css">
    <title>SooP GonG 관리자 페이지</title>
</head>
<body>
<c:set var="dUtil" value="<%=new CommonDateUtil() %>"/>
<div id="wrap">
    <header>
        <div class="page_logo">
            <img src="./../img/header_logo.svg" alt="로고">
        </div>
        <nav id="divMenu">
            <ul id="ulMenu">
                <li class="selBtn"><a href="./memberList.do" >회원 목록</a></li>
                <li><a href="./managerList.do">관리자 목록</a></li>
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
            <p class="studentTitle">회원 목록</p>
            <p class="studentCnt">전체 <span style="color:#006400; font-size:18px;">${map.totalMember}</span>건</p>
			<div class="contentHead">
				<div class="search_cont">
					<div class="search_box">
						<form name="frmSearch" id="frmSearch" method="get">
							<div class="search_option2">
								<select id="select_qu" name="search_category" class="search_category">
									<option value="" disabled selected>선택</option>
									<option value="memberId">아이디</option>
									<option value="memberName">이름</option>
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
                <p class="mainTitle2">번호</p>
                <p class="mainTitle3">이름</p>
                <p class="mainTitle4">아이디</p>
                <p class="mainTitle5">회원상태</p>
                <p class="mainTitle6">이메일</p>
                <p class="mainTitle7">가입일</p>
                <p class="mainTitle8">마지막 접속일</p>
                <p class="mainTitle9">상태변경</p>
                <p class="mainTitle10">삭제</p>
            </div>
           	<c:choose>
            	<c:when test = "${not empty map.memberList }">
            		<c:forEach var="list" items="${map.memberList}" varStatus="loop">
			        	<div class="listCon">
			                <p class="mainTitle2">${(map.totalMember - (map.page_no-1)*map.page_size) - (loop.count-1)}</p>
			                <p class="mainTitle3">${list.memberName }</p>
			                <p class="mainTitle4">${list.memberId }</p>
			                <p class="mainTitle5">${list.memberStatus }</p>
			                <p class="mainTitle6">${list.memberEmail }</p>
			                <p class="mainTitle7">${dUtil.localDateTimeToString(list.memberCreatedAt) }</p>
			                <p class="mainTitle8">${dUtil.localDateTimeToString(list.memberLoginAt)}</p>
			                <a href="./memberStatusChange.do?member_id=${list.memberId}&&member_status=${list.memberStatus}" class="mainTitle9"><button class="listBtn2"  type="button"><i class="fa-solid fa-pen-to-square"></i></button></a>
			                <a href="./memberDelete.do?member_id=${list.memberId}" class="mainTitle10"><button class="listBtn" type="button"><i class="fa-solid fa-trash-can"></i></button></a>
			           </div>
			  		</c:forEach>
			  	</c:when>
				<c:otherwise>
				 	<div>
				 		회원 정보가 없습니다.
				 	</div>
				</c:otherwise>
			</c:choose>
        </div>
        <div class="board_page">
			${map.paging }
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
			frm.action = "./memberList.do";
			frm.submit();
	
		} 
		else if ( search_category.value != "" && search_word.value != "" ){
				frm.action = "./memberList.do";
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