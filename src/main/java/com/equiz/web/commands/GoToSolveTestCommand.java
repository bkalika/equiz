package com.equiz.web.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.equiz.Path;
import com.equiz.db.beans.UserTestBean;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Answer;
import com.equiz.db.dtos.Question;
import com.equiz.db.dtos.Test;
import com.equiz.utils.Util;

@WebServlet(name = "GoToSolveTestCommand")
public class GoToSolveTestCommand extends Command {
	public static final Logger LOG = Logger.getLogger(GoToSolveTestCommand.class);

	private static final long serialVersionUID = -2461633132107641718L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.trace("Start tracing GoToSolveTestCommand");
		HttpSession session = request.getSession();
		Long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
		String role = String.valueOf(session.getAttribute("role"));
		Util u = new Util();
		if (session.getAttribute("test") == null) {
			doGetStart(request, response);
		} else if (Objects.equals(request.getParameter("stop"), "1")) {

			u.check(request, response);
		} else {
			u = new Util();
			u.doGetQuestion(request, response);
		}
		return Path.PAGE_SOLVE_TEST;
	}

	private String doGetStart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LocalDateTime date = LocalDateTime.now();
		HttpSession session = request.getSession();
		Long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
		String role = String.valueOf(session.getAttribute("role"));
		Long testId = 0l;
		testId = Long.parseLong(String.valueOf(request.getParameter("testId")));
//		UserTestBean userTest = DAOFactory.getTestDAO().findUserTestByTestId(userId, testId);
		Test userTest = DAOFactory.getTestDAO().find(testId);
		if (!role.equals("admin")) {
			request.setAttribute("test", userTest);
			LocalDateTime deadline = userTest.getDeadline();
			if (date.compareTo(deadline) > 0) {
				request.setAttribute("expired", true);
				RequestDispatcher rd = request.getRequestDispatcher(Path.PAGE_SOLVE_PREPARE);
				rd.forward(request, response);
				return Path.PAGE_SOLVE_PREPARE;
			}
		}
		if (Objects.equals(request.getParameter("confirm"), "1")) {
			doGetConfirm(request, response);
		} else {
			request.setAttribute("require_confirm", true);
			RequestDispatcher rd = request.getRequestDispatcher(Path.PAGE_SOLVE_PREPARE);
			rd.forward(request, response);
		}
		return Path.PAGE_SOLVE_PREPARE;
	}

	private String doGetConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));

		Long testId = Long.parseLong(request.getParameter("testId"));
//		UserTestBean userTest = DAOFactory.getTestDAO().findUserTestByTestId(userId, (Long.valueOf(testId)));
		Test userTest = DAOFactory.getTestDAO().find((Long.valueOf(testId)));

		List<Question> questions = DAOFactory.getQuestionDAO().findAll(Long.valueOf(testId));

		session.setAttribute("user_id", userId);
		session.setAttribute("test", userTest);
		session.setAttribute("questions", questions);
		session.setAttribute("cur_question_number", 0);
		session.setAttribute("answers", new ArrayList<List<String>>());
		Util u = new Util();
		u.doGetQuestion(request, response);
		Test test = DAOFactory.getTestDAO().find(testId);
		DAOFactory.getTestDAO().increaseTestPopularity(test);
		return Path.PAGE_SOLVE_TEST;
	}

}
