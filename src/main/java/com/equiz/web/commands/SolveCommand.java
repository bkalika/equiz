package com.equiz.web.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.beans.UserTestBean;
import com.equiz.db.daos.DAOFactory;
import com.equiz.utils.Util;

@WebServlet(name = "SolveCommand")
public class SolveCommand extends Command {
	public static final Logger LOG = Logger.getLogger(SolveCommand.class);
	private static final long serialVersionUID = -7619212830770284942L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int curQuestionNumber = 0;
		if (session.getAttribute("cur_question_number") != null) {
			curQuestionNumber = (Integer) session.getAttribute("cur_question_number");
		}

		List<?> questions = (List<?>) session.getAttribute("questions");
		List<List<String>> answers = (List<List<String>>) session.getAttribute("answers");
		Enumeration<String> parameterNames = request.getParameterNames();
		List<String> curAnswers = new ArrayList<>();
		while (parameterNames.hasMoreElements()) {
			String x = parameterNames.nextElement();
			if (answers != null) {
				if (Objects.equals(request.getParameter(x), "on")) {
					curAnswers.add(x);
				}
			}
		}
		if (answers != null) {
			answers.add(curAnswers);
		}

		if (answers != null) {
			session.setAttribute("answers", answers);
		}
		Util u = new Util();
		if (questions != null) {
			if (curQuestionNumber + 1 == questions.size()) {
				u.check(request, response);
			} else {
				session.setAttribute("cur_question_number", curQuestionNumber + 1);
				u = new Util();
				u.doGetQuestion(request, response);
			}
		}
		Long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
		List<UserTestBean> userPassedTests = DAOFactory.getTestDAO().findUserPassedTest(userId);
		request.setAttribute("passedTests", userPassedTests);
		String forward = Path.PAGE_SOLVE_TEST;
		if (Objects.equals(request.getParameter("stop"), "1")) {
			forward = Path.PAGE_PAGE_USER;
		}
		return forward;
	}
}
