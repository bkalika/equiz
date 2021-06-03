<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><my:Locale value="page.admin.title" /></title>
	<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="col-6 offset-md-4">
		<h4>
			<my:Locale value="page.admin.subjects.all" />
		</h4>
		<table border="1" cellpadding="5" cellspacing="1">
			<tr>
				<th><my:Locale value="page.admin.subjects.name" /></th>
				<th><my:Locale value="page.admin.subjects.edit" /></th>
				<th><my:Locale value="page.admin.subjects.delete" /></th>
			</tr>
			<c:forEach items="${subjects}" var="subject">
				<tr>
						<td>
							<form action="controller" method="post">
								<input type="hidden" name="subject_id" value="${subject.id}">
								<input type="text" id="subject_name" name="subject_name" value="${subject.name}">
						</td>
						<td>
								<input type="hidden" name="command" value="updateSubjectCommand">
								<button type="submit" class="spacebtn btn btn btn-primary">
									<my:Locale value="page.admin.subjects.edit" />
								</button>
							</form>
						</td>
						<td>
							<form>
								<input type="hidden" name="subject_id" value="${subject.id}">
								<input type="hidden" name="command" value="deleteSubjectCommand">
								<button type="submit" class="spacebtn btn btn-danger">
									<my:Locale value="page.admin.subjects.delete" />
								</button>
							</form>
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
		<h4>
			<my:Locale value="page.admin.subjects.newcreate" />
		</h4>
		<form method="post" action="controller">
			<input name="name" value="${subject.name}"
				placeholder="<my:Locale value="page.admin.subjects.newcreate"/>">
			<input type="hidden" name="command" value="createSubjectCommand">
			<input type="submit"
				value="<my:Locale value="page.admin.subjects.create" />"
				class="spacebtn btn btn-success"></input>
		</form>
	</div>

	<%-- ==============TESTS============ --%>
	<div class="col-6 offset-md-2">
		<h4>
			<my:Locale value="page.admin.tests.all" />
		</h4>
		<table border="1" cellpadding="2" cellspacing="1">
			<tr>
				<th><my:Locale value="page.admin.tests.subjectname" /></th>
				<th><my:Locale value="page.admin.tests.name" /></th>
				<th><my:Locale value="page.admin.tests.name" /></th>
				<th><my:Locale value="page.admin.tests.score" /></th>
				<th><my:Locale value="page.admin.tests.deadline" /></th>
				<th><my:Locale value="page.admin.tests.level" /></th>
				<th><my:Locale value="page.admin.tests.popularity" /></th>
				<th><my:Locale value="page.admin.tests.duration" /></th>
				<th><my:Locale value="page.admin.tests.edit" /></th>
				<th><my:Locale value="page.admin.tests.delete" /></th>
			</tr>
			<c:forEach var="test" items="${tests}">
				<tr>
					<td>
						<p>${test.subject.name }</p>
					</td>
					<td>
						<div>
							<form method="POST" action="controller">
								<input type="hidden" name="testId" value="${test.id}"> <input
									type="hidden" name="command" value="goToUpdateTestCommand">
								<button type="submit" class="btn btn-link">${test.name}</button>
							</form>
						</div>
					</td>
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="test_id" value="${test.id}"> <input
								type="text" id="test_name" name="test_name" value="${test.name}">
					</td>
					<td>
						<div>
							<%--<select name="test_score">
								<c:forEach var = "i" begin = "1" end = "30">
									<option value="${i}"
										<c:if test="${test.score == i }">selected</c:if>>${i}
									</option>
								</c:forEach>
							</select>  --%>
							<p>${test.score}</p>
						</div>
					</td>
					<td>
						<input type="datetime-local" id="deadline" name="deadline" value="${test.deadline}" min="2021-06-01T00:00" max="2030-06-14T00:00">
					</td>
					<td>
						<div>
							<select name="test_level">
								<c:forEach var="i" begin="1" end="5">
									<option value="${i}"
										<c:if test="${test.level == i }">selected</c:if>>${i}
									</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td>
						<p>${test.popularity}</p>
					</td>
					<td>
						<div>
							<select name="test_duration">
								<c:forEach var="i" begin="1" end="60">
									<option value="${i}"
										<c:if test="${test.duration == i }">selected</c:if>>${i}
									</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td>
							<input type="hidden" name="command" value="updateTestCommand">
							<button type="submit" class="spacebtn btn btn btn-primary">
								<my:Locale value="page.admin.tests.edit" />
							</button>
						</form>
					</td>
					<td>
						<form>
							<input type="hidden" name="test_id" value="${test.id}">
							<input type="hidden" name="command" value="deleteTestCommand">
							<button type="submit" class="spacebtn btn btn-danger">
								<my:Locale value="page.admin.tests.delete" />
							</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="col-6 offset-md-4">
		<h4>
			<my:Locale value="page.admin.tests.newcreate" />
		</h4>
		<form method="post" action="controller">
			<div>
				<label for="subject_id">
				<my:Locale value="page.admin.tests.new.subject" />
				</label>
				<select name="subject_id">
					<c:forEach var="subject" items="${subjects}">
						<option value="${subject.id}">${subject.name}</option>
					</c:forEach>
				</select>
			</div>
			<br />
			<label for="test_name"><my:Locale value="page.admin.tests.new.name" /></label>
			<input name="test_name" value="${test.name}" placeholder="<my:Locale value="page.admin.tests.new.name"/>">
			<br /> <label for="deadline"><my:Locale value="page.admin.tests.new.deadline" /></label>
			<input type="datetime-local" id="deadline" name="deadline" value="2021-06-01T00:00" min="2021-06-01T00:00" max="2030-06-14T00:00"><br/>
			<%--<div>
				<label for="test_score"><my:Locale value="page.admin.tests.new.score" /></label>
				<select name="test_score">
					<c:forEach var = "i" begin = "1" end = "30">
						<option value="1">${i}</option>
					</c:forEach>
				</select>
			</div> --%>
			<div>
				<label for="level"><my:Locale value="page.admin.tests.new.level"/></label>
				<select name="level">
					<c:forEach var="i" begin="1" end="5">
						<option value="${i}">${i}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label for="duration"><my:Locale value="page.admin.tests.new.duration" /></label>
				<select name="duration">
					<c:forEach var="i" begin="1" end="60">
						<option value="${i}">${i}</option>
					</c:forEach>
				</select>
			</div>
			<input type="hidden" name="command" value="createTestCommand">
			<input type="submit"
				value="<my:Locale value="page.admin.tests.create" />"
				class="spacebtn btn btn-success"></input>
		</form>
	</div>

	<%-- =====================USERS========================== --%>

	<div class="col-6 offset-md-4">
		<h4>
			<my:Locale value="page.admin.users.all" />
		</h4>
		<table border="1" cellpadding="5" cellspacing="1">
			<tr>
				<th><my:Locale value="page.admin.users.name" /></th>
				<th><my:Locale value="page.admin.users.password" /></th>
				<th><my:Locale value="page.admin.users.role" /></th>
				<th><my:Locale value="page.admin.users.edit" /></th>
			</tr>
			<c:forEach items="${users}" var="user">
				<tr>
					<form action="controller" method="post">
						<td><input type="hidden" name="user_id" value="${user.id}">
							<input type="text" id="user_name" name="user_name"
							value="${user.name}"></td>
						<td><input type="password" id="user_password"
							name="user_password" value="${user.password}"></td>
						<td>
							<div>
								<select name="role_id">
									<c:forEach var="role" items="${roles}">
										<option value="${role.id}"
											<c:if test="${user.role.name == role.name }">selected</c:if>>${role.name}
										</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td><input type="hidden" name="command"
							value="updateUserCommand">
							<button type="submit" class="spacebtn btn btn btn-primary">
								<my:Locale value="page.admin.users.edit" />
							</button></td>
					</form>
				</tr>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>