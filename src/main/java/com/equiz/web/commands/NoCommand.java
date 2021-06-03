package com.equiz.web.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;

public class NoCommand extends Command {
	private static final long serialVersionUID = 2107455449031865657L;
	private static final Logger LOG = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage: " + errorMessage);
		LOG.debug("Command finished");
		return Path.PAGE_ERROR_PAGE;
	}

}
