package net.fullstack10.support;

import java.time.LocalDateTime;

public class InquiryDTO {
	private int idx;
	private String inquiryTitle;
	private String inquiryContent;
	private String memberId;
	private int inquiryStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String iResolutionContent;
	private String managerId;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getInquiryStatus() {
		return inquiryStatus;
	}
	public void setInquiryStatus(int inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getiResolutionContent() {
		return iResolutionContent;
	}
	public void setiResolutionContent(String iResolutionContent) {
		this.iResolutionContent = iResolutionContent;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	
}
