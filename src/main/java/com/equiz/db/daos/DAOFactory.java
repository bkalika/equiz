package com.equiz.db.daos;

import com.equiz.db.daos.interfaces.IAnswerDAO;
import com.equiz.db.daos.interfaces.IQuestionDAO;
import com.equiz.db.daos.interfaces.IRoleDAO;
import com.equiz.db.daos.interfaces.ISubjectDAO;
import com.equiz.db.daos.interfaces.ITestDAO;
import com.equiz.db.daos.interfaces.IUserDAO;

public class DAOFactory {
	public static IUserDAO getUserDAO() {
		return new UserDAO();
	}

	public static IRoleDAO getRoleDAO() {
		return new RoleDAO();
	}

	public static ISubjectDAO getSubjectDAO() {
		return new SubjectDAO();
	}

	public static ITestDAO getTestDAO() {
		return new TestDAO();
	}

	public static IQuestionDAO getQuestionDAO() {
		return new QuestionDAO();
	}

	public static IAnswerDAO getAnswerDAO() {
		return new AnswerDAO();
	}
}
