package com.equiz.web.commands;

import java.io.IOException;
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
import com.equiz.db.dtos.User;
import com.equiz.exceptions.Error;
import com.equiz.utils.Util;

@WebServlet(name = "LoginCommand")
public class LoginCommand extends Command {
	private static final long serialVersionUID = -1054401004328577636L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing LoginCommand");
		HttpSession session = request.getSession();
		String login = "", password = "";
		String rememberMe = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMe);

		if ((request.getParameter("username") != null) && (request.getParameter("password") != null)) {
			login = new String(request.getParameter("username").getBytes("ISO-8859-1"), "UTF-8");
			password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "UTF-8");
		} else if ((session.getAttribute("username") != null) && (session.getAttribute("password") != null)) {
			login = String.valueOf(session.getAttribute("username"));
			password = String.valueOf(session.getAttribute("password"));
		}
		User user = null;
		LOG.info("User: " + login + " logged");

		if ((!login.isEmpty()) && (!password.isEmpty())) {
			user = DAOFactory.getUserDAO().findUserByLogin(login);
			
			if (user != null) {
				if (user.getPassword().equals(password)) {
					
					if (user.getRole().getName().equals("blocked")) {
						request.setAttribute("errorMessage", Error.ERR_BLOCKED_USER);
						return Path.PAGE_LOGIN;
					}

					request.setAttribute("username", login);
					request.setAttribute("role", user.getRole().getName());
					session.setAttribute("username", login);
					session.setAttribute("role", user.getRole().getName());
					session.setAttribute("userId", user.getId());
				} else {
					request.setAttribute("errorMessage", Error.ERR_INVALID_PASSWORD);
					return Path.PAGE_LOGIN;
				}
			} else {
				request.setAttribute("errorMessage", Error.ERR_CANNOT_FIND_USER_NAME);
				return Path.PAGE_LOGIN;
			}
			session.setAttribute("user", user);
		}
		
		if (remember) {
			Util.storeUserCookie(response, user);
		} else {
			Util.deleteUserCookie(response);
		}
		List<UserTestBean> userPassedTests = DAOFactory.getTestDAO().findUserPassedTest(user.getId());
		request.setAttribute("passedTests", userPassedTests);
		return Path.PAGE_PAGE_USER;
	}

}
