package com.equiz.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * Custom tag for support i18n.
 * @author bkalika
 */
public class LocaleTag extends TagSupport {
	private static final long serialVersionUID = -755410506602752411L;
	private static final Logger LOG = Logger.getLogger(LocaleTag.class);
	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		String language = String.valueOf(session.getAttribute("language"));
		if (language.isEmpty() || language.equals(null)) {
			language = "en";
		}

		if (value != null) {
			Locale locale = new Locale(language);
			ResourceBundle rb = ResourceBundle.getBundle("resources", locale);
			JspWriter out = pageContext.getOut();
			try {
				out.println(rb.getString(value));
			} catch (IOException e) {
				LOG.error("Not find this key. " + e);
			}
		}
		LOG.info("Language is switched to " + language.toUpperCase());
		return SKIP_BODY;
	}

}
