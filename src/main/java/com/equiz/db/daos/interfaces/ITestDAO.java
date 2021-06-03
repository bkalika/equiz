package com.equiz.db.daos.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.equiz.db.beans.UserTestBean;
import com.equiz.db.dtos.Test;

public interface ITestDAO {
	List<Test> find();
	Test find(Long testId);
	void update(UserTestBean userTest);
	List<UserTestBean> findUserTestsBySubject(Long userId, Long subjectId);
	List<UserTestBean> findUserPassedTest(Long userId);
	List<Test> findBySubject(Long subjectId);
	UserTestBean findUserTestByTestId(Long userId, Long testId);
	void insert(Test test);
	void update(Long id, String name, LocalDateTime deadline, Long level, int duration);
	void delete(Long testId);
	void increaseTestScore(Test test, Long l);
	void decreaseTestScore(Test test, Long score);
	void increaseTestPopularity(Test test);
	List<Test> findUsersTestBySubject(Long userId, Long subjectId);
}
