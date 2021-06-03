package com.equiz.web.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;

public class GoToUserRegisterCommand extends Command {
	private static final long serialVersionUID = -6268091814893919486L;
	public static final Logger LOG = Logger.getLogger(GoToUserRegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Start tracing GoToUserRegisterCommand");
		return Path.PAGE_REGISTER;
	}
}
