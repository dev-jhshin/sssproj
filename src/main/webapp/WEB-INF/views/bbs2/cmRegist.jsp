<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<%@page import="net.fullstack10.common.CommonDateUtil"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <link href="../css/cmRegist.css" rel="stylesheet" type="text/css"> -->
<link href="<c:url value='/css/cmRegist.css?<%=new Date() %>' />" rel="stylesheet" type="text/css">
<title>커뮤니티 - 등록페이지</title>

</head>
<body>
	<div class="page-container">
	
		<!-- 메인 콘텐츠 -->
		<div class="main-content">
		
			<!-- 폼 콘텐츠 -->
			<form name="frmRegist" method="post" action="regist.do" enctype="multipart/form-data">
				<div class="form-content">

					<div class="content-header">
						<img src="../img/header_logo.svg" class="logo">
						<h1>커뮤니티 게시물 등록</h1>
					</div>

					<div class="form-section">
						<div class="form-label">카테고리 선택</div>
						<div class="form-input select-group">
							<select id="categorySelect" class="category-select" name="category">
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
							<input type="text" id="title" name="title"
								placeholder="100자 이내로 작성하세요." maxlength="100" />
						</div>
					</div>

					<!-- 콘텐츠 섹션 -->
					<div class="content-section">
						<textarea class="content-part" id="content" name="content" placeholder="내용을 입력하세요." wrap="hard"></textarea>
					</div>

					<div class="form-section">
						<div class="form-label">이미지 첨부</div>
						<div class="form-input">
						<button type="button" class="file-select-btn">파일선택</button>
						<input type="file" name="files" id="fileInput" accept="image/*" style="display: none;" multiple>
					
						<!-- 파일 목록만 표시 -->
						<div class="file-list">
							<div class="no-files">선택된 파일 없음</div>
						</div>
					</div>
				</div>
				<!-- 버튼 세트 -->
				<div class="btn-set">
					<input type="submit" class="btn" id="registButton" value="등록" />
					<!-- <button class="btn" id="registButton">등록</button> -->
					<button type="button" class="btn" id="cancelButton">취소</button>
					<button type="button" class="btn" id="listButton">목록</button>
				</div>
		</form>
		</div>


	</div>

	<script>
	let selectedFiles = [];
	   
	   document.querySelector('.file-select-btn').addEventListener('click', function() {
	       document.getElementById('fileInput').click();
	   });
	   
	   // 파일 선택 시 파일 목록 업데이트
	   document.getElementById('fileInput').addEventListener('change', function(e) {
	       if (e.target.files.length > 0) {
	      
	           const newFiles = Array.from(e.target.files);
	           selectedFiles = selectedFiles.concat(newFiles);
	          
	           updateFileList();
	       }
	   });
	   
	   function updateFileList() {
	       const fileList = document.querySelector('.file-list');
	       fileList.innerHTML = '';
	       
	       if (selectedFiles.length === 0) {
	           const noFilesDiv = document.createElement('div');
	           noFilesDiv.className = 'no-files';
	           noFilesDiv.textContent = '선택된 파일 없음';
	           fileList.appendChild(noFilesDiv);
	           return;
	       }
	       
	       selectedFiles.forEach((file, index) => {
	           const fileItem = document.createElement('div');
	           fileItem.className = 'file-item';
	           
	           const fileName = document.createElement('div');
	           fileName.className = 'file-name';
	           fileName.textContent = file.name;
	           
	           const removeButton = document.createElement('div');
	           removeButton.className = 'remove-file-btn';
	           removeButton.textContent = '×';
	           removeButton.dataset.index = index;
	           
	           fileItem.appendChild(fileName);
	           fileItem.appendChild(removeButton);
	           fileList.appendChild(fileItem);
	       });
	   }
	// 파일 삭제 
       document.addEventListener('click', function(event) {
           if (event.target.classList.contains('remove-file-btn')) {
               const index = parseInt(event.target.dataset.index);

               selectedFiles = selectedFiles.filter((_, i) => i !== index);

               updateFileList();
           }
       });
        // 등록버튼 alert
        const registButton = document.getElementById('registButton');
        registButton.addEventListener('click', function(){
        	if(confirm('해당 학습내용을 등록하시겠습니까?')) {
        		const form = document.getElementById("frmRegist");
        		form.submit();
        	}
        	
        });
        
        // 취소 버튼 alert
        const cancelButton = document.getElementById('cancelButton');
        cancelButton.addEventListener('click', function() {
            if(confirm(' 정말로 작성을 취소하시겠습니까? 작성 중인 내용은 저장되지 않습니다.')) {
                window.location.href='list.do';
            }
        });
    </script>
</body>
</html>