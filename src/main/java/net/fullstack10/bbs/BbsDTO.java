package net.fullstack10.bbs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BbsDTO {
	private int idx;
	private String memberId;
	private String bbsCategory;
	private String bbsTitle;
	private String bbsContent;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int viewCnt;
	private int likeCnt;
	private List<Map> files;
	private List<Map> comments;
	private boolean like;

	public BbsDTO() {}

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
	public String getBbsCategory() {
		return bbsCategory;
	}
	public void setBbsCategory(String bbsCategory) {
		this.bbsCategory = bbsCategory;
	}
	public String getBbsTitle() {
		return bbsTitle;
	}
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	public String getBbsContent() {
		return bbsContent;
	}
	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
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

	public List<Map> getComments() {
		return comments;
	}

	public void setComment(List<Map> comments) {
		this.comments = comments;
	}

	public List<Map> getFiles() {
		return files;
	}

	public void setFiles(List<Map> files) {
		this.files = files;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}


}
