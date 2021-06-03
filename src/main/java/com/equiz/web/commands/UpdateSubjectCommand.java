package com.equiz.web.commands;

import java.io.IOException;
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

public class UpdateSubjectCommand extends Command {
	private static final long serialVersionUID = 5555546932197806953L;
	private static final Logger LOG = Logger.getLogger(UpdateSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing UpdateSubjectCommand");
		Long id = Long.parseLong(String.valueOf(request.getParameter("subject_id")));
		String newName = new String(request.getParameter("subject_name").getBytes("ISO-8859-1"), "UTF-8");
		DAOFactory.getSubjectDAO().update(id, newName);
		List<Subject> newSubjects = DAOFactory.getSubjectDAO().find();
		List<Test> tests = DAOFactory.getTestDAO().find();
		List<User> users = DAOFactory.getUserDAO().find();
		List<Role> roles = DAOFactory.getRoleDAO().find();
		request.setAttribute("tests", tests);
		request.setAttribute("users", users);
		request.setAttribute("roles", roles);
		request.setAttribute("subjects", newSubjects);
		return Path.PAGE_ADMIN;
	}

}
