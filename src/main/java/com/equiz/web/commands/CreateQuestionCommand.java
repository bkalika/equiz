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
import com.equiz.exceptions.Error;

public class CreateQuestionCommand extends Command {
	private static final long serialVersionUID = -4277355177321604885L;
	private static final Logger LOG = Logger.getLogger(CreateQuestionCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing CreateQuestionCommand");
		Long testId = Long.parseLong(request.getParameter("testId"));
		String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
		Long score = Long.parseLong(request.getParameter("score"));
		Boolean isSingle = Boolean.parseBoolean(request.getParameter("is_single"));
		boolean existQuestion = false;
		List<Question> questions = DAOFactory.getQuestionDAO().findAll(testId);
		for (Question question : questions) {
			if (name.equals(question.getName())) {
				existQuestion = true;
			}
		}
		if (existQuestion) {
			request.setAttribute("errorMessage", Error.ERR_QUESTION_ALREADY_EXIST);
		}

		if (!existQuestion) {
			Test test = DAOFactory.getTestDAO().find(testId);
			DAOFactory.getQuestionDAO().insert(name, score, testId, isSingle);
			DAOFactory.getTestDAO().increaseTestScore(test, score);
		}

		List<Question> newQuestions = DAOFactory.getQuestionDAO().findAll(testId);
		request.setAttribute("testId", testId);
		request.setAttribute("questions", newQuestions);
		return Path.PAGE_ADMIN_UPDATE_TEST;
	}

}
