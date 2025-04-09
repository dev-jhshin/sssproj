package net.fullstack10.learning;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import net.fullstack10.auth.AuthDTO;
import net.fullstack10.file.FileDTO;

public class LearningDTO {
	private int idx;
	private String memberId;
	private String learningTitle;
	private String learningContent;
	private String topic;
	private String hashtag;
	private LocalDate learningStartedAt;
	private LocalDate learningEndedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int viewCnt;
	private int likeCnt;
	private boolean isPublic;
	private boolean isVisible;
	private boolean isLiked;
	private List<LearningSharedDTO> sharedList;
	private List<LearningSharedDTO> sharedToAdd;
	private List<LearningSharedDTO> sharedToRemove;
	private List<FileDTO> files;
	private List<FileDTO> filesToAdd;
	private List<FileDTO> filesToRemove;
	private List<LearningCommentDTO> comments;
	
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
	public String getLearningTitle() {
		return learningTitle;
	}
	public void setLearningTitle(String learningTitle) {
		this.learningTitle = learningTitle;
	}
	public String getLearningContent() {
		return learningContent;
	}
	public void setLearningContent(String learningContent) {
		this.learningContent = learningContent;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public LocalDate getLearningStartedAt() {
		return learningStartedAt;
	}
	public void setLearningStartedAt(LocalDate learningStartedAt) {
		this.learningStartedAt = learningStartedAt;
	}
	public LocalDate getLearningEndedAt() {
		return learningEndedAt;
	}
	public void setLearningEndedAt(LocalDate learningEndedAt) {
		this.learningEndedAt = learningEndedAt;
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
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public boolean getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	public List<LearningSharedDTO> getSharedList() {
		return sharedList;
	}
	public void setSharedList(List<LearningSharedDTO> sharedList) {
		this.sharedList = sharedList;
	}
	public List<LearningSharedDTO> getSharedToAdd() {
		return sharedToAdd;
	}
	public void setSharedToAdd(List<LearningSharedDTO> sharedToAdd) {
		this.sharedToAdd = sharedToAdd;
	}
	public List<LearningSharedDTO> getSharedToRemove() {
		return sharedToRemove;
	}
	public void setSharedToRemove(List<LearningSharedDTO> sharedToRemove) {
		this.sharedToRemove = sharedToRemove;
	}
	public List<FileDTO> getFiles() {
		return files;
	}
	public void setFiles(List<FileDTO> files) {
		this.files = files;
	}
	public List<FileDTO> getFilesToAdd() {
		return filesToAdd;
	}
	public void setFilesToAdd(List<FileDTO> filesToAdd) {
		this.filesToAdd = filesToAdd;
	}
	public List<FileDTO> getFilesToRemove() {
		return filesToRemove;
	}
	public void setFilesToRemove(List<FileDTO> filesToRemove) {
		this.filesToRemove = filesToRemove;
	}
	public List<LearningCommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<LearningCommentDTO> commnets) {
		this.comments = commnets;
	}
}
