<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="jakarta.tags.core"%> --%>
<div id="modal">
    <div class="modalwrap">
        <div class="modalheader">
            <div class="head_img">
                <img src="../img/header_logo.svg">
            </div>
            <div class="head_noti">
                <p>숲공 - 신고하기</p>
                <p>신고하는 즉시 관리자에게 전송되며, 처리까지 1~2일 소요됩니다.</p>
            </div>
        </div>
        <div class="modalcontent">
            <div class="board">
                <div class="boardContent">
                    <p>내용</p>
                </div>
            </div>
            <form name="frmRegist" id="frmRegist" action="/sssproj/bbs/report/regist.do" method="post">
                <input type="hidden" name="idx" value="${bbs.idx}"/>
                <div class="titleMain">
                    <textarea name="content" placeholder="내용을 입력하세요."></textarea>
                </div>
                <div class="btmwrap">
                    <input class="endBtn" type="submit" value="작성완료">
                    <a class="cancelBtn endBtn" href="#">취소</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
	const endBtn = document.querySelector(".endBtn");
	endBtn.addEventListener('click', () => {
		window.location.href="list.do";
	})
	
</script>