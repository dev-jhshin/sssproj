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
	  // 달력 팝업창
	  document.addEventListener('DOMContentLoaded', function() {
	    const urlParams = new URLSearchParams(window.location.search);
	    const param = urlParams.get('date');
	
	    // 현재 날짜 가져오기
	    let currentDate = new Date();
	    let selectedDate = new Date();
	    if (param != null && param.length != 0) {
	      const parts = param.split("-");
	      const year = parseInt(parts[0], 10);
	      const month = parseInt(parts[1], 10) - 1; // month는 0-based
	      const day = parseInt(parts[2], 10);
	      selectedDate = new Date(year, month, day);
	    }
	    let currentMonth = selectedDate.getMonth();
	    let currentYear = selectedDate.getFullYear();
	    
	    const calendarToggle = document.getElementById('calendarToggle');
	    const calendarModal = document.getElementById('calendarModal');
	    const overlay = document.getElementById('overlay');
	    const modalMonthYear = document.getElementById('modalMonthYear');
	    const prevMonth = document.getElementById('prevMonth');
	    const nextMonth = document.getElementById('nextMonth');
	    const calendarDays = document.getElementById('calendarDays');
	    const weekDays = document.querySelectorAll('.week-day');
	    const selectedDateDisplay = document.querySelector('.selected-date');
	    
	    // 숫자 앞에 0을 붙이는 함수
	    function padZero(num) {
	      return (num < 10) ? "0" + num : num;
	    }
	    
	    // 달력 토글
	    calendarToggle.addEventListener('click', function() {
	      calendarModal.style.display = 'block';
	      overlay.style.display = 'block';
	      renderCalendar(currentYear, currentMonth);
	    });
	    
	    // 오버레이 클릭 시 캘린더 닫히는 거
	    overlay.addEventListener('click', function() {
	      calendarModal.style.display = 'none';
	      overlay.style.display = 'none';
	    });
	    
	    // 이전 달로 이동
	    prevMonth.addEventListener('click', function() {
	      currentMonth--;
	      if (currentMonth < 0) {
	        currentMonth = 11;
	        currentYear--;
	      }
	      renderCalendar(currentYear, currentMonth);
	    });
	    
	    // 다음 달로 이동
	    nextMonth.addEventListener('click', function() {
	      currentMonth++;
	      if (currentMonth > 11) {
	        currentMonth = 0;
	        currentYear++;
	      }
	      renderCalendar(currentYear, currentMonth);
	    });
	    
	    // 주간 달력의 날짜 클릭시 선택
	    weekDays.forEach(day => {
	      day.addEventListener('click', function() {
	        const dateStr = this.getAttribute('data-date');
	        if (dateStr) {
	          const [year, month, day] = dateStr.split('-').map(Number);
	          // selectedDate = new Date(year, month - 1, day);
	          // updateSelectedDate();
	          // updateWeekView();
	          window.location.href = './today.do?date=' + year + '-' + month + '-' + day;
	        }
	      });
	    });
	    
	    // 달력 렌더링
	    function renderCalendar(year, month) {
	      modalMonthYear.textContent = year + "년 " + (month + 1) + "월";
	      
	      const firstDayOfMonth = new Date(year, month, 1);
	      const lastDayOfMonth = new Date(year, month + 1, 0);
	
	      const days = [];
	      
	      const firstDayWeekday = firstDayOfMonth.getDay();
	      const prevMonthLastDay = new Date(year, month, 0).getDate();
	      
	      for (let i = firstDayWeekday - 1; i >= 0; i--) {
	        const prevMonth = month === 0 ? 11 : month - 1;
	        const prevYear = month === 0 ? year - 1 : year;
	        days.push({
	          day: prevMonthLastDay - i,
	          month: prevMonth,
	          year: prevYear,
	          isCurrentMonth: false
	        });
	      }
	      
	      // 현재 달의 날짜 추가
	      for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
	        days.push({
	          day: i,
	          month: month,
	          year: year,
	          isCurrentMonth: true
	        });
	      }
	      
	      // 다음 달의 날짜로 나머지 채우기
	      const remainingDays = 42 - days.length; 
	      for (let i = 1; i <= remainingDays; i++) {
	        const nextMonth = month === 11 ? 0 : month + 1;
	        const nextYear = month === 11 ? year + 1 : year;
	        days.push({
	          day: i,
	          month: nextMonth,
	          year: nextYear,
	          isCurrentMonth: false
	        });
	      }
	      
	      // 달력에 날짜 넣기
	      calendarDays.innerHTML = '';
	      days.forEach(day => {
	        const dateElement = document.createElement('div');
	        dateElement.classList.add('day');
	        
	        if (!day.isCurrentMonth) {
	          dateElement.classList.add('other-month');
	        }
	        
	        // 오늘 날짜 표시
	        const today = new Date();
	        if (day.day === today.getDate() && 
	            day.month === today.getMonth() && 
	            day.year === today.getFullYear()) {
	          dateElement.classList.add('today');
	        }
	        
	        // 선택된 날짜 표시
	        if (day.day === selectedDate.getDate() && 
	            day.month === selectedDate.getMonth() && 
	            day.year === selectedDate.getFullYear()) {
	          dateElement.classList.add('selected');
	        }
	        
	        dateElement.textContent = day.day;
	        
	        // 날짜 클릭 이벤트
	        dateElement.addEventListener('click', function() {
	          selectedDate = new Date(day.year, day.month, day.day);
	          updateSelectedDate();
	          updateWeekView();
	          calendarModal.style.display = 'none';
	          overlay.style.display = 'none';
	        });
	        
	        calendarDays.appendChild(dateElement);
	      });
	    }
	    
	    // 선택된 날짜 업데이트
	    function updateSelectedDate() {
	      const dayNames = ['일', '월', '화', '수', '목', '금', '토'];
	      const dayOfWeek = dayNames[selectedDate.getDay()];
	      
	      selectedDateDisplay.textContent = selectedDate.getFullYear() + "년 " + (selectedDate.getMonth() + 1) + "월 " + selectedDate.getDate() + "일 (" + dayOfWeek + ")";
	      
	      // 주간 달력 업데이트
	      const currentMonthElement = document.querySelector('.current-month');
	      if (currentMonthElement) {
	        currentMonthElement.textContent = selectedDate.getFullYear() + "년 " + (selectedDate.getMonth() + 1) + "월";
	      }
	    }
	    
	    // 주간 달력 뷰 업데이트
	    function updateWeekView() {
	      // 현재 선택된 날짜의 요일 (0: 일요일, 1: 월요일, ...)
	      const selectedDay = selectedDate.getDay();
	      
	      // 주의 시작일 (월요일)
	      const startOfWeek = new Date(selectedDate);
	      const startAdjustment = selectedDay === 0 ? -6 : 1 - selectedDay;
	      startOfWeek.setDate(selectedDate.getDate() + startAdjustment);
	      
	      // 주의 모든 날짜 업데이트
	      weekDays.forEach((weekDay, index) => {
	        const date = new Date(startOfWeek);
	        date.setDate(startOfWeek.getDate() + index);
	        
	        // data-date 속성 업데이트 
	        const year = date.getFullYear();
	        const month = padZero(date.getMonth() + 1);
	        const day = padZero(date.getDate());
	        const dateStr = year + "-" + month + "-" + day;
	        
	        weekDay.setAttribute('data-date', dateStr);
	        
	        // 날짜 표시 업데이트
	        weekDay.querySelector('.day-number').textContent = date.getDate();
	        
	        // 선택된 날짜 스타일 업데이트
	        weekDay.classList.remove('selected');
	        if (date.toDateString() === selectedDate.toDateString()) {
	          weekDay.classList.add('selected');
	        }
	        
	        // 오늘 날짜 스타일 업데이트
	        //weekDay.classList.remove('today');
	        //if (date.toDateString() === new Date().toDateString()) {
	        //  weekDay.classList.add('today');
	        //}
	        
	        // 주말 스타일 업데이트
	        weekDay.classList.remove('weekend');
	        if (date.getDay() === 0 || date.getDay() === 6) {
	          weekDay.classList.add('weekend');
	        }
	      });
	    }
	    
	    // 초기 렌더링
	    updateSelectedDate();
	    updateWeekView();
	  });
	</script>

</body>
</html>