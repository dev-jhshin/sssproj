<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/css/cmRegist.css?<%=new Date()%>'/>"
	rel="stylesheet" type="text/css">
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
				<input type="hidden" name="idx" value="${pMap.bbs.idx }" />
				<input type="hidden" name="memberId" value="${pMap.bbs.memberId }" />
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
								<c:forEach items="${categories}" var="category">
									<option value="${category }">${category }</option>
								</c:forEach>
								<option value="직접입력">직접입력</option>
							</select> <input type="text" id="customCategoryInput"
								name="customCategory" placeholder="카테고리를 입력하세요"
								style="display: none; margin-left: 5px;" autocomplete="off" />
						</div>
					</div>
					
					<div class="form-section">
						<div class="form-label">제목</div>
						<div class="form-input">
							<input type="text" placeholder="100자 이내로 작성해주세요."
								value="${pMap.bbs.bbsTitle }" name="title" maxlength="100"
								required />
						</div>
					</div>

					<!-- 콘텐츠 섹션 -->
					<div class="content-section">
						<textarea class="content-part" name="content"
							placeholder="내용을 입력하세요." required>${pMap.bbs.bbsContent }</textarea>
					</div>

					<!-- 이미지 삭제 섹션 -->
					<div class="form-section">
						<c:if test="${not empty pMap.bbs.files }">
							<div class="form-label">이미지 삭제</div>
							
							<div class="form-input file-input-container">
								<c:forEach items="${pMap.bbs.files }" var="file"
									varStatus="status">
									<input type="hidden" name="fileName_${status.index }"
										value="${file.fileName }" />
									<input type="hidden" name="fileIdx_${status.index }"
										value="${file['fileIdx'] }" />
									<img src="/sssproj/Uploads/${file.fileName }" width="50px"
										alt="${file.fileName }" />
									<label> <input type="checkbox" name="deleteFileIdx"
										value="${status.index }" /> 삭제
									</label>
								</c:forEach>
							</div>
						</c:if>
					</div>
					
					<!-- 이미지 섹션 -->
					<div class="form-section">
						<div class="form-label">이미지 첨부</div>
						<div class="form-input">
							<button type="button" class="file-select-btn">파일선택</button>
							<input type="file" name="files" id="fileInput" accept="image/*"
								style="display: none;" multiple>

							<!-- 파일 목록만 표시 -->
							<div class="file-list">
								<div class="no-files">선택된 파일 없음</div>
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
				const dataTransfer = new DataTransfer();
				selectedFiles.forEach(file => dataTransfer.items.add(file));
				document.getElementById('fileInput').files = dataTransfer.files;
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
		
		// 파일 다중 선택 
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
		
		// 파일 리스트 업데이트 
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
		
		// 카테고리 동적 추가
		document.addEventListener("DOMContentLoaded", function() {
			const categorySelect = document.getElementById("categorySelect");
			const customInput = document.getElementById("customCategoryInput");

			categorySelect.addEventListener("change", function() {
				if (categorySelect.value === "직접입력") {
					customInput.style.display = "block";
				} else {
					customInput.style.display = "none";
					customInput.value = ""; // 다른 카테고리 선택 시 input 초기화
				}
			});
		});
	</script>
</body>
</html>