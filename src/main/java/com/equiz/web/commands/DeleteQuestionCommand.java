package com.equiz.web.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Question;
import com.equiz.db.dtos.Test;

public class DeleteQuestionCommand extends Command {
	private static final long serialVersionUID = -7992833494247861758L;
	private static final Logger LOG = Logger.getLogger(DeleteQuestionCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing DeleteQuestionCommand");
		Long testId = Long.parseLong(String.valueOf(request.getParameter("test_id")));
		Long questionId = Long.parseLong(String.valueOf(request.getParameter("question_id")));
		Test test = DAOFactory.getTestDAO().find(testId);
		Question question = DAOFactory.getQuestionDAO().find(questionId);
		DAOFactory.getTestDAO().decreaseTestScore(test, (long) question.getScore());
		DAOFactory.getQuestionDAO().delete(questionId);
		List<Question> newQuestions = DAOFactory.getQuestionDAO().findAll(testId);
		request.setAttribute("questions", newQuestions);
		request.setAttribute("testId", testId);
		return Path.PAGE_ADMIN_UPDATE_TEST;
	}

}
