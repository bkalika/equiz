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
import com.equiz.db.dtos.User;

public class GoToUpdateTestCommand extends Command {
	private static final long serialVersionUID = -3417827428244659998L;
	public static final Logger LOG = Logger.getLogger(GoToUpdateTestCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing GoToUpdateTestCommand");
		Long testId = Long.parseLong(String.valueOf(request.getParameter("testId")));
		List<Question> questions = DAOFactory.getQuestionDAO().findAll(testId);
		List<User> usersWithoutThisTest = DAOFactory.getUserDAO().findUsersForAddingTheTest(testId);
		List<User> usersWithThisTest = DAOFactory.getUserDAO().findUsersWithThisTest(testId);
		request.setAttribute("testId", testId);
		request.setAttribute("questions", questions);
		request.setAttribute("usersWithoutThisTest", usersWithoutThisTest);
		request.setAttribute("usersWithThisTest", usersWithThisTest);

		return Path.PAGE_ADMIN_UPDATE_TEST;
	}
}
