package com.equiz.web.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Subject;

public class SubjectCommand extends Command {
	private static final long serialVersionUID = -4811118678752679513L;
	public static final Logger LOG = Logger.getLogger(SubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Starting trace SubjectCommand");
		HttpSession session = request.getSession();
		String role = String.valueOf(session.getAttribute("role"));
		List<Subject> subjects = null;
		switch (role) {
			case "user":
//				subjects = DAOFactory.getSubjectDAO().findUserSubjectsById(userId);
				subjects = DAOFactory.getSubjectDAO().find();
				break;
			case "admin":
				subjects = DAOFactory.getSubjectDAO().find();
				break;
		}
		session.setAttribute("subjects", subjects);
		return Path.PAGE_SUBJECT;
	}

}
