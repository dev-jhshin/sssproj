<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SP - 게시글 작성</title>
    
    <link href="<c:url value='/css/writerPageStyle.css?<%=new Date()%>' />" rel="stylesheet" type="text/css">
    <script src="https://kit.fontawesome.com/2d74121aef.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="modal">
        <div class="modalheader">
            <div class="head_img">
                <img src="../../img/header_logo.svg">
            </div>
            <div class="head_noti">
                <p>숲공 - 문의하기</p>
                <p>문의하는 즉시 관리자에게 전송되며, 답변은 1~2일 소요됩니다.</p>
            </div>
        </div>
        <div class="modalcontent">
            <div class="board">
                <div class="boardTitle">
                    <p>제목</p>
                </div>
                <div class="boardCont">
                    <p>내용</p>
                </div>
                <div class="boardImg">
                    <p>이미지 첨부</p>
                </div>
            </div>
                <form name="frmRegist" action="regist.do" method="post">
                    <div class="titleMain">
                        <input type="text" name="title" id="title" placeholder="100자 이내로 작성하세요." required>
                    </div>
                    <div class="contentMain">
                        <textarea name="content"></textarea>
                    </div>
                    <div class="imgMain">
                        <input type="file" id="file"> 
                    </div>
                    <div class="lastBtn">
                        <input class="endBtn" type="submit"  value="작성완료">
                        <input class="cancelBtn" type="button" value="취소">
                    </div>
                </form>
        </div>
    </div>
    <script>
    document.querySelector('.cancelBtn').addEventListener('click', function() {
        window.location.href = "./list.do";
    });
    
    </script>
    
</body>
</html>