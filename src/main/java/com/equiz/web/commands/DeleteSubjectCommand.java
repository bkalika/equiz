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

public class DeleteSubjectCommand extends Command {
	private static final long serialVersionUID = 7857382486159405919L;
	private static final Logger LOG = Logger.getLogger(DeleteSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Start tracing DeleteSubjectCommand");
		Long id = Long.parseLong(String.valueOf(request.getParameter("subject_id")));
		DAOFactory.getSubjectDAO().delete(id);
		List<Subject> newSubjects = DAOFactory.getSubjectDAO().find();
		List<Test> newTests = DAOFactory.getTestDAO().find();
		List<User> users = DAOFactory.getUserDAO().find();
		List<Role> roles = DAOFactory.getRoleDAO().find();
		request.setAttribute("users", users);
		request.setAttribute("roles", roles);
		request.setAttribute("tests", newTests);
		request.setAttribute("subjects", newSubjects);
		return Path.PAGE_ADMIN;
	}

}
