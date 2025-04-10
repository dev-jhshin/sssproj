<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:set var="date" value="<%=new Date().getTime() %>" />
<link href="<c:url value='/css/msInfo.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/reportModal.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<title>나의학습 - 학습정보</title>
<style>

</style>
</head>

<body>
	<div id="modal">
	    <div class="modalwrap">
	        <div class="modalheader">
	            <div class="head_img">
	                <img src="../img/header_logo.svg">
	            </div>
	            <div class="head_noti">
	                <p>숲공 - 신고하기</p>
	                <p>신고하는 즉시 관리자에게 전송되며, 처리까지 1~2일 소요됩니다.</p>
	            </div>
	        </div>
	        <div class="modalcontent">
	            <div class="board">
	                <div class="boardContent">
	                    <p>내용</p>
	                </div>
	            </div>
	            <form name="frmRegist" id="frmRegist" action="./report/regist.do" method="post">
	                <input type="hidden" name="idx" value="${ dto.idx }"/>
	                <div class="titleMain">
	                    <textarea name="content" placeholder="내용을 입력하세요."></textarea>
	                </div>
	                <div class="btmwrap">
	                    <input class="endBtn" type="submit" value="작성완료">
	                    <a class="endBtn" href="" onclick="cancelReport()">취소</a>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	<c:set var="dUtil" value="<%=new CommonDateUtil() %>" />
    <div class="page-container">
        <!-- 사이드바 -->
        <c:import url="../sidebar.jsp"/>

        <!-- 메인 콘텐츠 -->
        <div class="main-content">
            <div class="content-header">나의 학습 - 학습정보</div>
               <form name="frmDelete" id="frmDelete" action="delete.do" method="post">
					<input type="hidden" name="idx" value="${ dto.idx }"/>
					<input type="hidden" name="memberId" value="${ dto.memberId }" />
				</form>
               <!-- 콘텐트 인포 -->
               <div class="content-info">
                  <div class="views">조회수 : ${ dto.viewCnt ne null and not empty dto.viewCnt ? dto.viewCnt : '0' }</div>
                  <div class="icon">
                     <img src="<c:url value='/img/public_icon.svg' />" class="public-icon">
                  </div>               
               </div>
               
            <!-- 인포 테이블 -->
            <table class="info-table">
                <tr>
                    <th>제목</th>
                    <td>${ dto.learningTitle }</td>
                    <td class="td-like-btn">
						<button class="like-btn inactive" id="likeButton" <c:if test="${ empty sessionScope.memberId }">disabled</c:if>>
							${ dto.isLiked ? '💚' : '♡' }
						</button>
						<span id="likeCount">${ dto.likeCnt ne null and not empty dto.likeCnt ? dto.likeCnt : '0' }</span>
					</td>
                </tr>
            </table>
            <table class="info-table">
                <tr>
                    <th width="20%">등록일</th>
                    <th width="40%">오늘의 학습 노출 여부</th>
                    <th width="40%">오늘의 학습 노출기간</th>
                </tr>
                <tr>
                    <td style="text-align: center">${ dUtil.localDateTimeToString(dto.createdAt) }</td>
                    <td style="text-align: center">${ dto.isVisible ? 'Y' : 'N' }</td>
                    <td style="text-align: center">
                    	<c:if test="${ dto.isVisible }">
					    	${ dUtil.localDateToString(dto.learningStartedAt) } ~ ${ dUtil.localDateToString(dto.learningEndedAt) }
					    </c:if>
                    </td>
                </tr>
            </table>
            
            <!-- 콘텐츠 섹션 -->
            <div class="content-section">
                <div class="content-part">
                    <p>${ dto.learningContent }</p>
                </div>
            </div>
            
				<!-- 이미지 섹션 -->
				<c:if test="${ not empty dto.files }">
					<div class="image-part">
						<div class="image-button-set">
							<div class="nav-button" id="btnPrev" onclick="movePrev()">
								<img src="<c:url value='/img/left_arrow.svg' />" />                  
							</div>
						</div>
						<div class="image-container">
							<div class="slider-wrapper">
								<c:forEach items="${ dto.files }" var="file" varStatus="status">
									<img src="<c:url value='/Uploads/${ file.fileName }' />" class="slide-image" onclick="openModal();currentSlide(${status.index})"/>
								</c:forEach>
							</div>
						</div>
						<div class="image-button-set">
							<div class="nav-button" id="btnNext" onclick="moveNext()">
								<img src="<c:url value='/img/right_arrow.svg' />">
							</div>
						</div>
					</div>
					
					<!-- 이미지 슬라이딩 -->
					<div class="image-swiper" id="imageSwiper">
						<c:forEach items="${dto.files}" var="file" varStatus="status">
							<div class="swiper-dot ${status.index == 0 ? 'active' : ''}" data-index="${status.index}"></div>
						</c:forEach>
					</div>
				</c:if>
				
         <!-- 분야 섹션 -->
            <div class="tags-section">
                <div class="tag-label">분야</div>
                <div class="tag-content">
                	<c:if test="${ not empty topics }">
                		<c:forEach items="${ topics }" var="topic">
                			<span class="hashtag">${ topic }</span>
                		</c:forEach>
                	</c:if>
                </div>
            </div>
                       
        <!-- 해시태그 섹션 -->
            <div class="tags-section">
                <div class="tag-label">해시태그</div>
                <div class="tag-content">
                	<c:if test="${ not empty hashtags }">
                		<c:forEach items="${ hashtags }" var="hashtag">
                			<span class="hashtag">#${ hashtag }</span>
                		</c:forEach>
                	</c:if>
                </div>
            </div>
            
            <!-- 공유된 사용자 섹션 -->
            <div class="tags-section">
                <div class="tag-label">공유된 사용자</div>
                <div class="tag-content"> <!-- shared-section 이었던 것... -->
                    <div class="shared-user-section">
                    	<c:if test="${ not empty dto.sharedList }" >
                    		<c:forEach items="${ dto.sharedList }" var="user">
		                    	<div class="shared-user">${ user.sharedToName } (${ user.sharedTo })</div>
		                    </c:forEach>
                    	</c:if>
                    </div>
                </div>
            </div>
             <!-- 댓글 섹션 -->
            <div class="comment-section">
               <div class="comment-list-section" id="commentList">
                 <!-- 댓글 작성 -->
	               <c:if test="${ not empty sessionScope.memberId }">
	               	   <form name="frmCommentReigst" id="frmCommentReigst" method="post">
		               		<div class="comment-input-section">
		               			  <input type="hidden" name="learningIdx" value="${ dto.idx }" />
				                  <input type="text" autocomplete="off" placeholder="댓글 내용을 입력하세요." class="comment-input" name="commentContent" id="commentContent"> 
				                  <input type="submit" class="comment-button" id="registCommentButton" value="등록" />
		               		</div>
		               </form>
	               </c:if>
               	 <c:choose>
               	 	<c:when test="${ empty dto.comments }">
               	 		<div class="comment-list">
							<div class="comment-content comment-notice">등록된 댓글이 없어요.. 😢</div>
						</div>
               	 	</c:when>
               	 	<c:otherwise>
               	 		<c:forEach items="${ dto.comments }" var="comment">
               	 			<form name="frmComment${comment.idx}" id="frmComment${comment.idx}" class="frmComment">
               	 				<div class="comment-list">
               	 					<input type="hidden" name="learningIdx" value="${ comment.learningIdx }" /> 
									<input type="hidden" name="commentIdx" value="${ comment.idx }" /> 
				                	<input type="hidden" name="memberId" value="${ comment.memberId }" /> 
				                	<div class="comment-header">
				                        <div class="comment-user-date">
				                           <div class="comment-user">${ comment.memberId }</div>
				                           <div class="comment-date">
				                           		${ dUtil.localDateTimeToString(comment.createdAt) }
				                           		<c:if test="${ not empty comment.updatedAt and (comment.createdAt ne comment.updatedAt) }">
													(수정: ${ dUtil.localDateTimeToString(comment.updatedAt) } )                          		
				                           		</c:if>
				                           </div>
				                        </div>
				                        <c:if test="${ sessionScope.memberId eq comment.memberId }">
				                        	<div class="comment-btn-set">
					                           <button type="button" class="comment-btn comment-modify-btn" id="modifyCommentButton" >편집</button>
					                           <button type="button" class="comment-btn comment-delete-btn" id="deleteCommentButton" >삭제</button>                        
					                        </div>
				                        </c:if>
				                     </div>
				                     <div class="comment-text">${ comment.commentContent }</div>
				                     <!-- 댓글 수정 -->
				                     <textarea class="comment-textarea" name="commentEdit">${ comment.commentContent }</textarea>
				                     <div class="comment-edit-btn">
				                     	<input type="button" value="저장" id="editCommentButton" />
				                     	<input type="button" value="취소" id="cancleCommentButton" />
				                     </div>
				                </div>
               	 			</form>
               	 		</c:forEach>
               	 	</c:otherwise>
               	 </c:choose>
            </div>
            
            <!-- 버튼 세트 -->
            <div class="btn-set">
                <button type="button" class="btn" id="listButton">목록</button>
                <c:if test="${ (not empty sessionScope.memberId) and (sessionScope.memberId ne dto.memberId) }">
				    <a href="#modal" class="btn" id="reportButton">신고</a>
				</c:if>
                <c:if test="${ sessionScope.memberId eq dto.memberId }">
					<button class="btn" id="modifyButton">수정</button>
					<button class="btn" id="deleteButton">삭제</button>	
				</c:if>
            </div>
        </div>
    </div>
 </div>   
    
<script>
	// URL 정리
	document.addEventListener('DOMContentLoaded', function() {
		const url = new URL(window.location.href);
		const idx = url.searchParams.get('idx');
		const hash = url.hash;
		
		history.replaceState({}, document.title, url.pathname + '?idx=' + idx + hash);
	});

	// 좋아요 하트
	const likeButton = document.getElementById('likeButton');
	likeButton.addEventListener('click', function() {
       	if (!${ dto.isLiked }) {
       		window.location.href = './like/regist.do?idx=${ dto.idx }';
       	} else {
       		window.location.href = './like/delete.do?idx=${ dto.idx }';
       	}
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

     // 등록 버튼 클릭 이동
     const registButton = document.getElementById('registButton');
     if (registButton) {
    	 registButton.addEventListener('click', function() {
	     	window.location.href = './regist.do';
	     });
     }
     
     
     // 수정 버튼 클릭 이동
     const modifyButton = document.getElementById('modifyButton');
     if (modifyButton) {
    	 modifyButton.addEventListener('click', function() {
	     	window.location.href = './modify.do?idx=${ dto.idx }';
	     });
     }
     
     // 삭제 버튼 클릭 이동
	const deleteButton = document.getElementById('deleteButton');
    if (deleteButton) {
    	deleteButton.addEventListener('click', () => {
    		if(confirm('정말 게시글을 삭제하시겠습니까? \n삭제된 게시글은 복구되지 않습니다.')) {
    			const frmDelete = document.getElementById('frmDelete');
    			frmDelete.submit();
    		}
    	});
    }
	
	// 댓글 버튼
	const registCommentButton = document.getElementById('registCommentButton');
	if (registCommentButton) {
		registCommentButton.addEventListener('click', function(e) {
			e.preventDefault();
			e.stopPropagation();
			
			const frm = document.getElementById('frmCommentReigst');
			const content = frm.commentContent.value;
			if (content == null || content.length < 1) {
				alert("내용을 입력하세요.");
				return;
			}
			
			frm.action = './comment/regist.do'
			frm.submit();
		});
	}
	const deleteCommentButton = document.querySelectorAll('#deleteCommentButton');
	if (deleteCommentButton) {
		deleteCommentButton.forEach(btn => {
			btn.addEventListener('click', () => {
				if(confirm('정말 댓글을 삭제하시겠습니까?')) {
					const frm = btn.closest('form');
					frm.action = './comment/delete.do';
					frm.method = 'post';
					frm.submit();
				}
			});
		});
	}
	const modifyCommentButton = document.querySelectorAll('#modifyCommentButton');
	if (modifyCommentButton) {
		modifyCommentButton.forEach(btn => {
			btn.addEventListener('click', function() {
				const comment = this.closest('.comment-list');
				const text = comment.querySelector('.comment-text');
				const textarea = comment.querySelector('.comment-textarea');
				const btn = comment.querySelector('.comment-edit-btn');
				
				text.style.display = 'none';
				textarea.style.display = 'block';
				btn.style.display = 'block';
			});
		});
	}
	const editCommentButton = document.querySelectorAll('#editCommentButton');
	if (editCommentButton) {
		editCommentButton.forEach(btn => {
			btn.addEventListener('click', function() {
				const comment = this.closest('.comment-list');
				const text = comment.querySelector('.comment-text');
				const textarea = comment.querySelector('.comment-textarea');
				const btn = comment.querySelector('.comment-edit-btn');
				
				const frm = this.closest('.frmComment');
				const content = frm.commentEdit.value;
				if (content == null || content.length < 1) {
					alert("내용을 입력하세요.");
					return;
				}
				
				text.value = textarea.value;
				
				text.style.display = 'block';
				textarea.style.display = 'none';
				btn.style.display = 'none';
				
				frm.action = './comment/modify.do?';
				frm.method = 'post';
				frm.submit();
			});
		});
	}
	const cancleCommentButton = document.querySelectorAll('#cancleCommentButton');
	if (cancleCommentButton) {
		cancleCommentButton.forEach(btn => {
			btn.addEventListener('click', function() {
				const comment = this.closest('.comment-list');
				const text = comment.querySelector('.comment-text');
				const textarea = comment.querySelector('.comment-textarea');
				const btn = comment.querySelector('.comment-edit-btn');
				
				textarea.value = text.textContent;
				
				text.style.display = 'block';
				textarea.style.display = 'none';
				btn.style.display = 'none';
			});
		});
	}
	
	// 신고 버튼 
	const reportButton = document.getElementById('reportButton');
	if(reportButton) {
		reportButton.addEventListener('click', () => {
			const modal = document.getElementById('modal');
			modal.style.display = 'block';
		});
	}
	
	function cancelReport() {
		const modal = document.getElementById('modal');
		modal.style.display = 'none';
	}
	
	// 이미지 슬라이더
	document.addEventListener('DOMContentLoaded', function () {
		let currentIndex = 0;
		const sliderWrapper = document.querySelector('.slider-wrapper');
		const imageWidth = 200;
		const slideImages = document.querySelectorAll('.slide-image');
		const dots = document.querySelectorAll('.swiper-dot');
		
		document.getElementById('btnPrev').addEventListener('click', movePrev);
		document.getElementById('btnNext').addEventListener('click', moveNext);
		
		dots.forEach((dot, index) => {
			dot.addEventListener('click', function () {
				currentIndex = index;
				updateSlider();
			});
		});

		function movePrev() {
			if (currentIndex > 0) {
				currentIndex--;
			} else {
				currentIndex = slideImages.length - 1;
			}
			updateSlider();
		}

		function moveNext() {
			if (currentIndex < slideImages.length - 1) {
				currentIndex++;
			} else {
				currentIndex = 0;
			}
			updateSlider();
		}

		function updateSlider() {
			sliderWrapper.style.transform = 'translateX(-' + (currentIndex * imageWidth) + 'px)';
			updateDots();
		}

		function updateDots() {
			dots.forEach((dot, index) => {
				if (index === currentIndex) {
					dot.classList.add('active');
					dot.style.opacity = '1';
				} else {
					dot.classList.remove('active');
					dot.style.opacity = '0.3';
				}
			});
		}
		updateSlider();
	});
	
 
	// 이미지 모달
	let modalIndex = 0;
	
	function openModal() {
		document.getElementById('imageModal').style.display = 'block';
		showModalSlides(modalIndex);
	}

	function closeModal() {
		document.getElementById('imageModal').style.display = 'none';
	}

	function currentSlide(n) {
		modalIndex = n;
		showModalSlides(modalIndex);
	}

	function changeModalSlide(step) {
		const images = document.querySelectorAll('.modal-image');
		modalIndex += step;

		if (modalIndex >= images.length) {
			modalIndex = 0;
		} else if (modalIndex < 0) {
			modalIndex = images.length - 1;
		}
		
		showModalSlides(modalIndex);
	}

	function showModalSlides(n) {
		const images = document.querySelectorAll('.modal-image');

		for (let i = 0; i < images.length; i++) {
			images[i].style.display = 'none';
		}

		images[n].style.display = 'block';
	}
	

	document.addEventListener('keydown', function(event) {
		if (event.key === 'Escape') {
			closeModal();
		}
	});
</script> 
</body>
</html>