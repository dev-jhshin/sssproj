<%@page import="net.fullstack10.common.CommonUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
로그인완료

<% String user_id = (String) session.getAttribute("memberId");
	CommonUtil cUtil = new CommonUtil();
	cUtil.getCookieInfo(request, "memberId");
%>
<%=user_id %>
</body>
</html>