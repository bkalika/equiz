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

public class UpdateTestCommand extends Command {
	private static final long serialVersionUID = -3191197147671757090L;
	private static final Logger LOG = Logger.getLogger(UpdateTestCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing UpdateTestCommand");
		Long id = Long.parseLong(String.valueOf(request.getParameter("test_id")));
		String newName = new String(request.getParameter("test_name").getBytes("ISO-8859-1"), "UTF-8");
//		Long newScore = Long.parseLong(request.getParameter("test_score"));
		String newDeadline = new String(request.getParameter("deadline").getBytes("ISO-8859-1"), "UTF-8");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime newDeadlineParsed = LocalDateTime.parse(newDeadline, formatter);
		Long newLevel = Long.parseLong(request.getParameter("test_level"));
		Integer newDuration = Integer.parseInt(request.getParameter("test_duration"));
		DAOFactory.getTestDAO().update(id, newName, newDeadlineParsed, newLevel, newDuration);
		List<Test> newTests = DAOFactory.getTestDAO().find();
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		List<User> users = DAOFactory.getUserDAO().find();
		List<Role> roles = DAOFactory.getRoleDAO().find();
		request.setAttribute("subjects", subjects);
		request.setAttribute("tests", newTests);
		request.setAttribute("users", users);
		request.setAttribute("roles", roles);
		return Path.PAGE_ADMIN;
	}
}
