package net.fullstack10.learning;

import java.time.LocalDateTime;

public class LearningCommentDTO {
	private int idx;
	private int learningIdx;
	private String memberId;
	private String commentContent;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getLearningIdx() {
		return learningIdx;
	}
	public void setLearningIdx(int learningIdx) {
		this.learningIdx = learningIdx;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
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
}
