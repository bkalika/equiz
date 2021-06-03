package com.equiz.web.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.utils.Util;

@WebServlet(name = "LogoutCommand")
public class LogoutCommand extends Command {
	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);
	private static final long serialVersionUID = -3891150791907929234L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Start tracing LogoutCommand");

		HttpSession session = request.getSession();
		request.setAttribute("username", null);
		request.setAttribute("role", null);
		session.setAttribute("username", null);
		session.setAttribute("role", null);
		Util.deleteUserCookie(response);
		if (session != null) {
			session.invalidate();
		}
		LOG.info("request:" + request);
		LOG.info("session: " + session);
		return Path.PAGE_LOGIN;
	}

}
