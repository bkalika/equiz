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

public class DeleteAnswerCommand extends Command {
	private static final long serialVersionUID = -6931944849296757752L;
	private static final Logger LOG = Logger.getLogger(DeleteAnswerCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing DeleteAnswerCommand");
		Long questionId = Long.parseLong(String.valueOf(request.getParameter("question_id")));
		Long id = Long.parseLong(String.valueOf(request.getParameter("answer_id")));
		DAOFactory.getAnswerDAO().delete(id);
		List<Answer> newAnswers = DAOFactory.getAnswerDAO().findByQuestionId(questionId);
		request.setAttribute("answers", newAnswers);
		request.setAttribute("question_id", questionId);
		return Path.PAGE_ADMIN_UPDATE_QUESTION;
	}

}
