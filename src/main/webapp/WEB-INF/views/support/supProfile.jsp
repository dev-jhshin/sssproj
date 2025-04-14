<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${ not empty sessionScope.memberId }" >
<div class="profile">
	<div class="pro_cont">
		<h3>${sessionScope.memberId }님!</h3>
		<p>어서오세요. 오늘도 좋은하루 되세요.</p>
	</div>
</div>
<div class="pro_btn">
	<button id="logoutBtn"><i class="fa-solid fa-ban"></i>로그아웃</button>
</div>
</c:if>
<c:if test="${ empty sessionScope.memberId or sessionScope.memberId eq '' }">
<div class="profile">
	<div class="pro_cont">
		<h3>안녕하세요.</h3>
		<p>어서오세요. 오늘도 좋은하루 되세요.</p>
	</div>
</div>
<div class="pro_btn">
	<button id="loginBtn"><i class="fa-solid fa-user"></i>로그인</button>
</div>	
</c:if>
<script>
	const logoutBtn = document.getElementById('logoutBtn');
	if(logoutBtn) {
		logoutBtn.addEventListener('click', () => {
			window.location.href = '/sssproj/auth/logout.do';
		});
	}
	const loginBtn = document.getElementById('loginBtn');
	if (loginBtn) {
		loginBtn.addEventListener('click', () => {
			window.location.href = '/sssproj/auth/login.do';
		});
	}
</script>