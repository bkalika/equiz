package com.equiz.web.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.Subject;
import com.equiz.db.dtos.Test;
import com.equiz.db.dtos.User;
import com.equiz.exceptions.Error;

public class CreateTestCommand extends Command {
	private static final long serialVersionUID = -7782482648448335377L;
	private static final Logger LOG = Logger.getLogger(CreateTestCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing CreateTestCommand");
		Long subjectId = Long.parseLong(request.getParameter("subject_id"));
		String name = new String(request.getParameter("test_name").getBytes("ISO-8859-1"), "UTF-8");
		String deadline = new String(request.getParameter("deadline"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime newDeadlineParsed = LocalDateTime.parse(deadline, formatter);
//		Long score = Long.parseLong(request.getParameter("test_score"));
		Long level = Long.parseLong(request.getParameter("level"));
		int duration = Integer.parseInt(request.getParameter("duration"));

		boolean existTest = false;
		List<Test> tests = DAOFactory.getTestDAO().find();
		for (Test test : tests) {
			if (name.equals(test.getName())) {
				existTest = true;
			}
		}
		if (existTest) {
			request.setAttribute("errorMessage", Error.ERR_TEST_ALREADY_EXIST);
		}
		
		Test newTest = new Test(name, subjectId, newDeadlineParsed, 0L, level, duration);
		DAOFactory.getTestDAO().insert(newTest);
		List<User> users = DAOFactory.getUserDAO().find();
		List<Role> roles = DAOFactory.getRoleDAO().find();
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		List<Test> newTests = DAOFactory.getTestDAO().find();
		request.setAttribute("tests", newTests);
		request.setAttribute("users", users);
		request.setAttribute("roles", roles);
		request.setAttribute("subjects", subjects);

		return Path.PAGE_ADMIN;
	}

}
