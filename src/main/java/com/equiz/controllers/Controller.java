package com.equiz.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.web.commands.Command;
import com.equiz.web.commands.CommandContainer;

/**
 * Main servlet controller.
 *
 * @author bkalika
 */
@WebServlet(name = "Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 5547338443567761572L;
	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.trace("***********************POST***************");
		process(request, response, "post");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.trace("***********************GET***************");
		process(request, response, "get");
	}

	private void process(HttpServletRequest request, HttpServletResponse response, String method) throws IOException, ServletException {
		LOG.debug("Controller starts");
		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command: " + commandName);
		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command: " + command);
		String forward = Path.PAGE_ERROR_PAGE;
		try {
			forward = command.execute(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		LOG.trace("Forward address: " + forward);
		LOG.debug("Controller finished, now go to the forward address: " + forward);
		request.getRequestDispatcher(forward).forward(request, response);
	}

}
