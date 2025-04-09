<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% Date d = new Date(); %>
<!DOCTYPE html>
<html lang="ko_KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./../css/registStyle.css?<%=d.getTime() %>" rel="stylesheet" type="text/css">
    <title>SooP GonG 회원가입</title>
</head>
<body>
<div class="wrap">
    <header>
        <a href="./../home/WelcomeSoop.do"><img class="header_logo" src="./../img/header_logo.svg" alt="메인로고"></a>
        <p class="header_comment">"숲속처럼 편안한 여러분만의 공부방 숲공에 오신 걸 환영해요."</p>
    </header>
    <main>
        <form id="regi_form"class="regi_form" action="/sssproj/auth/regist.do" method="post">
            <div class="regi_input_box"> 
                <p class="regi_tit">아이디</p>
                <input type="text" name="memberId" id="username" autocomplete="off" class="regi_input1"  value="<%= request.getAttribute("savedId") != null ? request.getAttribute("savedId") : "" %>">
                <input type="button" name="idVali" id="idVali"  autocomplete="off" class="idVali" value="중복확인">
                <input type="hidden" id="idCheck" name="idCheck"  autocomplete="off"
                value="<c:choose> 
                			<c:when test="${duplicate == 1}">valid</c:when> 
                			<c:otherwise>invalid</c:otherwise>
				</c:choose>"/>
                <div id="usernameError" class="iderror">
    			<c:if test="${duplicate == 1}">
       	 			<span style="color: green;">사용 가능한 아이디입니다.</span>
    			</c:if>
    			<c:if test="${duplicate == 0}">
        			<span style="color: red;">사용 불가능한 아이디입니다.</span>
    			</c:if>
			</div>
            </div>
            <div class="regi_input_box"> 
                <label for="password" class="regi_tit">비밀번호</label>
                <input type="password" name="memberPwd" id="password" class="regi_input2" placeholder="8~16자 미만의 영문 소문자, 숫자, 특수문자 포함" maxlength="20">
                <button id="togglePassword" type="button">👁️</button>
                <div id="passwordError" class="pwerror"></div>
            </div>
            <div class="regi_input_box"> 
                <p class="regi_tit">비밀번호 확인</p>
                <input type="password" id="confirmPassword" name="비밀번호 확인" class="regi_input" autocomplete="off">
                <div id="confirmPasswordError" class="conerror"></div>
            </div>
            <div class="regi_input_box2"> 
                <p class="regi_tit">비밀번호 찾기 질문</p>
                <select id="select_qu" name="security_questions[]">
                    <option value="" disabled selected>질문을 선택해주세요</option>
                    <option value="1">당신의 학창시절 별명은 무엇인가요?</option>
                    <option value="2">기억의 남는 게임 한 가지</option>
                    <option value="3">출신 고등학교 이름은 무엇인가요?</option>
                    <option value="4">음식을 무엇을 제일 좋아하나요?</option>
                    <option value="5">살면서 처음 해 본 게임 이름</option>
                    <option value="6">제일 재밌게 봤었던 영화 이름 한 가지</option>
                    <option value="7">출신 초등학교 이름은 무엇인가요?</option>
                    <option value="8">제일 행복했었던 나이는 몇 살인가요?</option>
                    <option value="9">살면서 제일 힘들었었던 나이는 몇 살인가요?</option>
                    <option value="10">기억에 남는 버스 번호는 몇 번인가요?</option>
                </select>
                <input type="text" class="regi_input" autocomplete="off" name="answer" placeholder="답변 입력(20글자 내로 입력하세요!)" required>
            </div>
            <div class="regi_input_box"> 
                <p class="regi_tit">이름</p>
                <input type="text" name="memberName" class="regi_input" maxlength="10" autocomplete="off" required> 
            </div>
            <div class="regi_input_box"> 
                <p class="regi_tit">생년월일</p>
                <input type="date" name="memberBirthdate" class="regi_input" id="birthdate" name="birthdate" required>
            </div>
            <div class="regi_input_box3"> 
                <p class="regi_tit">이메일</p>
                <input class="select_email" type="text" name="memberEmail" autocomplete="off" required>
                <p class="email_between">@</p>
                <select id="select_email1" name="select_email">
                    <option value="" disabled selected>도메인을 선택하세요</option>
                    <option value="naver.com">naver.com</option>
                    <option value="hanmail.net">hanmail.net</option>
                    <option value="hotmail.com">hotmail.com</option>
                    <option value="nate.com">nate.com</option>
                    <option value="korea.com">korea.com</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="hanmir.com">hanmir.com</option>
                    <option value="paran.com">paran.com</option>
                    <option value="direct">직접 입력</option>
                    <input type="text" class="customDomain" id="customDomain" name="customDomain" placeholder="직접 입력" style="display:none;">
                </select>
                <input id="emailCheck" class="emailCheck" type="submit" name="" value="인증하기">
            </div>
            <div id="emailError" class="error"></div>
            <div class="regi_input_box">
                <p class="regi_tit">이메일 인증</p>
                <input type="text" name="memberId" id="username" autocomplete="off" class="regi_input1"  value="<%= request.getAttribute("savedId") != null ? request.getAttribute("savedId") : "" %>">
                <input type="button" name="idVali" id="idVali"  autocomplete="off" class="idVali" value="중복확인">
            </div>
            <div class="regi_input_box"> 
                <p class="regi_tit">성별</p><br>
                <div class="sex_choice">
                    <label class="radio_style">
                        <input type="radio" name="memberGender" value="MALE">
                        <span>남자</span>
                    </label>
                    <label class="radio_style">
                        <input type="radio" name="memberGender" value="FEMALE">
                        <span>여자</span>
                    </label>
                </div> 
                <div id="genderError" class="generror"></div>
            </div>
            <div class="btm_line"></div>
            <input id="checkKey" class="regi_btn" type="submit" name="regi_btn" value="회원가입하기">
        </form>
    </main>
    <footer>
        <a href="#"><img class="bottom_logo" src="./../img/bottom_logo.svg" alt="푸터로고"></a>
    </footer>
</div>
<script>
    // 이메일 직접입력 선택시 입력 칸 생성 스크립트
    document.getElementById('select_email1').addEventListener('change', function() {
        const customDomain = document.getElementById('customDomain');
        if (this.value === 'direct') {
            customDomain.style.display = 'inline';
            customDomain.required = true; // 직접 입력 필드 필수로 설정
        } else {
            customDomain.style.display = 'none';
            customDomain.required = false; // 직접 입력 필드 선택 해제
        }
    });

    document.getElementById('regi_form').addEventListener('submit', function(event) {
        let valid = true;

        // 아이디 유효성 검사
        const username = document.getElementById('username').value;
        const usernameError = document.getElementById('usernameError');
        usernameError.textContent = '';

        const usernameRegex = /^[a-z0-9]{5,20}$/; // 영어 소문자 및 숫자, 5~20자
        if (!usernameRegex.test(username)) {
            usernameError.textContent = '아이디는 5~20자 사이의 영어 소문자와 숫자만 사용할 수 있습니다.';
            valid = false;
        }
        console.log("valid 값:", valid); // 디버깅용 콘솔 출력
        const idCheck = document.getElementById('idCheck').value;
        if (idCheck !== 'valid') {
            usernameError.textContent = '아이디 중복 확인을 해주세요.';
            valid = false;
        }

        // 비밀번호 유효성 검사
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const passwordError = document.getElementById('passwordError');
        passwordError.textContent = '';

        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/; // 영어+숫자+특수문자, 8~16자
        if (!passwordRegex.test(password)) {
            passwordError.textContent = '비밀번호는 8~16자 사이의 영어, 숫자, 특수문자만 사용할 수 있습니다.';
            valid = false;
        }

        // 비밀번호 확인
        if(password !== confirmPassword) {
            confirmPasswordError.textContent = '비밀번호가 일치하지 않습니다.';
            vaild = false;
        }

        // 성별 유효성 검사
        const gender = document.querySelector('input[name="memberGender"]:checked');
        const genderError = document.getElementById('genderError');
        genderError.textContent = '';

        if (!gender) {
            genderError.textContent = '성별을 선택해야 합니다.';
            valid = false;
        }

        if (!valid) {
            event.preventDefault(); // 폼 제출 방지
        }
    });
	//아이디 중복확인 루틴
	document.getElementById('idVali').addEventListener('click',function(){
		const username = document.getElementById('username').value;
		const usernameRegex = /^[a-z0-9]{5,20}$/; // 영어 소문자 및 숫자, 5~20자
		const usernameError = document.getElementById('usernameError');
	    usernameError.textContent = '';
		
	    if (!usernameRegex.test(username)) {
	            usernameError.textContent = '아이디는 5~20자 사이의 영어 소문자와 숫자만 사용할 수 있습니다.';
	            document.getElementById('username').focus();
	            return;
	        }
	    window.location.href = "./idDuplicate.do?member_id=" + username;
	});
    // 전화번호 입력시 자동으로 "-" 입력 
//     function oninputPhone(target) {
//         target.value = target.value
//             .replace(/[^0-9]/g, '')
//             .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
//     }

    // 비밀번호 눈 아이콘 클릭시 비밀번호 표시 및 아이콘 변경
    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordInput = document.getElementById('password');
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        this.textContent = type === 'password' ? '👁️' : '🙈'; // 아이콘 변경
    });
    
</script>
</body>
</html>