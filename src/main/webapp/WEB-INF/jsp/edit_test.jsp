<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title><my:Locale value="page.admin.title"/></title>
		<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
		<script src="../../bootstrap/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
	</head>
	<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="col-6 offset-md-2">
		<h4><my:Locale value="page.admin.questions.all" /></h4>
		<table border="1" cellpadding="4" cellspacing="1">
			<tr>
				<th><my:Locale value="page.admin.questions.name" /></th>
				<th><my:Locale value="page.admin.questions.score" /></th>
				<th><my:Locale value="page.admin.questions.single" /></th>
				<th><my:Locale value="page.admin.questions.answers" /></th>
				<th><my:Locale value="page.admin.questions.edit" /></th>
				<th><my:Locale value="page.admin.questions.delete" /></th>
			</tr>
			<c:forEach var="question" items="${questions}">
				<tr>
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="question_id" value="${question.id}">
							<input type="text" id="question_name" name="question_name" value="${question.name}">
					</td>
					<td>
						<div>
							<select name="question_score">
								<c:forEach var = "i" begin = "1" end = "3">
									<option value="${i}"
										<c:if test="${question.score == i }">selected</c:if>>${i}
									</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td>
						<select name="question_single">
								<option value="${question.isSingle}"
									<c:if test="${question.isSingle == false }">selected</c:if>>${question.isSingle}
								</option>
								<option value="${!question.isSingle}">${!question.isSingle}</option>
						</select>
					</td>
					<td>
						<input type="hidden" name="test_id" value="${question.test.id}">
						<input type="hidden" name="command" value="updateQuestionCommand">
						<button type="submit" class="spacebtn btn btn btn-primary">
							<my:Locale value="page.admin.questions.edit"/>
						</button>
						</form>
					</td>
					<td>
						<form>
							<input type="hidden" name="test_id" value="${question.test.id}">
							<input type="hidden" name="question_id" value="${question.id}">
							<input type="hidden" name="command" value="deleteQuestionCommand">
							<button type="submit" class="spacebtn btn btn-danger">
								<my:Locale value="page.admin.questions.delete"/>
							</button>
						</form>
					</td>
					<td>
						<%--<c:forEach items="${question.answers}" var="answer">
							<input type="text" id="answer_name" name="answer_name" value="${answer.name}">
							<input type="text" id="is_correct" name="is_correct" value="${answer.isCorrect}">
						</c:forEach>  --%>
						<div>
							<form method="POST" action="controller">
								<input type="hidden" name="question_id" value="${question.id}">
								<input type="hidden" name="command" value="goToUpdateAnswerCommand">
								<button type="submit" class="btn btn-link"><p><my:Locale value="page.admin.questions.answers" /></p></button>
							</form>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="col-6 offset-md-4">
		<c:if test="${errorMessage!=\"\"}">
			<div>
				<p>${errorMessage}</p>
			</div>
		</c:if>
	</div>
	
	<div class="col-6 offset-md-4">
		<h4><my:Locale value="page.admin.questions.newcreate" /></h4>
		<form method="POST" action="controller">
			<label for="name"><my:Locale value="page.admin.questions.new.name" /></label>
			<input name="name" value="${question.name}" placeholder="<my:Locale value="page.admin.questions.new.name"/>">
			<br/>
			<label for="score"><my:Locale value="page.admin.questions.score" /></label>
				<select name="score">
					<c:forEach var = "i" begin = "1" end = "3">
						<option value="${i}">${i}</option>
					</c:forEach>
				</select>
			<br/>
			<label for="is_single"><my:Locale value="page.admin.questions.single" /></label>
			<select name="is_single">
				<option value="true" selected>true</option>
				<option value="false">false</option>
			</select>
			<br/>
			<input type="hidden" name="testId" value="${testId}">
			<input type="hidden" name="command" value="createQuestionCommand">
			<input type="submit" value="<my:Locale value="page.admin.questions.newcreate" />" class="spacebtn btn btn-success"></input>
		</form>
	</div>
	
	<%-- =====================USERS========================== --%>
	<%--
	<div class="col-6 offset-md-4">
		<h4><my:Locale value="page.admin.users.all" /></h4>
		<form action="controller" method="POST">
			<select multiple size="10" name="user_id">
				<c:forEach var="user" items="${usersWithoutThisTest}">
					<option value="${user.id}"
						<c:if test="${user.id == user.id }">selected</c:if>>${user.name}
					</option>
				</c:forEach>
			</select>
			<br/>
			<input type="hidden" name="test_id" value="${testId}">
			<input type="hidden" name="command" value="addUsersToTheTestCommand">
			<input type="submit" value="<my:Locale value="page.admin.questions.newcreate" />" class="spacebtn btn btn-success"></input>
		</form>
	</div>
	
	<div class="col-6 offset-md-4">
		<h4><my:Locale value="page.admin.users.all" /></h4>
		<select multiple size="10" name="user_id">
			<c:forEach var="user" items="${usersWithThisTest}">
				<option value="${user.id}"
					<c:if test="${user.id == user.id }">selected</c:if>>${user.name}
				</option>
			</c:forEach>
		</select>
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
	</body>
	 --%>
</html>