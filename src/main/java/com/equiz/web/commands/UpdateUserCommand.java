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

public class UpdateUserCommand extends Command {
	private static final Logger LOG = Logger.getLogger(UpdateSubjectCommand.class);
	private static final long serialVersionUID = -1800850049265774635L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing UpdateUserCommand");
		Long userId = Long.parseLong(String.valueOf(request.getParameter("user_id")));
		String userName = new String(request.getParameter("user_name").getBytes("ISO-8859-1"), "UTF-8");
		String userPassword = new String(request.getParameter("user_password").getBytes("ISO-8859-1"), "UTF-8");
		Long roleId = Long.parseLong(request.getParameter("role_id"));

		List<Role> roles = DAOFactory.getRoleDAO().find();
		User user = DAOFactory.getUserDAO().find(userId);
		Role role = DAOFactory.getRoleDAO().find(roleId);
		user.setName(userName);
		user.setPassword(userPassword);
		user.setRole(role);
		DAOFactory.getUserDAO().update(user);
		List<User> newUsers = DAOFactory.getUserDAO().find();
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		List<Test> tests = DAOFactory.getTestDAO().find();
		request.setAttribute("tests", tests);
		request.setAttribute("subjects", subjects);
		request.setAttribute("users", newUsers);
		request.setAttribute("roles", roles);
		return Path.PAGE_ADMIN;
	}

}
