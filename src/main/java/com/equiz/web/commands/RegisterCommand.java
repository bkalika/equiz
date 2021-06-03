package com.equiz.web.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.beans.UserTestBean;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.User;
import com.equiz.exceptions.Error;
import com.equiz.utils.Util;

@WebServlet(name = "RegisterCommand")
public class RegisterCommand extends Command {
	private static final long serialVersionUID = 394724928191180950L;
	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Start tracing RegisterCommand");
		String forward = "";
		HttpSession session = request.getSession();

		String login = "login";
		String password = "password";
		String passwordConfirm = "passwordConfirm";
		String rememberMe = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMe);

		if ((request.getParameter(login) != null) && (request.getParameter(password) != null)
				&& (request.getParameter(passwordConfirm) != null)) {
			login = new String(request.getParameter(login).getBytes("ISO-8859-1"), "UTF-8");
			password = new String(request.getParameter(password).getBytes("ISO-8859-1"), "UTF-8");
			passwordConfirm = new String(request.getParameter(passwordConfirm).getBytes("ISO-8859-1"), "UTF-8");
		} else if ((session.getAttribute(login) != null) && (session.getAttribute(password) != null)) {
			login = String.valueOf(session.getAttribute(login));
			password = String.valueOf(session.getAttribute(password));
		}

		User user = null;

		LOG.info("User: " + login + " registered");

		if ((!login.isEmpty()) && (!password.isEmpty())) {
			user = DAOFactory.getUserDAO().findUserByLogin(login);
			if (user != null) {
				request.setAttribute("errorMessage", Error.ERR_LOGIN_ALREADY_EXIST);
				return Path.PAGE_REGISTER;
			} else {

				if (!password.equals(passwordConfirm)) {
					request.setAttribute("errorMessage", Error.ERR_PASSWORD_DONT_MATCH);
					return Path.PAGE_REGISTER;
				}

				try {
					Role role = DAOFactory.getRoleDAO().find(2L);
					user = new User(login, password, role);
					DAOFactory.getUserDAO().add(user);
					user = DAOFactory.getUserDAO().findUserByLogin(login);
					request.setAttribute("username", login);
					request.setAttribute("password", password);
					request.setAttribute("role", user.getRole().getName());
					session.setAttribute("username", login);
					session.setAttribute("password", password);
					session.setAttribute("role", user.getRole().getName());
					session.setAttribute("userId", user.getId());
				} catch (RuntimeException e) {
					request.setAttribute("errorMessage", Error.ERR_UNPREDICTABLE_ERROR);
					return Path.PAGE_REGISTER;
				}
			}
			session.setAttribute("user", user);
		}
		if (remember) {
			Util.storeUserCookie(response, user);
		} else {
			Util.deleteUserCookie(response);
		}
		List<User> usersList = new ArrayList<>(Arrays.asList(user));
		List<UserTestBean> userPassedTests = DAOFactory.getTestDAO().findUserPassedTest(user.getId());
		request.setAttribute("passedTests", userPassedTests);
		forward = Path.PAGE_PAGE_USER;
		return forward;
	}

}
