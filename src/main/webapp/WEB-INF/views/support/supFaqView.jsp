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
    <link href="<c:url value='/css/faqview.css?<%=new Date()%>' />" rel="stylesheet" type="text/css">
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
            <p class="mainTitle2">${faq['title'] }</p>
        </div>
        <div class="mainInfo">
            <div class="mainWriter1">
                <p class="mainWriterName1"><i class="fa-solid fa-tree"></i>작성자</p>
                <p class="mainWriterName2">${faq['managerId'] }</p>
            </div>
        <div class="mainWriter2">
            <p class="mainWriterDate1"><i class="fa-solid fa-tree"></i>작성날짜</p>
            <p class="mainWriterDate2">2025-04-07</p>
        </div>
        <div class="mainContent">
            <p class="mainContent1"><i class="fa-solid fa-tree"></i>내용</p>
            <p class="mainContent2">${faq['content'] }</p>
        </div>
    </div>
</div>
<script>
const listBtn1 = document.querySelector('.listBtn1');
listBtn1.addEventListener('click', () => {
	window.location.href = 'list.do';
});
</script>
</body>
</html>