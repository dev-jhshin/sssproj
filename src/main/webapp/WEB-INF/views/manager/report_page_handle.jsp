<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="dUtil" value="<%=new CommonDateUtil() %>" />
<c:choose>
    <c:when test="${empty dto or dto.targetId == 0}">
        <div>삭제 처리 완료되었습니다.</div>
        <input type="hidden" id="reportId" value="${reportIdx}" />
        <input type="button" id="back" value="뒤로가기"/>
    </c:when>
    <c:otherwise>
        <form action="./reportDelete.do" method="post">
            <input type="text" name="targetId" value="${dto.targetId}" style="display: none;" />
            <input type="text" name="targetType" value="${targetType}" style="display: none;" />
            <input type="text" id="reportId" name="reportId" value="${reportIdx}" style="display: none;" />

            <div>
              <label>게시물 타입:</label><br/>
              <div>${targetType}</div>
            </div>
            <div>
              <label>게시물 ID:</label><br/>
              <div>${dto.targetId}</div>
            </div>
            <div>
              <label>신고자 ID:</label><br/>
              <div>${dto.rmemberId}</div>
            </div>
            <div>
              <label>신고 제목:</label><br/>
              <div>${dto.reportTitle}</div>
            </div>
            <div>
              <label>신고 내용:</label><br/>
              <div>${dto.reportContent}</div>
            </div>
            <div>
              <label>최종 수정일:</label><br/>
              <c:choose>
                <c:when test="${not empty dto and not empty dto.reportUpdatedAt}">
                  <div>${dUtil.localDateTimeToString(dto.reportUpdatedAt)}</div>
                </c:when>
                <c:otherwise>
                  <div>정보 없음</div>
                </c:otherwise>
              </c:choose>
            </div>

            <input type="submit" name="delete" value="삭제하기"/>
            <input type="button" id="back" value="뒤로가기"/>
        </form>
    </c:otherwise>
</c:choose>

</body>
<script>
document.getElementById("back").addEventListener("click", function() {
	const reportId = document.getElementById("reportId").value;
  	window.location.href = "./reportDetail.do?idx=" + reportId;
});
</script>
</html>