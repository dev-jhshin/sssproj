package net.fullstack10.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuthDTO {
	private int idx;
	private String memberId;
	private String memberPwd;
	private String memberName;
	private LocalDate memberBirthDate;
	private String memberEmail;
	private int memberStatus;
	private String memberGender;
	private LocalDateTime memberCreatedAt;
	private LocalDateTime memberLoginAt;
	private int questionId;
	private String answer;

	
	public AuthDTO() {}
	
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
	public String getMemberName() {
		return memberName;
	}
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public LocalDate getMemberBirthDate() {
		return memberBirthDate;
	}
	public void setMemberBirthDate(LocalDate memberBirthDate) {
		this.memberBirthDate = memberBirthDate;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public int getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(int memberStatus) {
		this.memberStatus = memberStatus;
	}
	public LocalDateTime getMemberCreatedAt() {
		return memberCreatedAt;
	}
	public void setMemberCreatedAt(LocalDateTime memberCreatedAt) {
		this.memberCreatedAt = memberCreatedAt;
	}
	public LocalDateTime getMemberLoginAt() {
		return memberLoginAt;
	}

	public void setMemberLoginAt(LocalDateTime memberLoginAt) {
		this.memberLoginAt = memberLoginAt;
	}

	public String getMemberGender() {
		return memberGender;
	}
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
