<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri ="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./../css/emailChoice.css" rel="stylesheet" type="text/css">
    <title>SooP GonG 비밀번호 찾기</title>
</head>
<body>
<div class="wrap">
    <header>
        <a href="./findPwdChoice.do"><img class="header_logo" src="./../img/header_logo.svg" alt="메인로고"></a>
        <p class="header_comment">"비밀번호 찾기 페이지입니다."</p>
    </header>
    <main>
        <form id="fpw_form"class="fpw_form" action="./pwdChange" method="post">
        	<input type="text" id="serverCode" value="<%= request.getAttribute("code") != null ? request.getAttribute("code") : "" %>" style="display:none">
            <div class="fpw_input_box"> 
                <p class="fpw_tit">가입 한 아이디</p>
                <input type="text" name="memberId" autocomplete="off" id="username" class="fpw_input" placeholder="가입하신 아이디를 입력해주세요."  value="<%= request.getAttribute("memberId") != null ? request.getAttribute("memberId") : "" %>">
                <input type="hidden" id="idCheck" name="idCheck"  autocomplete="off"
                value="<c:choose> 
                			<c:when test="${accord == 1}">valid</c:when> 
                			<c:otherwise>invalid</c:otherwise>
				</c:choose>"/>
                <div id="usernameError" class="iderror">
    			<c:if test="${accord == 1}">
       	 			<span style="color: green;">이메일 확인후 인증번호 입력하세요.</span>
    			</c:if>
            	</div>
            </div>
            <div class="fpw_input_box2"> 
                <p class="fpw_tit">이메일</p>
                <input type="text" class="fpw_input2" id="userEmail" autocomplete="off"  name="security_answers[]" placeholder="답변 입력" required value="<%= request.getAttribute("memberEmail") != null ? request.getAttribute("memberEmail") : "" %>">
                <input type="button" id="emailCheck" autocomplete="off" class="tempw_checked" value="입력">
            </div>
            <div class="fpw_input_box2"> 
                <p class="fpw_tit">인증번호 확인</p>
                <input type="text" id="confirmCode" class="fpw_input2" autocomplete="off" name="security_answers[]" placeholder="답변 입력" required>
                <input type="button" id="tempw_checked" autocomplete="off" class="tempw_checked" value="인증 확인">
            
            </div>
        </form>
    </main>
</div>
<script>
//아이디 체크 로직
document.getElementById("emailCheck").addEventListener("click", function() {
	  const memberId = document.getElementById("username").value;
	  const memberEmail = document.getElementById("userEmail").value;
	  window.location.href = "./idCheck.do?member_id=" + memberId +"&&member_email="+memberEmail;
	});

  
document.getElementById("tempw_checked").addEventListener("click", function() {
    const confirmCode = document.getElementById("confirmCode").value;
    const serverCode = document.getElementById("serverCode").value;
    const memberId = document.getElementById("username").value;

    if(confirmCode === serverCode){
//     	check = 1;
        alert("인증완료");
        window.location.href ="./pwdChange?member_id="+memberId;
    } else {
        alert("인증실패");
    }
});

</script>
</body>
</html>