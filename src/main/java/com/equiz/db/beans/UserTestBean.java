package com.equiz.db.beans;

import java.time.LocalDateTime;

import com.equiz.db.dtos.DTO;

public class UserTestBean extends DTO {
	private static final long serialVersionUID = 3697106792533683072L;

	private Long testId;
	private Long subjectId;
	private Long userId;
	private String testName;
	private String subjectName;
	private String userName;
	private Long userScore;
	private Long testScore;
	private LocalDateTime deadline;
	private LocalDateTime userPassedDate;
	private Long testLevel;
	private Long testPopularity;
	private int testDuration;

	public int getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(int testDuration) {
		this.testDuration = testDuration;
	}

	public Long getTestLevel() {
		return testLevel;
	}

	public void setTestLevel(Long testLevel) {
		this.testLevel = testLevel;
	}

	public Long getTestPopularity() {
		return testPopularity;
	}

	public void setTestPopularity(Long testPopularity) {
		this.testPopularity = testPopularity;
	}

	public Long getUserScore() {
		return userScore;
	}

	public void setUserScore(Long userScore) {
		this.userScore = userScore;
	}

	public Long getTestScore() {
		return testScore;
	}

	public void setTestScore(Long testScore) {
		this.testScore = testScore;
	}

	public LocalDateTime getUserPassedDate() {
		return userPassedDate;
	}

	public void setUserPassedDate(LocalDateTime userPassedDate) {
		this.userPassedDate = userPassedDate;
	}

	public Long getScore() {
		return userScore;
	}

	public void setScore(Long score) {
		this.userScore = score;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getPassedDate() {
		return userPassedDate;
	}

	public void setPassedDate(LocalDateTime passedDate) {
		this.userPassedDate = passedDate;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + ((subjectId == null) ? 0 : subjectId.hashCode());
		result = prime * result + ((subjectName == null) ? 0 : subjectName.hashCode());
		result = prime * result + testDuration;
		result = prime * result + ((testId == null) ? 0 : testId.hashCode());
		result = prime * result + ((testLevel == null) ? 0 : testLevel.hashCode());
		result = prime * result + ((testName == null) ? 0 : testName.hashCode());
		result = prime * result + ((testPopularity == null) ? 0 : testPopularity.hashCode());
		result = prime * result + ((testScore == null) ? 0 : testScore.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPassedDate == null) ? 0 : userPassedDate.hashCode());
		result = prime * result + ((userScore == null) ? 0 : userScore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTestBean other = (UserTestBean) obj;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		if (subjectName == null) {
			if (other.subjectName != null)
				return false;
		} else if (!subjectName.equals(other.subjectName))
			return false;
		if (testDuration != other.testDuration)
			return false;
		if (testId == null) {
			if (other.testId != null)
				return false;
		} else if (!testId.equals(other.testId))
			return false;
		if (testLevel == null) {
			if (other.testLevel != null)
				return false;
		} else if (!testLevel.equals(other.testLevel))
			return false;
		if (testName == null) {
			if (other.testName != null)
				return false;
		} else if (!testName.equals(other.testName))
			return false;
		if (testPopularity == null) {
			if (other.testPopularity != null)
				return false;
		} else if (!testPopularity.equals(other.testPopularity))
			return false;
		if (testScore == null) {
			if (other.testScore != null)
				return false;
		} else if (!testScore.equals(other.testScore))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPassedDate == null) {
			if (other.userPassedDate != null)
				return false;
		} else if (!userPassedDate.equals(other.userPassedDate))
			return false;
		if (userScore == null) {
			if (other.userScore != null)
				return false;
		} else if (!userScore.equals(other.userScore))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserTestBean [testId=" + testId + ", subjectId=" + subjectId + ", userId=" + userId + ", testName="
				+ testName + ", subjectName=" + subjectName + ", userName=" + userName + ", userScore=" + userScore
				+ ", testScore=" + testScore + ", deadline=" + deadline + ", userPassedDate=" + userPassedDate
				+ ", testLevel=" + testLevel + ", testPopularity=" + testPopularity + ", testDuration=" + testDuration
				+ "]";
	}

}
