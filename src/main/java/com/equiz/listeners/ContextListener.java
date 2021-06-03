package com.equiz.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context Listener
 * @author bkalika
 */
@WebListener()
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(ContextListener.class);
	public void contextInitialized(ServletContextEvent event) {
		LOG.trace("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();

		LOG.trace("Servlet context initialization finished");
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		LOG.trace("Servlet context destruction starts");
		LOG.trace("Servlet context destruction finished");
	}

	private void initCommandContainer() {
		try {
			Class.forName("com.equiz.web.commands.CommandContainer");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

	private void initLog4J(ServletContext servletContext) {
		LOG.trace("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception e) {
			LOG.trace("Cannot configure Log4j");
			e.printStackTrace();
		}
		LOG.trace("Log4j initialization finished");
	}

}
