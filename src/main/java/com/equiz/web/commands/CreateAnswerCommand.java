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
import com.equiz.db.dtos.Question;
import com.equiz.exceptions.Error;

public class CreateAnswerCommand extends Command {
	private static final long serialVersionUID = 5609873472284024827L;
	private static final Logger LOG = Logger.getLogger(CreateAnswerCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing CreateAnswerCommand");
		Long questionId = Long.parseLong(request.getParameter("question_id"));
		String name = new String(request.getParameter("answer_name").getBytes("ISO-8859-1"), "UTF-8");
		Boolean isCorrect = Boolean.parseBoolean(request.getParameter("answer_correct"));
		boolean existAnswer = false;
		boolean existCorrect = false;
		Question question = DAOFactory.getQuestionDAO().find(questionId);
		List<Answer> answers = DAOFactory.getAnswerDAO().findByQuestionId(questionId);
		for (Answer answer : answers) {
			if (name.equals(answer.getName())) {
				existAnswer = true;
			}
			if (isCorrect == true && question.getIsSingle() == true) {
				if (answer.getIsCorrect() == true) {
					existCorrect = true;
				}
			}
		}
		if (existAnswer) {
			request.setAttribute("errorMessage", Error.ERR_ANSWER_ALREADY_EXIST);
		}
		
		if (existCorrect) {
			request.setAttribute("errorMessage", Error.ERR_CORRECT_ANSWER_ALREADY_EXIST);
		}

		if (!existAnswer && !existCorrect) {
			DAOFactory.getAnswerDAO().insert(name, questionId, isCorrect);
		}

		List<Answer> newAnswers = DAOFactory.getAnswerDAO().findByQuestionId(questionId);
		request.setAttribute("question_id", questionId);
		request.setAttribute("answers", newAnswers);
		return Path.PAGE_ADMIN_UPDATE_QUESTION;
	}

}
