package com.equiz.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.equiz.Path;
import com.equiz.db.beans.UserTestBean;
import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Answer;
import com.equiz.db.dtos.Question;
import com.equiz.db.dtos.Test;
import com.equiz.db.dtos.User;

public class Util {
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	public static void storeUserCookie(HttpServletResponse response, User user) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getName());
		cookieUserName.setMaxAge(24 * 60 * 60); // 1 day:
		response.addCookie(cookieUserName);
	}

	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}

	public String check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		List<List<String>> answers = (List<List<String>>) session.getAttribute("answers");
		Long userId = (Long) session.getAttribute("user_id");
		Test test = (Test) session.getAttribute("test");
		UserTestBean userTest = new UserTestBean(); // (UserTestBean) session.getAttribute("test");

		int score = 0;
		for (int i = 0; i < Math.min(questions.size(), answers.size()); i++) {
			Set<Long> answerSet = questions
					.get(i)
					.getAnswers()
					.stream()
					.filter(Answer::getIsCorrect)
					.map(Answer::getId)
					.collect(Collectors.toSet());
			Set<Long> userAnswerSet = answers
					.get(i)
					.stream()
					.map(Long::parseLong)
					.collect(Collectors.toSet());
			
			if (answerSet.containsAll(userAnswerSet) && userAnswerSet.containsAll(answerSet)) {
				score += questions.get(i).getScore();
			}
		}
		userTest.setScore(Long.valueOf(score));
		userTest.setPassedDate(LocalDateTime.now());
		userTest.setTestId(test.getId());
		userTest.setUserId(userId);
		DAOFactory.getTestDAO().update(userTest);
		session.setAttribute("test", null);
		session.setAttribute("questions", null);
		session.setAttribute("cur_question_number", null);
		session.setAttribute("answers", null);
		return Path.PAGE_SOLVE_TEST;
	}

	public String doGetQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		int curQuestionNumber = (Integer) session.getAttribute("cur_question_number");
		Question question = questions.get(curQuestionNumber);
		request.setAttribute("cur_question", question);
		return Path.PAGE_SOLVE_TEST;
	}

}
