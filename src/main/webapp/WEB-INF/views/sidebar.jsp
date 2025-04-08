<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<div class="sidebar">
	<img src="../img/header_logo.svg">
	<div class="sidebar-list"><a href="/sssproj/auth/myinfo.do">나의 정보</a></div>
	<div class="sidebar-list"><a href="/sssproj/learning/today.do">오늘의 학습</a></div>
	<div class="sidebar-list"><a href="/sssproj/learning/list.do">모두의 학습</a></div>
	<div class="sidebar-list"><a href="/sssproj/learning/my_list.do">나의학습</a></div>
	<div class="sidebar-list"><a href="/sssproj/learning/shared_list.do">공유학습</a></div>
	<div class="sidebar-list"><a href="/sssproj/bbs/list.do">커뮤니티</a></div>

	<div class="downsidebar">
		<c:if var="isLogin" test="${empty sessionScope.memberId }">
			<div><a href="/sssproj/auth/login.do">로그인</a></div>
		</c:if>
		<c:if test="${not isLogin }" >
			<div><a href="/sssproj/auth/logout.do">로그아웃</a></div>
		</c:if>
		<div><a href="/sssproj/support/faq.do">고객센터</a></div>
	</div>
</div>
