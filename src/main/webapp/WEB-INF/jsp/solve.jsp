<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%
	int cur_question_number = 0;
	int total = 0;
	if (session.getAttribute("cur_question_number") != null) {
		cur_question_number = (Integer) session.getAttribute("cur_question_number");
	}
	if (session.getAttribute("questions") != null) {
		total = ((List<?>) session.getAttribute("questions")).size();
	}
	int percent = Math.round(100 * cur_question_number / (float) total);
	pageContext.setAttribute("percent", percent);
	pageContext.setAttribute("total", total);
%>
<html>
<head>
<title>Solving the test</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-flat.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="w3-container w3-flat-wisteria">
		<p>Question ${sessionScope.cur_question_number + 1} / ${total}</p>
	</div>
	<div class="w3-container">
		<h2>${cur_question.name}</h2>
		<form method="post">
			<c:forEach var="answer" items="${cur_question.answers}">
				<p>
					<input class="w3-check" type="checkbox" name="${answer.id}" id="${answer.id}"> <label for="${answer.id}">${answer.name}</label>
				</p>
			</c:forEach>
			<hr>
			<input type="hidden" name="command" value="solveCommand">
			<c:choose>
				<c:when test="${sessionScope.cur_question_number + 1 == total}">
					<input name="stop" value="1" type="hidden">
					<button type="submit" class="btn btn-success profile">
						<my:Locale value="page.test.solve.finish" />
					</button>
				</c:when>
				<c:otherwise>
					<input type="submit" name="submit"
						value="<my:Locale value="page.test.solve.continue"/>"
						class="btn btn-success profile">
				</c:otherwise>
			</c:choose>
		</form>
		<div class="w3-light-grey w3-round">
			<div class="w3-container w3-round w3-blue" style="width:${percent}%">${percent}%</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
