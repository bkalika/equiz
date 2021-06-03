package com.equiz.web.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Answer;

public class GoToUpdateAnswerCommand extends Command {
	private static final long serialVersionUID = -2431769369563806156L;
	public static final Logger LOG = Logger.getLogger(GoToUpdateAnswerCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing GoToUpdateAnswerCommand");
		Long questionId = Long.parseLong(String.valueOf(request.getParameter("question_id")));
		List<Answer> answers= DAOFactory.getAnswerDAO().findByQuestionId(questionId);
		request.setAttribute("question_id", questionId);
		request.setAttribute("answers", answers);
		return Path.PAGE_ADMIN_UPDATE_QUESTION;
	}
}
