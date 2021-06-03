<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title><my:Locale value="page.admin.title"/> </title>
		<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
		<script src="../../bootstrap/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
	</head>
	<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="col-6 offset-md-3">
		<h4><my:Locale value="page.admin.answers.all" /></h4>
		<table border="1" cellpadding="4" cellspacing="1">
			<tr>
				<th><my:Locale value="page.admin.answers.name" /></th>
				<th><my:Locale value="page.admin.answers.correct" /></th>
				<th><my:Locale value="page.admin.answers.edit" /></th>
				<th><my:Locale value="page.admin.answers.delete" /></th>
			</tr>
			<c:forEach var="answer" items="${answers}">
				<tr>
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="answer_id" value="${answer.id}">
							<input type="text" id="answer_name" name="answer_name" value="${answer.name}">
					</td>
					<td>
						<select name="answer_correct">
								<option value="${answer.isCorrect}"
									<c:if test="${answer.isCorrect == false }">selected</c:if>>${answer.isCorrect}
								</option>
								<option value="${!answer.isCorrect}">${!answer.isCorrect}</option>
						</select>
					</td>
					<td>
						<input type="hidden" name="question_id" value="${question_id}">
						<input type="hidden" name="command" value="updateAnswerCommand">
						<button type="submit" class="spacebtn btn btn btn-primary">
							<my:Locale value="page.admin.answers.edit"/>
						</button>
						</form>
					</td>
					<td>
						<form>
							<input type="hidden" name="question_id" value="${question_id}">
							<input type="hidden" name="answer_id" value="${answer.id}">
							<input type="hidden" name="command" value="deleteAnswerCommand">
							<button type="submit" class="spacebtn btn btn-danger">
								<my:Locale value="page.admin.answers.delete"/>
							</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="col-6 offset-md-3">
		<c:if test="${errorMessage!=\"\"}">
			<div>
				<p>${errorMessage}</p>
			</div>
		</c:if>
	</div>
	
	<div class="col-6 offset-md-3">
		<h4><my:Locale value="page.admin.answers.newcreate" /></h4>
		<form method="POST" action="controller">
			<label for="answer_name"><my:Locale value="page.admin.answers.new.name" /></label>
			<input name="answer_name" value="${answer.name}" placeholder="<my:Locale value="page.admin.answers.new.name"/>">
			<br/>
			<label for="answer_correct"><my:Locale value="page.admin.answers.correct" /></label>
			<select name="answer_correct">
				<option value="true" selected>true</option>
				<option value="false">false</option>
			</select>
			<br/>
			<input type="hidden" name="question_id" value="${question_id}">
			<input type="hidden" name="command" value="createAnswerCommand">
			<input type="submit" value="<my:Locale value="page.admin.answers.newcreate" />" class="spacebtn btn btn-success"></input>
		</form>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>