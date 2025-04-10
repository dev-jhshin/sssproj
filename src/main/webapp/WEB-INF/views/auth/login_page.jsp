<%@page import="net.fullstack10.common.CommonUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<% Date d = new Date(); %>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
    <link href="./../css/loginPgStyle.css?<%=d.getTime() %>" rel="stylesheet" type="text/css">
    <title>SooP GonG 로그인</title>
</head>
<%
CommonUtil cUtil = new CommonUtil();
String saveIdFlag = cUtil.getCookieInfo(request, "saveIdFlag");
String saveId = cUtil.getCookieInfo(request, "saveId");
%>
<body>
<div class="wrap">
    <header>
        <img class="header_logo" src="./../img/header_logo.svg" alt="메인로고">
        <p class="header_comment">원하는 목표를 위해 오늘도 화이팅!</p>
    </header>
    <main>
        <div class="login_content">
            <form class="login_form" action="/sssproj/auth/login.do" method="post" onsubmit="return validateForm()">
                <div class="input_div one">
                       <div class="i">
                          <i class="fas fa-user"></i>
                    </div>
                    <div class="div">
                          <h5>아이디</h5>
                          <input type="text" class="input" name="memberId" autocomplete="off" value=<%= saveIdFlag.equals("Y") ? saveId : "" %>>
                     </div>
                </div>
                <div class="input_div pass">
                    <div class="i"> 
                        <i class="fas fa-lock"></i>
                    </div>
                    <div class="div">
                        <h5>비밀번호</h5>
                        <input id="password" type="password" class="input" autocomplete="off" name="memberPwd">
                        <button id="togglePassword" type="button">😁</button>
                    </div>
                </div>
                <div class="save_id">
                    <p>아이디 저장</p><input name="saveId" type="checkbox" id="save_id" value="Y" <%=saveIdFlag.equals("Y") ? "checked" : "" %>> 
                </div>
                <input type="submit" class="btn" value="Login">
                <div class="regi_chgpwd_div">
                    <a href="/sssproj/auth/regist.do">회원가입</a>
                    <a href="/sssproj/auth/findPwdChoice.do">비밀번호 찾기</a>
                </div>
            </form>
        </div>
    </main>
</div>
<script>
    const inputs = document.querySelectorAll(".input");

    function addcl(){
        let parent = this.parentNode.parentNode;
        parent.classList.add("focus");
    }

    function remcl(){
        let parent = this.parentNode.parentNode;
        if(this.value == ""){
            parent.classList.remove("focus");
        }
    }

    inputs.forEach(input => {
        input.addEventListener("focus", addcl);
        input.addEventListener("blur", remcl);
    });

    // 비밀번호 눈 아이콘 클릭시 비밀번호 표시 및 아이콘 변경
    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordInput = document.getElementById('password');
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        this.textContent = type === 'password' ? '😁' : '😴'; // 아이콘 변경
    });
</script>
</body>
</html>