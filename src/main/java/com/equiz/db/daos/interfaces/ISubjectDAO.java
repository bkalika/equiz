package com.equiz.db.daos.interfaces;

import java.util.List;

import com.equiz.db.dtos.Subject;

/**
 * DAO class for managing {@link Subject} entities
 *
 * @author bkalika
 */
public interface ISubjectDAO {
	List<Subject> find();
	Subject find(Long id);
	List<Subject> findUserSubjectsById(Long userId);
	void insert(String subjectName);
	void update(Long subjectId, String subjectName);
	void delete(Long subjectId);
}
