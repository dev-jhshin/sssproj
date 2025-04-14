<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 내역 상세</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
  body {
	font-family: 'Arial', sans-serif;
	background-color: #f9f9f9;
	padding: 20px;
	margin: 0;
  }

  .container {
	max-width: 700px;
	margin: 0 auto;
	background: #fff;
	padding: 24px;
	box-shadow: 0 4px 12px rgba(0,0,0,0.05);
	border-radius: 12px;
  }

  h2 {
	font-size: 22px;
	margin-bottom: 24px;
	border-bottom: 1px solid #ddd;
	padding-bottom: 12px;
  }

  .field {
	margin-bottom: 16px;
  }

  .field label {
	font-weight: bold;
	display: block;
	margin-bottom: 6px;
	color: #444;
  }

  .field div {
	background-color: #f5f5f5;
	padding: 10px 14px;
	border-radius: 6px;
	word-break: break-word;
  }

  .btn-area {
	text-align: right;
	margin-top: 30px;
  }

  button, input[type="button"], input[type="submit"] {
	background-color: #3478f6;
	color: white;
	border: none;
	padding: 10px 20px;
	margin-left: 10px;
	font-size: 14px;
	border-radius: 6px;
	cursor: pointer;
  } 
   
  input[type="submit"] {
 	background-color: #660000;
	color: white;
  }

  button:hover, input[type="button"]:hover, input[type="submit"]:hover {
	background-color: #245ec4;
  }

  input[type="hidden"] {
	display: none;
  }

  @media screen and (max-width: 600px) {
	.container {
	  padding: 16px;
	}

	button, input[type="submit"],input[type="button"]  {
	  width: 100%;
	  margin: 10px 0 0 0;
	}

	.btn-area {
	  text-align: center;
	}
  }
</style>
</head>
<body>
	<div class="container">
		<c:set var="dUtil" value="<%=new CommonDateUtil() %>" />
		<c:choose>
			<c:when test="${empty dto or dto.targetId == 0}">
				<h2>삭제 처리 완료</h2>
				<div class="field">
					<div>삭제 처리 완료되었습니다.</div>
				</div>
				<input type="hidden" id="reportId" value="${reportIdx}" />
				<div class="btn-area">
					<input type="button" id="back" value="뒤로가기" />
				</div>
			</c:when>
			<c:otherwise>
				<h2>신고 상세 내역</h2>
				<form action="./reportDelete.do" method="post">
					<input type="hidden" name="targetId" value="${dto.targetId}" />
					<input type="hidden" name="targetType" value="${targetType}" />
					<input type="hidden" id="reportId" name="reportId" value="${reportIdx}" />
		
					<div class="field">
						<label>게시물 타입</label>
						<div>${targetType}</div>
					</div>
					<div class="field">
						<label>게시물 ID</label>
						<div>${dto.targetId}</div>
					</div>
					<div class="field">
						<label>신고자 ID</label>
						<div>${dto.rmemberId}</div>
					</div>
					<div class="field">
						<label>신고 제목</label>
						<div>${dto.reportTitle}</div>
					</div>
					<div class="field">
						<label>신고 내용</label>
						<div>${dto.reportContent}</div>
					</div>
					<div class="field">
						<label>최종 수정일</label>
						<div>	
							<c:choose>
								<c:when test="${not empty dto and not empty dto.reportUpdatedAt}">
									${dUtil.localDateTimeToString(dto.reportUpdatedAt)}
								</c:when>
								<c:otherwise>
									정보 없음
								</c:otherwise>
							</c:choose>
						</div>
					</div>
		
					<div class="btn-area">
						<input type="submit" name="delete" value="삭제하기"/>
						<input type="button" id="back" value="뒤로가기"/>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
	
	<script>
		document.getElementById("back").addEventListener("click", function() {
			const reportId = document.getElementById("reportId").value;
			window.location.href = "./reportDetail.do?idx=" + reportId;
		});
	</script>
</body>
</html>
