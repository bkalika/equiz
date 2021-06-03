package com.equiz.web.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Question;
import com.equiz.db.dtos.User;

/* This Command is unused
 * @author bkalika
 */
public class AddUsersToTheTestCommand extends Command {
	private static final long serialVersionUID = 3944930972150845792L;
	public static final Logger LOG = Logger.getLogger(AddUsersToTheTestCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Start tracing AddUsersToTheTestCommand");
		Long testId = Long.parseLong(request.getParameter("test_id"));
		Enumeration<String> usersId = request.getParameterNames();
		List<User> usersList = new ArrayList<>();
		while (usersId.hasMoreElements()) {
			String paramName = usersId.nextElement();
			if (paramName.equals("user_id")) {
				String[] paramValues = request.getParameterValues(paramName);
				for (int i = 0; i < paramValues.length; i++) {
					User user = DAOFactory.getUserDAO().find(Long.parseLong(paramValues[i]));
					usersList.add(user);
				}
			}
		}

		DAOFactory.getUserDAO().addUsersToTheTest(testId, usersList);

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
