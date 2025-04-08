<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/css/cmInfo.css' />?v=<%=System.currentTimeMillis()%>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/reportModal.css' />?v=<%=System.currentTimeMillis()%>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?<%=new Date() %>' />" rel="stylesheet" type="text/css">

<title>커뮤니티 - 상세정보</title>
</head>
<style>
</style>
<body>
	<c:set var="dUtil" value="<%=new CommonDateUtil() %>"/>
	<div class="page-container">
		<!-- 사이드바 -->
		<c:import url="../sidebar.jsp"/>

		<!-- 메인 콘텐츠 -->
		<div class="main-content">
			<c:import url="../reportModal.jsp"/>
			<div class="div1200">
			<div class="header-section">
			    <div class="content-header">커뮤니티 </div>
			    <div class="content-header-nav"><a href="/sssproj/bbs/list.do">커뮤니티</a> > <a href="/sssproj/bbs/list.do?category=${bbs.bbsCategory }">${bbs.bbsCategory }게시판</a></div>
			    
			</div>
			<form name="frmDelete" id="frmDelete" action="delete.do" method="post">
				<input type="hidden" name="bbs_idx" value="${bbs.idx }"/>
			</form>
			<table class="info-table">
				<tr>
					<th width="20%">제목</th>
					<td >${bbs.bbsTitle }</td>
					<td class="td-like-btn">
						<button class="like-btn inactive" id="likeButton" <c:if test="${empty sessionScope.memberId }">disabled</c:if>>
							<c:if var="isLike" test="${bbs.like eq 'true' }">
								💚
							</c:if>
							<c:if test="${not isLike }">♡
							</c:if>
						</button> <span id="likeCount">${bbs.likeCnt }</span>
					</td>
				</tr>
			</table>
			<table class="info-table">
                <tr>
                    <th width="20%">작성자</th>
                    <td width="30%">${bbs.memberId }</td>
                    <th width="20%">작성일</th>
                    <td width="30%" class="td-one-line">${fn:replace( bbs.createdAt, 'T', ' ') }
					<c:if test="${not empty bbs.updatedAt }" >
						<c:if test="${not (bbs.createdAt eq bbs.updatedAt) }">
							(수정: ${fn:replace( bbs.updatedAt, 'T', ' ') })
						</c:if>
					</c:if>
					</td>
                </tr>
            </table>

			<!-- 콘텐츠 섹션 -->
			<div class="content-section">
				<div class="content-part">
					<p>${bbs.bbsContent }</p>
				</div>
			</div>

			<c:if test="${not empty bbs.files }">
				<div class="image-part">
					<c:forEach items="${bbs.files }" var="file">
						<img src="../Uploads/${file.fileName }" width="200px"/>
					</c:forEach>
				</div>
			</c:if>
			<!-- 댓글 섹션 -->
			<div class="comment-section">
				<div class="comment-list-section" id="commentList">
					<!--  댓글 조회 부분  -->
					<c:if test="${empty bbs.comments }" >
						<div class="comment-list">
							<div class="comment-content comment-notice">등록된 댓글이 없어요.. 😢</div>
						</div>
					</c:if>
					<c:forEach items="${bbs.comments }" var="comment">
						<form name="frmComment${comment.idx}" id="frmComment${comment.idx}" class="frmComment">
							<div class="comment-list">
								<div class="comment-header">
									<input type="hidden" name="bbs_idx" value="${bbs.idx }" /> 
									<input type="hidden" name="comment_idx" value="${comment['idx'] }" /> 
									<input type="hidden" name="comment_memberId" value="${comment['memberId'] }" /> 
									<div class="comment-user">${comment['memberId'] }</div>
									<div class="comment-date">
										${comment['createdAt'] } 
										<c:if test="${not empty comment['updatedAt'] }" >
											<c:if test="${not (comment['createdAt'] eq comment['updatedAt']) }">
												(수정: ${comment['updatedAt']})
											</c:if>
										</c:if>
										<c:if test="${sessionScope.memberId eq comment.get('memberId') }">
											<input type="button" class="comment-btn" style="border: 0px;" id="commentEditButton" value="편집" onclick="enableEdit(this)" />
											<input type="button" class="comment-btn" style="border: 0px;" id="commentDeleteButton" value="삭제"/>
										</c:if>
									</div>
								</div>
								<div class="comment-text">${comment.get('commentContent') }</div>
								<div class="comment-content"><textarea class="edit-textarea" style="display:none; width:100%;" name="comment_content">${comment.get('commentContent') }</textarea></div>
								<div class="edit-actions" style="display:none;">
									<input type="button" value="저장" onclick="saveEdit(this)" />
									<input type="button" value="취소" onclick="cancelEdit(this)" />
								</div>
							</div>
						</form>
					</c:forEach>
					<!--  댓글 작성 부분  -->
					<div class="comment-on-section">
					<c:if test="${not empty sessionScope.memberId }">
					<form name="frmCommentRegist" action="comment/regist.do"
							method="post">
						<div class="comment-input-section">
								<input type="hidden" name="bbs_idx" value="${bbs.idx }" /> 
								<input type="text" placeholder="댓글 내용을 입력하세요." class="comment-input" name="comment_content" id="comment_content" autocomplete="off">
								<input type="submit" class=" comment-button btn" id="commentButton" value="등록" />
						</div>
					</form>
					</c:if>
				</div>
				</div>
			</div>
				<!-- 버튼 세트 -->
				<div class="btn-set">
					<!-- <button class="btn" id="listButton">목록</button> -->
					
					<c:if test="${ (not empty sessionScope.memberId) and not (sessionScope.memberId eq bbs.memberId) }">
				    	<a href="#modal" class="btn" id="reportButton">신고</a>
					</c:if>
					<c:if test="${ sessionScope.memberId eq bbs.memberId }">
						<button class="btn" id="modifyButton">수정</button>
						<button class="btn" id="deleteButton">삭제</button>	
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<script>
		// 좋아요 하트
		document.addEventListener('DOMContentLoaded', function() {
			const likeButton = document.getElementById('likeButton');
			const likeCount = document.getElementById('likeCount');
			let isLiked = ${isLike};
			likeButton.addEventListener('click', function() {
				isLiked = !isLiked;
				updateLikeButton();
			});

			function updateLikeButton() {
				if (isLiked) {
					// 좋아요 추가 로직 
					window.location.href="like/regist.do?idx=${ bbs.idx}";
					likeButton.innerHTML = '💚';
					likeButton.classList.add('active');
					likeButton.classList.remove('inactive');
					likeCount.textContent = parseInt(likeCount.textContent) + 1;
				} else {
					// 좋아요 삭제 로직 
					window.location.href="like/delete.do?idx=${ bbs.idx}";
					likeButton.innerHTML = '♡';
					likeButton.classList.add('inactive');
					likeButton.classList.remove('active');
					if (parseInt(likeCount.textContent) > 0) {
						likeCount.textContent = parseInt(likeCount.textContent) - 1;
					}
				}
			}
		});
		// 목록 버튼 클릭 이동
		/* const listButton = document.getElementById('listButton');
		listButton.addEventListener('click', function() {
			window.location.href = 'list.do';
		}); */

		// 수정 버튼 클릭 이동
		const modifyButton = document.getElementById('modifyButton');
		if(modifyButton) {
			modifyButton.addEventListener('click', function() {
				window.location.href = 'modify.do?idx=${bbs.idx}';
			});
		}
		
		// 게시글 삭제 버튼 클릭 이벤트 
		const deleteButton = document.getElementById('deleteButton');
		if (deleteButton) {
			deleteButton.addEventListener('click', () => {
				if(confirm('정말 게시글을 삭제하시겠습니까? \n삭제된 게시글은 복구되지 않습니다.')) {
					const frmDelete = document.getElementById('frmDelete');
					frmDelete.submit();
				}
			});
		}
		const commentDeleteButton = document.getElementById('commentDeleteButton');
		if (commentDeleteButton) {
			commentDeleteButton.addEventListener('click', () => {
				if(confirm('정말 댓글을 삭제하시겠습니까?')) {
					const form = document.getElementById('frmComment');
					form.action = "/sssproj/bbs/comment/delete.do";
					form.method="post";
					form.submit();
				}			
			});
		}
		// 신고 버튼 
		const reportButton = document.getElementById('reportButton');
		if(reportButton) {
			reportButton.addEventListener('click', () => {
				const modal = document.getElementById('Modal');
				modal.style.display = 'block';
			});
		}
		function enableEdit(editBtn) {
			const commentList = editBtn.closest('.comment-list');
			const text = commentList.querySelector('.comment-text');
			const textarea = commentList.querySelector('.edit-textarea');
			const actions = commentList.querySelector('.edit-actions');
		
			text.style.display = 'none';
			textarea.style.display = 'block';
			actions.style.display = 'block';
		}
		
		function cancelEdit(cancelBtn) {
			const commentList = cancelBtn.closest('.comment-list');
			const text = commentList.querySelector('.comment-text');
			const textarea = commentList.querySelector('.edit-textarea');
			const actions = commentList.querySelector('.edit-actions');
		
			textarea.value = text.textContent; // 원래 내용 복구
			text.style.display = 'block';
			textarea.style.display = 'none';
			actions.style.display = 'none';
		}
		
		function saveEdit(saveBtn) {
			const commentList = saveBtn.closest('.comment-list');
			const text = commentList.querySelector('.comment-text');
			const textarea = commentList.querySelector('.edit-textarea');
			const actions = commentList.querySelector('.edit-actions');
		
			const newContent = textarea.value;
			text.textContent = newContent;
		
			text.style.display = 'block';
			textarea.style.display = 'none';
			actions.style.display = 'none';
		
			const form = saveBtn.closest('form');
			form.action = "/sssproj/bbs/comment/modify.do";
			form.method="post";
			form.submit();
		}
	</script>
</body>
</html>