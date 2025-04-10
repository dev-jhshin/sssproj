package net.fullstack10.manager;

import java.time.LocalDateTime;

/**
 *
 */
public class ManagerDTO {

		private String managerId; //매니저아이디
		private String managerPwd; //매니저비밀번호
		private String managerName; //매니저 이름
		private String managerEmail; //매니저 이메일
		private int managerStatus; //매니저 직급
		private int inquiryIdx; //질문 인덱스번호
		private String inquiryTitle; //질문 제목
		private String inquiryContent; //질문 내용
		private LocalDateTime inquiryCreatedAt; //질문 생성날짜
		private LocalDateTime inquiryUpdatedAt; // 질문 수정 날짜
		private boolean inquiryStatus; //질문 상태
		private int iInquiryIdx; //질문 답변인덱스
		private String iManagerid; //질문 답변 매니저
		private String iResolutionContent; //질문 응답 내용
		private int reportIdx; //신고 인덱스번호
		private int targetId; // 신고 당한 인덱스 번호
		private String targetType; // 신고 당한 게시물 유형
		private String description; // 신고 사유
		private LocalDateTime reportCreatedAt; //신고 한 날짜
		private String SmemberId;	//신고, 질의 한 memberId
		private String RmemberId;	//신고, 질의를 받은 memberId
		private Boolean reportStatus; // 신고 상태
		private String rResolutionContent; // 매니저 신고 응답 내용
		private int rReportIdx;	//신고 답변 인덱스번호
		private String rManagerId; //신고 답변한 매니저 아이디
		private LocalDateTime resolutionCreatedAt; //신고, 문의 답변 한 날짜
		private String reportTitle; //신고받은 게시물 제목
		private String reportContent; //신고받은 게시물 내용
		private LocalDateTime reportUpdatedAt; //신고 받은 게시물 수정날짜

		public ManagerDTO() {}

		public String getManagerId() {
			return managerId;
		}
		public void setManagerId(String managerId) {
			this.managerId = managerId;
		}
		public String getManagerPwd() {
			return managerPwd;
		}
		public void setManagerPwd(String managerPwd) {
			this.managerPwd = managerPwd;
		}
		public String getManagerName() {
			return managerName;
		}
		public void setManagerName(String managerName) {
			this.managerName = managerName;
		}
		public int getManagerStatus() {
			return managerStatus;
		}
		public void setManagerStatus(int managerStatus) {
			this.managerStatus = managerStatus;
		}

		public String getManagerEmail() {
			return managerEmail;
		}

		public void setManagerEmail(String managerEmail) {
			this.managerEmail = managerEmail;
		}

		public int getrReportIdx() {
			return rReportIdx;
		}

		public void setrReportIdx(int rReportIdx) {
			this.rReportIdx = rReportIdx;
		}

		public String getrManagerId() {
			return rManagerId;
		}

		public void setrManagerId(String rManagerId) {
			this.rManagerId = rManagerId;
		}

		public LocalDateTime getResolutionCreatedAt() {
			return resolutionCreatedAt;
		}

		public void setResolutionCreatedAt(LocalDateTime resolutionCreatedAt) {
			this.resolutionCreatedAt = resolutionCreatedAt;
		}

		public int getInquiryIdx() {
			return inquiryIdx;
		}

		public void setInquiryIdx(int inquiryIdx) {
			this.inquiryIdx = inquiryIdx;
		}

		public String getInquiryTitle() {
			return inquiryTitle;
		}

		public void setInquiryTitle(String inquiryTitle) {
			this.inquiryTitle = inquiryTitle;
		}

		public String getInquiryContent() {
			return inquiryContent;
		}

		public void setInquiryContent(String inquiryContent) {
			this.inquiryContent = inquiryContent;
		}

		public LocalDateTime getInquiryCreatedAt() {
			return inquiryCreatedAt;
		}

		public void setInquiryCreatedAt(LocalDateTime inquiryCreatedAt) {
			this.inquiryCreatedAt = inquiryCreatedAt;
		}

		public LocalDateTime getInquiryUpdatedAt() {
			return inquiryUpdatedAt;
		}

		public void setInquiryUpdatedAt(LocalDateTime inquiryUpdatedAt) {
			this.inquiryUpdatedAt = inquiryUpdatedAt;
		}

		public int getReportIdx() {
			return reportIdx;
		}

		public void setReportIdx(int reportIdx) {
			this.reportIdx = reportIdx;
		}

		public int getTargetId() {
			return targetId;
		}

		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDateTime getReportCreatedAt() {
			return reportCreatedAt;
		}

		public void setReportCreatedAt(LocalDateTime reportCreatedAt) {
			this.reportCreatedAt = reportCreatedAt;
		}

		public String getSmemberId() {
			return SmemberId;
		}

		public void setSmemberId(String smemberId) {
			SmemberId = smemberId;
		}

		public String getRmemberId() {
			return RmemberId;
		}

		public void setRmemberId(String rmemberId) {
			RmemberId = rmemberId;
		}

		public String getTargetType() {
			return targetType;
		}

		public void setTargetType(String targetType) {
			this.targetType = targetType;
		}

		public Boolean getReportStatus() {
			return reportStatus;
		}

		public void setReportStatus(Boolean reportStatus) {
			this.reportStatus = reportStatus;
		}

		public String getrResolutionContent() {
			return rResolutionContent;
		}

		public void setrResolutionContent(String rResolutionContent) {
			this.rResolutionContent = rResolutionContent;
		}

		public String getReportTitle() {
			return reportTitle;
		}

		public void setReportTitle(String reportTitle) {
			this.reportTitle = reportTitle;
		}

		public String getReportContent() {
			return reportContent;
		}

		public void setReportContent(String reportContent) {
			this.reportContent = reportContent;
		}

		public LocalDateTime getReportUpdatedAt() {
			return reportUpdatedAt;
		}

		public void setReportUpdatedAt(LocalDateTime reportUpdatedAt) {
			this.reportUpdatedAt = reportUpdatedAt;
		}

		public boolean isInquiryStatus() {
			return inquiryStatus;
		}

		public void setInquiryStatus(boolean inquiryStatus) {
			this.inquiryStatus = inquiryStatus;
		}

		public int getiInquiryIdx() {
			return iInquiryIdx;
		}

		public void setiInquiryIdx(int iInquiryIdx) {
			this.iInquiryIdx = iInquiryIdx;
		}

		public String getiManagerid() {
			return iManagerid;
		}

		public void setiManagerid(String iManagerid) {
			this.iManagerid = iManagerid;
		}

		public String getIResolutionContent() {
			return iResolutionContent;
		}

		public void setIResolutionContent(String iResolutionContent) {
			this.iResolutionContent = iResolutionContent;
		}

}
