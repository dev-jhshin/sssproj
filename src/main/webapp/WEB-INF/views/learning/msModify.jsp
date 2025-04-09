<%@page import="net.fullstack10.learning.LearningSharedDTO"%>
<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<% Date d = new Date(); %>
<c:set var="date" value="<%=new Date().getTime() %>" />
<link href="<c:url value='/css/msModify.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<title>나의학습 - 학습수정</title>
<style>

</style>
</head>

<body>
	<c:set var="dUtil" value="<%=new CommonDateUtil() %>" />
    <div class="page-container">
        <!-- 사이드바 -->
        <c:import url="../sidebar.jsp"/>

        <!-- 메인 콘텐츠 -->
        <div class="main-content">
            <div class="content-header">나의 학습 - 학습수정</div>

			<form name="frmModify" id="frmModify" action="./modify.do" method="post" enctype="multipart/form-data">
				<input type="hidden" name="idx" value="${ dto.idx }"/>
				<input type="hidden" name="memberId" value="${ dto.memberId }" />
				<table class="info-table">
	                <tr>
	                    <th>제목</th>
	                    <td><input type="text" name="learningTitle" autocomplete="off" placeholder="제목을 입력하세요." maxlength="100" value="${ dto.learningTitle }"/></td>
	                </tr>
	            </table>
	
	            <table class="info-table">
	                <tr>
	                    <th width="20%">등록일</th>
	                    <th width="40%">오늘의 학습 노출 여부</th>
	                    <th width="40%">오늘의 학습 노출기간</th>
	                </tr>
	                <tr>
	                    <td style="text-align: center">
	                        <fmt:formatDate value="${ dUtil.toDateTime(dto.createdAt) }" pattern="yyyy-MM-dd" /></td>
	                    </td>
	                    <td style="text-align: center">
	                        <div class="checkbox-container">
	                            <label>
	                                <input type="radio" name="isVisible" value="Y" ${ dto.isVisible ? 'checked' : '' } /> 노출(Y)
	                            </label>
	                            &nbsp;&nbsp;&nbsp;
	                            <label>
	                                <input type="radio" name="isVisible" value="N" ${ dto.isVisible ? '' : 'checked' } /> 노출 안함(N)
	                            </label>
	                        </div>
	                    </td>
	                    <td style="text-align: center">
		                    <input type="date" class="date-input" name="learningStartedAt" disabled value="${ dto.learningStartedAt ? dUtil.localDateToString(dto.learningStartedAt) : '' }"/> 
		                    &nbsp;&nbsp;~ &nbsp;&nbsp;
		                    <input type="date" class="date-input" name="learningEndedAt" disabled value="${ dto.learningEndedAt ? dUtil.localDateToString(dto.learningEndedAt) : '' }"/>
	                    </td>
	                </tr>
	            </table>
	            
	            <!-- 콘텐츠 섹션 -->
	            <div class="content-section">
	                <textarea class="content-part" name="learningContent" placeholder="내용을 입력하세요~~">${ dto.learningContent }</textarea>
	            </div>
	            
	            <!-- 이미지 섹션 -->
	            <div class="form-section">
	                <div class="form-label">이미지 첨부</div>
	                <div class="form-input">
	                    <button type="button" class="file-select-btn">파일선택</button>
	                    <input type="file" name="files" id="fileInput" accept="image/*" style="display:none;" multiple>
	                    
	                    <!-- 업로드 파일 목록 표시 -->
	                    <div class="form-input file-input-container">
							<c:forEach items="${ dto.files }" var="file" varStatus="status">
								<input type="hidden" name="fileName_${ status.index }" value="${ file.fileName }"/>
								<input type="hidden" name="fileIdx_${ status.index }" value="${ file.idx }"/>
								<img src="/sssproj/Uploads/${ file.fileName }" width="50px" alt="${ file.fileName }"/>
								<label>
								    <input type="checkbox" name="deleteFileIdx" value="${ status.index }" /> 삭제
								</label>
							</c:forEach>
						</div>
	                    
	                    <!-- 파일 목록 표시 -->
	                    <div class="file-list">
	                    	<div class="no-files">선택된 파일 없음</div>
	                    </div>
	                </div>
	            </div>
	            
	            <!-- 분야 섹션 -->
	            <div class="tags-section">
	                <div class="tag-label">분야</div>
	                <div class="field-content">
	                	<c:if test="${ not empty topics }">
	                		<c:forEach items="${ topics }" var="topic">
	                			<span class="hashtag">${ topic }</span>
	                		</c:forEach>
	                	</c:if>
	                	
	                	<input type="hidden" name="topics" />
	                    <input type="text" autocomplete="off" class="field-input" placeholder="분야 입력 (10자 이내, ','로 구분하여 4개까지 등록 가넝)" maxlength="10"/>
	                    <button type="button" class="add-field-btn">추가</button>
	                </div>
	            </div>
	            
	            <!-- 해시태그 섹션 -->
	            <div class="tags-section">
	                <div class="tag-label">해시태그</div>
	                <div class="hashtag-content">
	                	<c:if test="${ not empty hashtags }">
	                		<c:forEach items="${ hashtags }" var="hashtag">
	                			<span class="hashtag">#${ hashtag }</span>
	                		</c:forEach>
	                	</c:if>
	                	
	                	<input type="hidden" name="hashtags" />
	                    <input type="text" autocomplete="off" class="hashtag-input" placeholder="해시태그 입력 (10자 이내, '#'으로 구분하여 4개까지 등록 가넝)" maxlength="10"/>
	                    <button type="button" class="add-hashtag-btn">추가</button>
	                </div>
	            </div>
	
	            <!-- 공유된 사용자 섹션 -->
	            <div class="tags-section">
	                <div class="tag-label">공유된 사용자</div>
	                <div class="tag-content"> <!-- shared-section 이었던 것... -->
	                    <div class="shared-user-section">
	                    	<input type="hidden" name="addShared" />
	                    	<input type="hidden" name="deleteShared" />
	                        <button type="button" class="share-button" id="shareButton">공유하기</button>
	                        
	                        <c:if test="${ not empty dto.sharedList }" >
	                    		<c:forEach items="${ dto.sharedList }" var="user">
			                    	<div class="shared-user" data-member-id="${ user.sharedTo }">
			                    		${ user.sharedToName } (${ user.sharedTo }) x
			                    	</div>
			                    </c:forEach>
	                    	</c:if>
	                     </div>
	                </div>
	            </div>
	            
	            <!-- 공유하기 팝업창 -->
	            <div id="sharePopup" class="share-popup">
	                <div class="share-popup-content">
	                    <div class="share-popup-header">
	                        <h3>사용자 검색</h3>
	                        <span class="close-btn" id="closeSharePopup">&times;</span>
	                    </div>
	                    <div class="share-popup-body">
	                        <div class="search-container">
	                            <input type="text" id="userSearchInput" placeholder="사용자 검색...">
	                            <button type="button" id="searchUserBtn">검색</button>
	                        </div>
	                        <div class="search-results"></div>
	                        <div class="selected-users">
	                            <h4>선택된 사용자</h4>
	                            <div id="selectedUsersList"></div>
	                        </div>
	                    </div>
	                    <div class="share-popup-footer">
	                        <button type="button" id="confirmShareBtn">확인</button>
	                        <button type="button" id="cancelShareBtn">취소</button>
	                    </div>
	                </div>
	            </div>
	            
	            <!-- 공개/비공개 섹션 -->
	            <div class="public-section">
					<label>
						<input type="radio" name="isPublic" value="Y" ${ dto.isPublic ? 'checked' : '' }/> 공개(Y)
					</label>
					&nbsp;&nbsp;&nbsp;
					<label>
							<input type="radio" name="isPublic" value="N" ${ dto.isPublic ? '' : 'checked' }/> 비공개(N)
					</label>
				</div>
	            
	            <!-- 버튼 세트 -->
	            <div class="btn-set">
	                <button class="btn" type="button" id="listButton">목록</button>
	                <button class="btn" type="button" id="cancleButton">취소</button>
	                <button class="btn" type="button" id="modifyButton">수정</button>
	            </div>
			</form>
            

        </div>
    </div>

    <script>
        // 오늘의 학습 노출 여부
        document.querySelectorAll('input[name="isVisible"]').forEach(radio => {
            radio.addEventListener('change', function() {
                const dateInputs = document.querySelectorAll('input[type="date"].date-input');
                if (this.value === 'Y') {
                    dateInputs.forEach(input => input.removeAttribute('disabled'));
                } else {
                    dateInputs.forEach(input => input.setAttribute('disabled', 'disabled'));
                }
            });
        });
        
        // 분야 추가
        document.querySelector('.add-field-btn').addEventListener('click', function() {
        	 const tagInput = document.querySelector('.field-input');
             const tagText = tagInput.value;
             
             const invalidPattern = /[^ㄱ-ㅎ가-힣a-zA-Z_]/;
             
             if (tagText) {
             	if (invalidPattern.test(tagText)) {
             		alert('분야에는 공백, 숫자, 특수문자(언더스코어 제외)를 포함할 수 없습니다.');
             		tagInput.value = null;
             		return;
             	}
      
                 const tagContainer = document.querySelector('.field-content');
                 const existingTags = tagContainer.querySelectorAll('.field');
                 
                 if (existingTags.length < 4) {
                     const tagElement = document.createElement('span');
                     tagElement.className = 'field';
                     tagElement.style.cursor = 'pointer';
                     
                     const tagName = document.createElement('span');
                     tagName.className = 'tagName';
                     tagName.textContent = tagText;
                     
                     const tagButton = document.createElement('span');
                     tagButton.textContent = ' x';
                     
                     tagElement.addEventListener('click', function () {
                     	tagElement.remove();
                     });
                     
                     tagElement.appendChild(tagName);
                     tagElement.appendChild(tagButton);
                     
                     tagContainer.insertBefore(tagElement, tagInput);
                     tagInput.value = null;
                 } else {
                     alert('분야는 최대 4개까지만 등록할 수 있습니다.');
                 }
             }
        });
        
        
        // 해시태그 추가 
        document.querySelector('.add-hashtag-btn').addEventListener('click', function() {
        	const tagInput = document.querySelector('.hashtag-input');
            const tagText = tagInput.value;
            
            const invalidPattern = /[^ㄱ-ㅎ가-힣a-zA-Z_]/;
            
            if (tagText) {
            	if (invalidPattern.test(tagText)) {
            		alert('분야에는 공백, 숫자, 특수문자(언더스코어 제외)를 포함할 수 없습니다.');
            		tagInput.value = null;
            		return;
            	}
            	
                const tagContainer = document.querySelector('.hashtag-content');
                const existingTags = tagContainer.querySelectorAll('.hashtag');
                
                if (existingTags.length < 4) {
                    const tagElement = document.createElement('span');
                    tagElement.className = 'hashtag';
                    tagElement.style.cursor = 'pointer';

                    const tagName = document.createElement('span');
                    tagName.className = 'tagName';
                    tagName.textContent = '#' + tagText;
                    
                    const tagButton = document.createElement('span');
                    tagButton.textContent = ' x';
                    
                    tagElement.addEventListener('click', function () {
                    	tagElement.remove();
                    });
                    
                    tagElement.appendChild(tagName);
                    tagElement.appendChild(tagButton);
                    
                    tagContainer.insertBefore(tagElement, tagInput);
                    tagInput.value = null;
                } else {
                    alert('해시태그는 최대 4개까지만 등록할 수 있습니다.');
                }
            }
        });
        
        // 이미지 관련 
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
        
     	// 공유 기능
     	let sharedList = [
     		<c:forEach var="sharedInfo" items="${ dto.sharedList }" varStatus="status">
     			{
     				memberId: "${ fn:escapeXml(sharedInfo.sharedTo) }",
     				memberName: "${ fn:escapeXml(sharedInfo.sharedToName) }",
     			}<c:if test="${ !status.last }">,</c:if>
     		</c:forEach>
     	];
        let newSharedList = sharedList;
        
        // 공유된 사용자 삭제
        document.querySelectorAll('.shared-user').forEach(item => {
        	item.addEventListener('click', function() {
        		item.remove();
    			newSharedList = newSharedList.filter(u => u.memberId != item.dataset.memberId);
        	});
        });
        
		// 공유하기 버튼 팝업창
		const shareButton = document.getElementById('shareButton');
        const sharePopup = document.getElementById('sharePopup');
        const closeSharePopup = document.getElementById('closeSharePopup');
        const cancelShareBtn = document.getElementById('cancelShareBtn');

		shareButton.addEventListener('click', function() {
            sharePopup.style.display = 'block';
            
            const container = document.querySelector('.search-results');
            container.innerText = '';
            
            newSharedList.forEach(user => {
            	const userText = user.memberName + " (" + user.memberId + ")";
            	addSelectedUser(user, userText);
            });
        });
        
        closeSharePopup.addEventListener('click', function() {
            sharePopup.style.display = 'none';
        });
        
        cancelShareBtn.addEventListener('click', function() {
            sharePopup.style.display = 'none';
        });

		// 유저 검색
		document.getElementById('searchUserBtn').addEventListener('click', function () { 
			const container = document.querySelector('.search-results');
        	container.innerText = '';

			const keyword = document.getElementById('userSearchInput');
        	const params = new URLSearchParams();
        	params.append("keyword", encodeURIComponent(keyword.value));
        	
        	keyword.value = null;

			fetch('../user/list.do', {
				method: "POST",
        		headers: {
        			"Content-Type": "application/x-www-form-urlencoded",
        		},
        		body: params.toString()
			})
			.then(response => response.json())
			.then(data => {
				if (data.length == 0) {
					container.innerText = '사용자가 없습니다.';
        			return;
				}

				data.forEach(user => {
					const userText = user.memberName + " (" + user.memberId + ")";

					const item = document.createElement('div');
        			item.className = 'user-item';
					item.dataset.memberId = user.memberId;

					const member = document.createElement('span');
        			member.textContent = userText;

					const addButton = document.createElement('button');
        			addButton.className = 'add-user-btn';
        			addButton.type = 'button';
					addButton.textContent = '추가'

					if (newSharedList.some(u => u.memberId == user.memberId)) {
						addButton.disabled = true;
                    	addButton.textContent = '추가됨';
					}

					addButton.addEventListener('click', function() {
        				addSelectedUser(user, userText);
        				this.disabled = true;
                        this.textContent = '추가됨';
        			});

					item.appendChild(member);
        			item.appendChild(addButton);
        			
        			container.appendChild(item);
				});
			})
		});

		// 선택된 사용자에 추가
		function addSelectedUser(user, userText) {
			const container = document.getElementById('selectedUsersList');

			const userDiv = document.createElement('div');
            userDiv.className = 'selected-user-item';
            
            const userSpan = document.createElement('span');
            userSpan.textContent = userText;

			const removeBtn = document.createElement('button');
            removeBtn.className = 'remove-user-btn';
            removeBtn.textContent = '삭제';

			removeBtn.addEventListener('click', function() {
                userDiv.remove();
				newSharedList = newSharedList.filter(u => u.memberId != user.memberId);
                
                const item = document.querySelector('.user-item[data-member-id="' + user.memberId + '"]');
                const addBtn = item.querySelector('.add-user-btn');
                addBtn.disabled = false;
                addBtn.textContent = '추가';
            });

			userDiv.appendChild(userSpan);
            userDiv.appendChild(removeBtn);
			container.appendChild(userDiv);

			const { memberId, memberName } = user;
			newSharedList.push({ memberId, memberName });
		}

		// 공유 확인 버튼
		confirmShareBtn.addEventListener('click', function() {
			const container = document.querySelector('.shared-user-section');
			container.querySelectorAll('.shared-user').forEach(node => node.remove());
			
			newSharedList.forEach(user => {
				const userText = user.memberName + " (" + user.memberId + ")";
				
				const userDiv = document.createElement('div');
				userDiv.className = 'shared-user';
				userDiv.textContent = userText + ' x';
				userDiv.style.cursor = 'pointer';

				userDiv.addEventListener('click', function() {
					userDiv.remove();
					newSharedList = newSharedList.filter(u => u.memberId != user.memberId);
				});

				container.appendChild(userDiv);
			});

			sharePopup.style.display = 'none';
		});
        
        // 등록버튼 alert
        const modifyButton = document.getElementById('modifyButton');
        modifyButton.addEventListener('click', function(){
        	if (!confirm('해당 학습내용을 등록하시겠습니까?')) return;
        	
			const frm = document.getElementById("frmModify");
        	
        	const topics = [...document.querySelectorAll('.field .tagName')].map(topic => topic.textContent.trim());
        	const tags = [...document.querySelectorAll('.hashtag .tagName')].map(tag => tag.textContent.replace('#', '').trim());
        	
        	// 기존 공유 목록과 새 공유 목록 비교
        	const sharedArray = sharedList.map(item => item.memberId);
        	const newSharedArray = newSharedList.map(item => item.memberId);
        	
        	const added = newSharedArray.filter(id => !sharedArray.includes(id));
        	const removed = sharedArray.filter(id => !newSharedArray.includes(id));
        	
        	
        	// hidden input value 설정
        	document.querySelector('input[name="topics"]').value = topics.join(',');
        	document.querySelector('input[name="hashtags"]').value = tags.join(',');
        	
			document.querySelector('input[name="addShared"]').value = added.join(',');
			document.querySelector('input[name="deleteShared"]').value = removed.join(',');

        	frm.submit();
        });
        
     	// 목록 버튼 클릭 이동
        const listButton = document.getElementById('listButton');
        listButton.addEventListener('click', function() {
        	const history = "${ sessionScope.redirectURL }";
        	if (history && history != "") {
        		window.location.href = history;
        	} else {
        		window.location.href = "./list.do"
        	}
        });
        
     	// 취소 버튼 클릭 이동
        const cancleButton = document.getElementById('cancleButton');
        cancleButton.addEventListener('click', function() {
        	window.location.href ="./view.do?idx=${ dto.idx }"
        });
    </script> 
</body>

</html>