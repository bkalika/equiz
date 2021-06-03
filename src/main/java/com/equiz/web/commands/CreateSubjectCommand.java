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
import com.equiz.exceptions.Error;

public class CreateSubjectCommand extends Command {
	private static final long serialVersionUID = 9056813339221044889L;
	private static final Logger LOG = Logger.getLogger(CreateSubjectCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing CreateSubjectCommand");
		String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
		boolean existSubject = false;
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		for (Subject subject : subjects) {
			if (name.equals(subject.getName())) {
				existSubject = true;
			}
		}
		if (existSubject) {
			request.setAttribute("errorMessage", Error.ERR_SUBJECT_ALREADY_EXIST);
		}
		DAOFactory.getSubjectDAO().insert(name);
		List<Subject> newSubjects = DAOFactory.getSubjectDAO().find();
		List<User> users = DAOFactory.getUserDAO().find();
		List<Role> roles = DAOFactory.getRoleDAO().find();
		List<Test> tests = DAOFactory.getTestDAO().find();
		request.setAttribute("tests", tests);
		request.setAttribute("users", users);
		request.setAttribute("roles", roles);
		request.setAttribute("subjects", newSubjects);

		return Path.PAGE_ADMIN;
	}

}
