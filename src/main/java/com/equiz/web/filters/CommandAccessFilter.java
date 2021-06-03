package com.equiz.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.exceptions.Error;

/**
 * Context Listener
 * @author bkalika
 */
public class CommandAccessFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	private Map<String, List<String>> accessMap = new HashMap<>();
	private List<String> commons = new ArrayList<>();
	private List<String> outOfControl = new ArrayList<>();

	public void destroy() {
		LOG.debug("Filter destruction starts.");
		// do nothing
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessage = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessage);
			LOG.trace("Set the request attrubute: errorMessage: " + errorMessage);

			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}

		if (session.getAttribute("user") == null) {
			request.setAttribute("errorMessage", Error.ERR_USER_NOT_LOGGER);
			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}

		if (outOfControl.contains(commandName)) {
			return true;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			return false;
		}

		String userRole;
		String role = String.valueOf(session.getAttribute("role"));
		switch (role) {
		case "admin":
			userRole = "admin";
			break;
		case "user":
			userRole = "user";
			break;
		case "blocked":
			userRole = "blocked";
			break;
		default:
			userRole = null;
			break;
		}

		if (userRole == null) {
			return false;
		}

		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.debug("Filter initialization starts");

		// roles
		accessMap.put("admin", asList(filterConfig.getInitParameter("admin")));
		accessMap.put("user", asList(filterConfig.getInitParameter("common")));
		LOG.trace("Access map: " + accessMap);

		// commons
		commons = asList(filterConfig.getInitParameter("common"));
		LOG.trace("Common commands: " + commons);

		// out of control
		outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands: " + outOfControl);

		LOG.trace("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 *
	 * @param str
	 * parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreElements()) {
			list.add(st.nextToken());
		}
		return list;
	}

}
