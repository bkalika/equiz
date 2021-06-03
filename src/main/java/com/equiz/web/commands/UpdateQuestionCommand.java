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

public class UpdateQuestionCommand extends Command {
	private static final long serialVersionUID = -4286195504672481297L;
	private static final Logger LOG = Logger.getLogger(UpdateQuestionCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing UpdateQuestionCommand");
		Long testId = Long.parseLong(String.valueOf(request.getParameter("test_id")));
		Long id = Long.parseLong(String.valueOf(request.getParameter("question_id")));
		Long newScore = Long.parseLong(request.getParameter("question_score"));
		String newName = new String(request.getParameter("question_name").getBytes("ISO-8859-1"), "UTF-8");
		Boolean newIsSingle = Boolean.parseBoolean(request.getParameter("question_single"));
		Test test = DAOFactory.getTestDAO().find(testId);
		Question question = DAOFactory.getQuestionDAO().find(id);
		if (question.getScore() > newScore) {
			DAOFactory.getTestDAO().decreaseTestScore(test, newScore);
		} else {
			DAOFactory.getTestDAO().increaseTestScore(test, newScore-test.getScore());
		}
		DAOFactory.getQuestionDAO().update(id, newName, newScore, newIsSingle);
		List<Question> questions = DAOFactory.getQuestionDAO().findAll(testId);
		request.setAttribute("testId", testId);
		request.setAttribute("questions", questions);
		return Path.PAGE_ADMIN_UPDATE_TEST;
	}

}
