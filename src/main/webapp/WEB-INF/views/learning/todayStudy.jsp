<%@page import="net.fullstack10.common.CommonDateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<c:set var="date" value="<%=new Date().getTime() %>" />
<link href="<c:url value='/css/todayStudy.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/sidebar.css?ver=${ date }' />" rel="stylesheet" type="text/css">
<title>오늘의 학습</title>
<style>

</style>
</head>
<body>
	<div class="page-container">
	  	<!-- 사이드바 -->
	  	<c:import url="../sidebar.jsp"/>
		
	  	<!-- 메인 콘텐츠 -->
	  	<div class="main-content">
	  		<div class="content-header">오늘의 학습</div>
	   	 	<div class="date-selection">
		      	<div class="selected-date"></div>
		      	<div class="calendar-icon" id="calendarToggle"></div>
		    </div>
	
		    <!-- 주간 캘린더 -->
		    <div class="week-container">
		      	<div class="week-day" data-date="2025-03-31">
		        	<div class="day-name">월</div>
		        	<div class="day-number">31</div>
		      	</div>
		      	<div class="week-day" data-date="2025-04-01">
		        	<div class="day-name">화</div>
		        	<div class="day-number">1</div>
		      	</div>
			    <div class="week-day" data-date="2025-04-02">
			        <div class="day-name">수</div>
			        <div class="day-number">2</div>
			    </div>
		      	<div class="week-day" data-date="2025-04-03">
		        	<div class="day-name">목</div>
		        	<div class="day-number">3</div>
		      	</div>
		      	<div class="week-day" data-date="2025-04-04">
		        	<div class="day-name">금</div>
		        	<div class="day-number">4</div>
		      	</div>
		      	<div class="week-day weekend" data-date="2025-04-05">
		        	<div class="day-name">토</div>
		        	<div class="day-number">5</div>
		      	</div>
		      	<div class="week-day weekend" data-date="2025-04-06">
		       	 	<div class="day-name">일</div>
		        	<div class="day-number">6</div>
		      	</div>
		    </div>
		    
		    <!-- 캘린더 모달 -->
		    <div class="overlay" id="overlay"></div>
		    <div class="calendar-modal" id="calendarModal">
		      	<div class="calendar-modal-header">
		        	<div class="month-nav">
		          		<button class="month-nav-btn" id="prevMonth">&lt;</button>
		          		<div class="month-name" id="modalMonthYear">2025년 4월</div>
		          		<button class="month-nav-btn" id="nextMonth">&gt;</button>
		        	</div>
		      	</div>
		      	<div class="calendar-body">
		        	<div class="weekdays">
			          	<div class="weekday">일</div>
			          	<div class="weekday">월</div>
			          	<div class="weekday">화</div>
			          	<div class="weekday">수</div>
			          	<div class="weekday">목</div>
			          	<div class="weekday">금</div>
			          	<div class="weekday">토</div>
		        	</div>
		        	<div class="days" id="calendarDays"></div>
		      	</div>
		    </div>
		
		    <!-- 나의학습 ms 섹션 -->
		    <h2 class="section-title">나의 학습</h2>
		    <div class="ms-container">
		    	<c:forEach items="${ learningList }" var="learningDTO" >
		      		<div class="ms-section">
			      		<!-- 이미지 부분 -->
			      		<c:if test="${ not empty learningDTO.files }">
			      			<img src="<c:url value='/Uploads/${ learningDTO.files[0].fileName }' />" class="ms-image"/>
			      		</c:if>
				          
				        <div class="ms-content">
				            <div class="ms-title">${ learningDTO.learningTitle }</div>
				            <div class="ms-description">${ learningDTO.learningContent }</div>
				        </div>
				         
				        <div class="ms-detail">
				          	<div class="ms-detail-part">
				           	 	<div class="ms-detail-label">분야</div>
				            	<div class="ms-tags">
				            		<c:if test="${ not empty learningDTO.topic }">
				            			<c:forEach items="${ fn:split(learningDTO.topic, ',') }" var="topic">
					            			<span class="ms-tag">${ topic }</span>
					            		</c:forEach>
				            		</c:if>
				            	</div>
				          	</div>
				          
				          	<div class="ms-detail-part">
				            	<div class="ms-detail-label">해시태그</div>
				            	<div class="ms-detail-value">
					              	<div class="ms-tags">
					              		<c:if test="${ not empty learningDTO.hashtag }">
					            			<c:forEach items="${ fn:split(learningDTO.hashtag, ',') }" var="tag">
						            			<span class="ms-tag">#${ tag }</span>
						            		</c:forEach>
					            		</c:if>
					              	</div>
					            </div>
					   		</div>
					   		
					   		<div class="ms-detial-part shared-person">
						        <div class="ms-detail-label">공유한 사람</div>
						        <div class="ms-detail-value">
						            <div class="ms-shared-tags">
						            	<c:if test="${ not empty learningDTO.sharedList }">
					            			<c:forEach items="${ learningDTO.sharedList }" var="shared">
						            			<span class="ms-shared-tag">${ shared.sharedToName } (${ shared.sharedTo })</span>
						            		</c:forEach>
					            		</c:if>
						            </div>
						        </div>
						    </div>
						</div>
					</div>
				</c:forEach>
			    <!-- 나의 학습 페이징 -->
			    <div class="paging">
					${ paging }
				</div>
			</div>
		    
		    <!-- 공유학습 섹션 -->
		    <h2 class="section-title">공유 학습</h2>
		    <div class="ss-container">
		    	<c:forEach items="${ sharedList }" var="sharedDTO">
			    	<div class="ss-section">
			        	<!-- 이미지 부분 -->
			      		<c:if test="${ not empty sharedDTO.files }">
			      			<img src="<c:url value='/Uploads/${ sharedDTO.files[0].fileName }' />" class="ms-image"/>
			      		</c:if>
			        	<div class="ss-label">
			            	<div class="ss-likes">
				              	<span class="ss-likes-icon">💚</span>
				              	<span class="ss-likes-count">${ sharedDTO.likeCnt }</span>
				            </div>
							<div class="ss-shared-user">${ sharedDTO.memberId }</div>
						</div>
					</div>
		    	</c:forEach>
			</div> 
		</div>
	</div>
	
	
	<script>
		// 숫자 앞에 0을 붙이는 함수
	    function padZero(num) {
	      return (num < 10) ? "0" + num : num;
	    }
	
		document.addEventListener('DOMContentLoaded', () => {
			const urlParams = new URLSearchParams(window.location.search);
		    const param = urlParams.get('date');
		    
		    const calendarToggle = document.getElementById('calendarToggle');
		    const calendarModal = document.getElementById('calendarModal');
		    const overlay = document.getElementById('overlay');
		    const modalMonthYear = document.getElementById('modalMonthYear');
		    const prevMonthBtn = document.getElementById('prevMonth');
		    const nextMonthBtn = document.getElementById('nextMonth');
		    const calendarDays = document.getElementById('calendarDays');
		    const weekDays = document.querySelectorAll('.week-day');
		    const selectedDateDisplay = document.querySelector('.selected-date');
		    
			const today = new Date();
		    let selectedDate = param ? new Date(param) : new Date();
		    let currentMonth = selectedDate.getMonth();
		    let currentYear = selectedDate.getFullYear();
		    
		    // 선택된 날짜 업데이트
		  	const updateSelectedDate = () => {
		  		const dayNames = ['일', '월', '화', '수', '목', '금', '토'];
		  	    const dayOfWeek = dayNames[selectedDate.getDay()];
		  	    selectedDateDisplay.textContent = selectedDate.getFullYear() + '년 ' + (selectedDate.getMonth() + 1) + '월 ' + selectedDate.getDate() + '일 (' + dayOfWeek + ')';
		  	};
		    
		 	// 주간 달력 뷰 업데이트
		    const updateWeekView = () => {
		    	// 현재 선택된 날짜의 요일 (0: 일요일, 1: 월요일, ...)
		    	const selectedDay = selectedDate.getDay();
		    	
		    	// 주의 시작일 (월요일)
		       	const startAdjustment = selectedDay === 0 ? -6 : 1 - selectedDay;
		       	const startOfweek = new Date(selectedDate);
		       	startOfweek.setDate(selectedDate.getDate() + startAdjustment);

		    	// 주의 모든 날짜 업데이트
		    	weekDays.forEach((weekDay, index) => {
		    		const date = new Date(startOfweek);
		    		date.setDate(date.getDate() + index);

		    		// date-date 속성 업데이트
		    		const dateStr = date.getFullYear() + '-' + padZero(date.getMonth() + 1) + '-' + padZero(date.getDate());
		    		weekDay.setAttribute('data-date', dateStr);
		    		
		    		// 날짜 표시 업데이트
		    		weekDay.querySelector('.day-number').textContent = date.getDate();

		    		// 선택된 날짜 스타일 업데이트
		    		weekDay.classList.toggle('selected', date.toDateString() === selectedDate.toDateString());
		    		// 오늘 날짜 스타일 업데이트
		    		weekDay.classList.toggle('today', date.toDateString() === new Date().toDateString());
		    		// 주말 날짜 스타일 업데이트
		    		weekDay.classList.toggle('weekend', date.getDay() === 0 || date.getDay() === 6);
				});
			};

			// 달력 렌더링
			const renderCalendar = (year, month) => {
				modalMonthYear.textContent = year + '년' + (month + 1) + '월';

				const firstDay = new Date(year, month, 1);
				const startDay = firstDay.getDay();
				const totalCells = 42;

				const daysFragment = document.createDocumentFragment();
				const todayStr = today.toDateString();
				const selectedStr = selectedDate.toDateString();

				calendarDays.innerHTML = '';

				for (let i = 0; i < totalCells; i++) {
					const offset = i - startDay;
					const date = new Date(year, month, 1 + offset);
					const dayEl = document.createElement('div');
					dayEl.className = 'day';
					if (date.getMonth() !== month) dayEl.classList.add('other-month');
					if (date.toDateString() === todayStr) dayEl.classList.add('today');
					if (date.toDateString() === selectedStr) dayEl.classList.add('selected');
	
					dayEl.dataset.date = date.getFullYear() + '-' + padZero(date.getMonth() + 1) + '-' + padZero(date.getDate());
					dayEl.textContent = date.getDate();
					
					daysFragment.appendChild(dayEl);
				}

				calendarDays.appendChild(daysFragment);
			};
			
			// 날짜 클릭 이벤트 위임 (event delegation)
			calendarDays.addEventListener('click', (e) => {
				if (!e.target.classList.contains('day')) return;
				const dateStr = e.target.dataset.date;
				if (!dateStr) return;
				selectedDate = new Date(dateStr);
				updateSelectedDate();
				updateWeekView();
				calendarModal.style.display = 'none';
				overlay.style.display = 'none';
			});

			// 달력 토글
			calendarToggle.addEventListener('click', () => {
				calendarModal.style.display = 'block';
				overlay.style.display = 'block';
				renderCalendar(currentYear, currentMonth);
			});

			// 오버레이 클릭 시 캘린더 종료
			overlay.addEventListener('click', () => {
				calendarModal.style.display = 'none';
				overlay.style.display = 'none';
			});

			// 이전 달로 이동
			prevMonthBtn.addEventListener('click', () => {
				currentMonth = (currentMonth - 1 + 12) % 12;
				if (currentMonth === 11) currentYear--;
				renderCalendar(currentYear, currentMonth);
			});

			// 다음 달로 이동
			nextMonthBtn.addEventListener('click', () => {
				currentMonth = (currentMonth + 1) % 12;
				if (currentMonth === 0) currentYear++;
				renderCalendar(currentYear, currentMonth);
			});

			// 주간 달력의 날짜 클릭 시
			weekDays.forEach(day => {
				day.addEventListener('click', () => {
				const dateStr = day.dataset.date;
				if (dateStr) window.location.href = './today.do?date=' + dateStr;
				});
			});
			
			// 초기 렌더링
			updateSelectedDate();
			updateWeekView();
		});
	</script>

</body>
</html>