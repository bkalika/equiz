package com.equiz.db.daos.interfaces;

import java.util.List;

import com.equiz.db.dtos.Answer;

/**
 * DAO class for managing {@link Answer} entities
 *
 * @author bkalika
 */
public interface IAnswerDAO {
	void insert(String name, Long questionId, Boolean isCorrect);
	Answer find(Long id);
	List<Answer> findByQuestionId(Long questionId);
	void update(Long id, String name, Boolean isCorrect);
	void delete(Long id);
}
