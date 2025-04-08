<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<% Date d = new Date(); %>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/homeMenuStyle.css?<%=d.getTime() %>'/>" rel="stylesheet" type="text/css">
    <title>SooP GonG 홈</title>
</head>
<body>
<div class="wrap">
    <header>
        <a href="#"><img class="header_logo" src="../img/header_logo.svg" alt="메인로고"></a>
        <p class="header_comment"><span style="color:rgb(3, 137, 32); font-size: 30px;">숲속처럼</span> 편안한 여러분만의 공부방 <span style="color:rgb(3, 137, 32); font-size: 30px;">숲공</span>에 오신 걸 환영해요.</p>
    </header>
    <main>
        <div class="flex-container">
            <div class="flex-item">
                <div class="item_img1">
                    <img src="../img/student.jpg" alt="학생 로그인">
                </div>
                <p>공부하기 위해서는 숲공의 학생이 되어야 해요.
                    계정이 없는 학생은 회원가입 먼저, 계정이 있는 학생은
                    로그인을 한 후 학습을 진행하실 수 있습니다.</p>
                <div class="aTag_box">
                	<a class="log_a" href="/sssproj/auth/login.do" target="_blank">학생 로그인</a>
                	<a class="log_a" href="/sssproj/auth/regist.do" target="_blank">학생 회원가입</a>
                </div>
            </div>
            <div class="flex-item">
                <div class="item_img2">
                    <img src="../img/study_page.jpg" alt="학생 로그인">
                </div>
                <p>로그인이 안 돼있어도 어떤 식으로 공부하고
                    선생님과 소통하는지 미리 체험해 볼 수 있어요
                    하지만 비로그인 시 제한되는 기능이 많으니 로그인하는 걸 추천드려요
                </p>
                <a class="log_a2" href="/sssproj/learning/list.do" target="_blank">공부방 둘러보기</a> 
            </div>
            <div class="flex-item">
                <div class="item_img3">
                    <img src="../img/comunitiy.jpg" alt="학생 로그인">
                </div>
                <p>학생과 선생님 간의
                    질문 또는 소통을 위한
                    공간입니다.</p>
                <a class="log_a2" href="/sssproj/bbs/list.do" target="_blank">학생 커뮤니티</a>
            </div>
            <div class="flex-item">
                <div class="item_img4">
                    <img src="../img/teacher.jpg" alt="학생 로그인">
                </div>
                <p>쾌적한 학습을 할 수 있도록
                    환경을 제공해주는
                    관리자 로그인 창입니다.</p>
                <a class="log_a2" href="/sssproj/manager/login.do" target="_blank">관리자 로그인</a>
            </div>
          </div>
    </main>
    <footer>
        <a href="#"><img class="bottom_logo" src="../img/bottom_logo.svg" alt="푸터로고"></a>
    </footer>
</div>
</body>
</html>