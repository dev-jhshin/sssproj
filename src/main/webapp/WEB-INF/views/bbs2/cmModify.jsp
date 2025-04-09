<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/css/cmModify.css' />?v=<%=System.currentTimeMillis()%>" rel="stylesheet" type="text/css">
<%-- <link href="<c:url value='/css/cmModify.css?<%=new Date() %>' />" rel="stylesheet" type="text/css"> --%>
<title>커뮤니티 - 수정페이지</title>
<style>
</style>
</head>
<body>
	<div class="page-container">

		<!-- 메인 콘텐츠 -->
		<div class="main-content">

			<!-- 폼 콘텐츠 -->
			<form name="frmModify" id="frmModify" enctype="multipart/form-data">
				<input type="hidden" name="idx" value="${pMap.bbs.idx }"/>
				<input type="hidden" name="memberId" value="${pMap.bbs.memberId }"/>
				<div class="form-content">

					<div class="content-header">
						<img src="../img/header_logo.svg" class="logo">
						<h1>커뮤니티 게시물 수정</h1>
					</div>

					<div class="form-section">
						<div class="form-label">카테고리 선택</div>
						<div class="form-input select-group">
							<select id="categorySelect" class="category-select"
								name="category">
								<!--<option value="" disabled selected>자유 게시판 </option>  -->
								<option value="자유" selected>자유</option>
								<option value="시험정보">시험정보</option>
								<option value="시험후기">시험후기</option>
								<option value="자료">자료</option>
							</select>
						</div>
					</div>
					<div class="form-section">
						<div class="form-label">제목</div>
						<div class="form-input">
							<input type="text" placeholder="100자 이내로 작성해주세요."
								value="${pMap.bbs.bbsTitle }" name="title" maxlength="100">
						</div>
					</div>

					<!-- 콘텐츠 섹션 -->
					<div class="content-section">
						<textarea class="content-part" name="content" placeholder="내용을 입력하세요.">${pMap.bbs.bbsContent }</textarea>
					</div>

					<!-- 이미지 섹션 -->
					<div class="form-section">
						<div class="form-label">이미지 삭제</div>
						<div class="form-input file-input-container">
						<c:forEach items="${pMap.bbs.files }" var="file" varStatus="status">
							<input type="hidden" name="fileName_${status.index }" value="${file.fileName }"/>
							<input type="hidden" name="fileIdx_${status.index }" value="${file['fileIdx'] }"/>
							<img src="/sssproj/Uploads/${file.fileName }" width="50px" alt="${file.fileName }"/>
							<label class="delete-checkbox">
							    <input type="checkbox" name="deleteFileIdx" value="${status.index }" /> 삭제
							</label>
						</c:forEach>
						</div>
					</div>
					<!-- 이미지 섹션 -->
					<div class="form-section">
						<div class="form-label">이미지 첨부</div>
						<div class="form-input file-input-container">
							<input type="file" name="files" multiple />
						</div>
					</div>
				</div>

				<!-- 버튼 세트 -->
				<div class="btn-set">
					<button class="btn" id="cancelButton">취소</button>
					<input type="submit" class="btn" id="modifyButton" value="수정" />
				</div>
			</form>
		</div>
	</div>

<script>
       // 수정 버튼 
       const modifyButton = document.getElementById('modifyButton');
       modifyButton.addEventListener('click', ()=>{
       	if(confirm('해당 내용을 정말 수정하시겠습니까?')) {
           	const form = document.getElementById('frmModify');
           	form.method = "post";
           	form.action = "modify.do";
           	form.submit();
       	}
       });
       // 취소 버튼
       const cancelButton = document.getElementById('cancelButton');
       cancelButton.addEventListener("click", () => {
       	history.back();
       })
       // 이미지 삭제 버튼
       const imgDeleteButtons = document.querySelectorAll(".imgDeleteButton");
       imgDeleteButtons.forEach((imgDeleteButton)=> {
       	imgDeleteButton.addEventListener('click', () => {
       		console.log('clicked');
       	});
       })
	function deleteFile(btn, fileName) {
		console.log(fileName);
		if(confirm('정말 파일을 삭제하시겠습니까? 삭제한 파일은 복구되지 않습니다.')) {
	    	const frm = btn.form; // 버튼이 속한 form을 가져옴
	    	frm.action = "file/delete.do";
	    	frm.method="post";
	    	frm.submit();        		
		}
	}
   </script>
</body>
</html>