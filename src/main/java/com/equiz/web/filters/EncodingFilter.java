package com.equiz.web.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

/**
 * Context Listener
 * @author bkalika
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
	private static final String DEFAULT_ENCODING = "UTF-8";
	private String encoding;

	public void destroy() {
		LOG.trace("====================================");
		LOG.info("Filter destruction starts");
		LOG.info("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		LOG.info("Filter starts");
		request.setCharacterEncoding(DEFAULT_ENCODING);
		response.setContentType("text/html; charset=UTF-8");

		response.setCharacterEncoding(encoding);

		LOG.info("Filter finished");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		LOG.info("Filter initialization starts");
		encoding = config.getInitParameter("encoding");
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
			LOG.info("Encoding from Filter Config: " + encoding);
		}
		LOG.info("Filter initialization finished");
	}

}
