<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<% Date d = new Date(); %>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/aa252fc318.js" crossorigin="anonymous"></script>
    <link href="./../css/adminLoginStyle.css?<%=d.getTime() %>" rel="stylesheet" type="text/css">
    <title>SooP GonG 관리자 로그인</title>
</head>
<body>
<div class="wrap">
    <header>
        <img class="header_logo" src="./../img/header_logo.svg" alt="메인로고">
        <p class="header_comment">학생분들의 깨끗한 환경을 책임지시는 관리진 페이지입니다.</p>
    </header>
    <main>
        <div class="login_content">
            <form class="login_form" action="/sssproj/manager/login.do" method="post" onsubmit="return validateForm()">
                <div class="input_div one">
                       <div class="i">
                          <i class="fas fa-user"></i>
                    </div>
                    <div class="div">
                          <h5>아이디</h5>
                          <input type="text" class="input" name="managerId">
                     </div>
                </div>
                <div class="input_div pass">
                    <div class="i"> 
                        <i class="fas fa-lock"></i>
                    </div>
                    <div class="div">
                        <h5>비밀번호</h5>
                        <input type="password" class="input" name="managerPwd">
                    </div>
                </div>
                <input type="submit" class="btn" value="Login">
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
</script>
</body>
</html>