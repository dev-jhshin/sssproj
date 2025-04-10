package net.fullstack10.learning;

import java.time.LocalDateTime;

public class LearningSharedDTO {
	private int idx;
	private int learningIdx;
	private String sharedTo;
	private String sharedToName;
	private String sharedFrom;
	private String sharedFromName;
	private LocalDateTime createdAt;

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
	public String getSharedTo() {
		return sharedTo;
	}
	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}
	public String getSharedToName() {
		return sharedToName;
	}
	public void setSharedToName(String sharedToName) {
		this.sharedToName = sharedToName;
	}
	public String getSharedFrom() {
		return sharedFrom;
	}
	public void setSharedFrom(String sharedFrom) {
		this.sharedFrom = sharedFrom;
	}
	public String getSharedFromName() {
		return sharedFromName;
	}
	public void setSharedFromName(String sharedFromName) {
		this.sharedFromName = sharedFromName;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
