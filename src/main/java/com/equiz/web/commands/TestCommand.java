package com.equiz.web.commands;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.beans.UserTestBean;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Test;

public class TestCommand extends Command {
	private static final long serialVersionUID = 2543835073488685718L;
	public static final Logger LOG = Logger.getLogger(TestCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Starting trace TestCommand");
		HttpSession session = request.getSession();
		Long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
		String sort = String.valueOf(request.getParameter("sort"));
//		String role = String.valueOf(session.getAttribute("role"));
		Long subjectId = Long.parseLong(String.valueOf(request.getParameter("subjectId")));
		List<UserTestBean> userTests = null;
		List<Test> allTests = null;
//		allTests = DAOFactory.getTestDAO().findUsersTestBySubject(userId, subjectId);
		
//		switch (role) {
//			case "user":
//				userTests = DAOFactory.getTestDAO().findUserTestsBySubject(userId, subjectId);
//				break;
//			case "admin":
//				allTests = DAOFactory.getTestDAO().findBySubject(subjectId);
//				break;
//		}
		switch (sort) {
			case "name":
				allTests = DAOFactory.getTestDAO().findUsersTestBySubject(userId, subjectId);
				break;
			case "level":
				allTests = DAOFactory.getTestDAO().findUsersTestBySubject(userId, subjectId).stream()
												.sorted(Comparator.comparingLong(Test::getLevel))
												.collect(Collectors.toList());
				break;
			case "popularity":
				allTests = DAOFactory.getTestDAO().findUsersTestBySubject(userId, subjectId).stream()
												.sorted(Comparator.comparingLong(Test::getPopularity))
												.collect(Collectors.toList());
				break;
			default:
				allTests = DAOFactory.getTestDAO().findUsersTestBySubject(userId, subjectId);
				break;
			}
		
		if (userTests != null) {
			session.setAttribute("tests", userTests);
		} else if (allTests != null) {
			session.setAttribute("tests", allTests);
		}
		session.setAttribute("subjectId", subjectId);
		return Path.PAGE_TEST;
	}

}
