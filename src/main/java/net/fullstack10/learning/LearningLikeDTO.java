package net.fullstack10.learning;

import java.time.LocalDateTime;

public class LearningLikeDTO {
	private int idx;
	private String memberId;
	private int learningIdx;
	private LocalDateTime createdAt;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getLearningIdx() {
		return learningIdx;
	}
	public void setLearningIdx(int learningIdx) {
		this.learningIdx = learningIdx;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
