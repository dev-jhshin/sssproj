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
    <link href="./../css/admin_question_list_page.css?<%=d.getTime() %>" rel="stylesheet" type="text/css">
    <title>SooP GonG 관리자 페이지</title>
</head>
<body>
<div id="wrap">
    <header>
        <div class="page_logo">
            <img src="./../img/header_logo.svg" alt="로고">
        </div>
        <nav id="divMenu">
            <ul id="ulMenu">
                <li><a href="./memberList.do">회원 목록</a></li>
                <li><a href="./managerList.do">관리자 목록</a></li>
                <li><a href="./reportList.do">신고 내역</a></li>
                <li class="selBtn"><a href="./inquiryList.do">문의 내역</a></li>
            </ul>
        </nav>
        <div class="profile">
            <div class="pro_cont">
                <h3>${sessionScope.managerId }</h3>
                <p>어서오세요. 오늘도 좋은하루 되세요.</p>
            </div>
        </div>
        <div class="pro_btn">
             <a href="./logout.do"><button>로그아웃</button></a>
        </div>
    </header>
      <div class="search_cont">
        <div class="search_box">
           <form name="frmSearch" id="frmSearch" method="get">
               <div class="search_option2">
                   <p>검색명</p>
                   <select id="select_qu" name="search_category" class="search_category">
                       <option value="" disabled selected>선택</option>
                       <option value="memberId">문의 유저</option>                 
                   </select>
                   <div class="ser2_input">
                       <input type="text" class="search_word" name="search_word" placeholder="검색어를 입력해주세요.">
                       <input type="submit" class="serch_btn" name="serch_btn" value="검색">
                   </div>
               </div>
           </form>       
        </div>
    </div>
    <div id="content">
        <div class="listTitle">
            <p class="studentTitle">문의내역</p>
            <p class="studentCnt">전체 ${map.totalInquiry }건</p>
        </div>
        <div class="listMain">
            <div class="mainTitle">
                <p class="mainTitle1">선택</p>
                <p class="mainTitle2">번호</p>
                <p class="mainTitle3">아이디</p>
                <p class="mainTitle4">제목</p>
                <p class="mainTitle5">처리상태</p>             
            </div>
            <c:choose>
                <c:when test= "${not empty map.inquiryList }">
                  <c:forEach var="list" items="${map.inquiryList}" varStatus="loop">
                     <div class="listCon">
                         <a class="mainTitle1" href="./inquiryDetail.do?idx=${list.inquiryIdx}"><button  class="deBtn" type="button">상세보기</button></a>
                         <p class="mainTitle2">${(map.totalInquiry - (map.page_no-1)*map.page_size) - (loop.count-1) }</p>
                         <p class="mainTitle3">${list.smemberId}</p>
                         <p class="mainTitle4">${list.inquiryTitle }</p>
                         <p class="mainTitle5">${list.inquiryStatus }</p>
                     </div>
                  </c:forEach>
               </c:when>
               <c:otherwise>
                  <div>
                     등록된 문의가 없습니다.
                  </div>
               </c:otherwise>
            </c:choose>
        </div>
        <div class="board_page">
            <div class="pages">
                ${map.paging }
            </div>
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
      frm.action = "./inquiryList.do";
      frm.submit();

   } 
   else if ( search_category.value != "" && search_word.value != "" ){
         frm.action = "./inquiryList.do";
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
   }   return;}
   , false);
</script>
</body>
</html>