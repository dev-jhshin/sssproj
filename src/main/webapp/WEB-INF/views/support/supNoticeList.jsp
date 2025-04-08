<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
    <link href="<c:url value='/css/noticePageStyle.css?<%=new Date() %>' />" rel="stylesheet" type="text/css">
    <title>SooP GonG 고객센터</title>
</head>
<body>
<div id="wrap">
    <header>
        <div class="page_logo">
            <img src="../img/header_logo.svg" alt="로고">
        </div>
        <jsp:include page="supMenu.jsp?<%=new Date() %>"/>
        <jsp:include page="supProfile.jsp?<%=new Date() %>"/>
        <%-- <c:import url="supProfile.jsp"/> --%>
    </header>
    <div id="content">
        <div class="board_wrap">
            <div class="board_title">
                <h1>공지사항</h1>
                <p>학습관련 공지사항 또는 홈페이지 공지사항이 업로드되는 공간입니다.</p>
            </div>
            <div class="board_list_wrap">
                <div class="board_list">
                    <div class="top">
                        <div class="num">번호</div>
                        <div class="title">제목</div>
                        <div class="content">내용</div>
                        <div class="writer">글쓴이</div>
                        <div class="date">작성일</div>
                        <div class="count">조회</div>
                    </div>
                    <div>
                        <div class="num">5</div>
                        <div class="title"><a href="view.html">글 제목이 들어갑니다.</a></div>
                        <div class="content">내용이 들어갈까 말까~?</div>
                        <div class="writer">이동규</div>
                        <div class="date">2024.1.3</div>
                        <div class="count">33</div>
                    </div>
                    <div>
                        <div class="num">4</div>
                        <div class="title"><a href="view.html">글 제목이 들어갑니다.</a></div>
                        <div class="content">내용이 들어갈까 말까~?</div>
                        <div class="writer">이덩그</div>
                        <div class="date">2024.1.3</div>
                        <div class="count">33</div>
                    </div>
                    <div>
                        <div class="num">3</div>
                        <div class="title"><a href="view.html">글 제목이 들어갑니다.</a></div>
                        <div class="content">내용이 들어갈까 말까~?</div>
                        <div class="writer">이규동</div>
                        <div class="date">2024.1.3</div>
                        <div class="count">33</div>
                    </div>
                    <div>
                        <div class="num">2</div>
                        <div class="title"><a href="view.html">글 제목이 들어갑니다.</a></div>
                        <div class="content">내용이 들어갈까 말까~?</div>
                        <div class="writer">이동구</div>
                        <div class="date">2024.1.3</div>
                        <div class="count">33</div>
                    </div>
                    <div>
                        <div class="num">1</div>
                        <div class="title"><a href="view.html">글 제목이 들어갑니다.</a></div>
                        <div class="content">내용이 들어갈까 말까~?</div>
                        <div class="writer">이동구</div>
                        <div class="date">2024.1.3</div>
                        <div class="count">33</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <!-- <form class="search_cont" action="" method="">
        <div class="search_box">
            <div class="search_option2">
                <p>검색명</p>
                <select id="select_qu">
                    <option value="qu0">전체</option>
                    <option value="qu1">제목</option>
                    <option value="qu2">내용</option>
                </select>
                <div class="ser2_input">
                    <input type="text" class="serch_in" placeholder="검색어를 입력해주세요.">
                    <input type="submit" class="serch_btn" name="serch_btn" value="검색">
                </div>
            </div>        
        </div>
    </form> -->
</body>
</html>