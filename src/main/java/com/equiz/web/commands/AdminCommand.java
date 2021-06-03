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
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.Subject;
import com.equiz.db.dtos.Test;
import com.equiz.db.dtos.User;

public class AdminCommand extends Command {
	private static final long serialVersionUID = 4629463331529418740L;
	public static final Logger LOG = Logger.getLogger(AdminCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing AdminCommand");
		HttpSession session = request.getSession();
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		List<Test> tests = DAOFactory.getTestDAO().find();
		List<User> users = DAOFactory.getUserDAO().find();
		List<Role> roles = DAOFactory.getRoleDAO().find();
		request.setAttribute("subjects", subjects);
		request.setAttribute("tests", tests);
		request.setAttribute("users", users);
		request.setAttribute("roles", roles);
		if (session.getAttribute("username") != null) {
			return Path.PAGE_ADMIN;
		}
		return Path.PAGE_LOGIN;
	}
}
