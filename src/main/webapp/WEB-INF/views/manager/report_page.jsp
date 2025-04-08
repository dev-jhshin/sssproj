<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% Date d = new Date(); %>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html lang="ko_KR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/aa252fc318.js"
	crossorigin="anonymous"></script>
<link href="./../css/report_page.css?<%=d.getTime() %>" rel="stylesheet"
	type="text/css">
<title>SooP GonG 관리자 페이지</title>
</head>
<body>
	<c:set var="dUtil" value="<%=new CommonDateUtil() %>" />
	<div id="wrap">
		<header>
			<div class="page_logo">
				<img src="./../img/header_logo.svg" alt="로고">
			</div>
			<nav id="divMenu">
				<ul id="ulMenu">
					<li><a href="./memberList.do">회원 목록</a></li>
					<li><a href="./managerList.do">관리자 목록</a></li>
					<li><a href="./reportList.do">신고 내역</a></li>
					<li><a href="./inquiryList.do">문의 내역</a></li>
				</ul>
			</nav>
			<div class="profile">
				<div class="pro_cont">
					<h3>${sessionScope.managerId }</h3>
					<p>어서오세요. 오늘도 좋은하루 되세요.</p>
				</div>
			</div>
			<div class="pro_btn">
				<a href="./logout.do"><button>로그아웃</button></a>
			</div>
		</header>
		<form action="./reportResolution.do" method="post">
			<input type="text" name="reportIdx" value="${report.reportIdx}" style="display: none;" />
			<div id="content">
				<div class="listMain">
					<div class="mainTitle">
						<div class="mainType1">
							<p class="mainTypeName1">
								<i class="fa-solid fa-tree"></i>유형
							</p>
							<p class="mainTypeName2">${report.targetType}</p>
						</div>
						<div class="mainType2">
							<p class="mainReportName1">
								<i class="fa-solid fa-tree"></i>신고 번호
							</p>
							<p class="mainReportName2">${report.reportIdx}</p>
						</div>
						<div class="mainType3">
							<p class="mainWriteName1">
								<i class="fa-solid fa-tree"></i>신고 받은 번호
							</p>
							<a href="./reportPage.do?targetId=${report.targetId}&&targetType=${report.targetType}&&reportIdx=${report.reportIdx}"><p class="mainWriteName2">${report.targetId }</p></a>
						</div>
					</div>
					<div class="mainInfo">
						<div class="mainWriter1">
							<p class="mainWriterName1">
								<i class="fa-solid fa-tree"></i>작성자
							</p>
							<p class="mainWriterName2">${report.smemberId }</p>
						</div>
						<div class="mainWriter2">
							<p class="mainWriterDate1">
								<i class="fa-solid fa-tree"></i>작성날짜
							</p>
							<p class="mainWriterDate2">${dUtil.localDateTimeToString(report.reportCreatedAt) }</p>
						</div>
						<div class="mainContent">
							<p class="mainContent1">
								<i class="fa-solid fa-tree"></i>내용
							</p>
							<p class="mainContent2">${report.description }</p>
						</div>
						<div class="mainAnswer">
								<p class="mainAnswer1">
									<i class="fa-solid fa-tree"></i>답변
								</p>
								<div class="mainAnswer2">
									<c:choose>
										<c:when test ="${not empty report.rResolutionContent}" >
											<div class="answerBox1">
												<div class="reportIdx">신고 답변 번호 : ${report.rReportIdx}</div>
												<div class="reportAdmin">신고 답변한 매니저 : ${report.rManagerId } </div>
												<div class="reportDate">신고 답변한 날짜 : ${dUtil.localDateTimeToString(report.resolutionCreatedAt) }</div>
											</div>
											<textarea class="reportComment" name="rResolutionContent" readonly>${report.rResolutionContent }</textarea>
										</c:when>
										<c:otherwise>
											<textarea class="reportComment" name="rResolutionContent"></textarea>
										</c:otherwise>
									</c:choose>
								</div>
						</div>
						<div class="contentBtn">
							<button class="listBtn1" type="button" id="goList">
								<i class="fa-solid fa-list"></i>목록
							</button>
							<button class="listBtn2" type="submit">
								<i class="fa-solid fa-trash-can"></i>답변
							</button>
						</div>
					</div>
				</div>
			</div>
		</form>
<script>
document.getElementById('goList').addEventListener('click', function() {
    window.location.href = "./reportList.do";
});

</script>
</body>
</html>