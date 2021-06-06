<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="{% 'css/styles.css' %}">
<title>eQuiz</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<form method="post" action="controller">
			<input type="hidden" name="command" value="goToUserCommand">
			<button type="submit" class="btn btn-link">
				eQuiz
			</button>
		</form>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<form method="post" action="controller">
						<input type="hidden" name="command" value="subjectCommand">
						<button type="submit" class="btn btn-link">
							<my:Locale value="page.subject.name"/>
						</button>
					</form>
				</li>
				<c:if test="${user.role.name == 'admin'}">
					<li class="nav-item">
						<form method="post" action="controller">
							<input type="hidden" name="command" value="adminCommand">
							<button type="submit" class="btn btn-link">Admin</button>
						</form>
					</li>
				</c:if>
				<li class="nav-item">
					<form method="post">
						<input type="hidden" name="command" value="logoutCommand">
						<button type="submit" id="register" class="btn btn-link">
							<my:Locale value="page.header.logout" />
						</button>
					</form>
				</li>
				<c:if test="${user == null}">
				<li class="nav-item">
					<div id="flags">
						<form action="controller" method="post">
							<input type="hidden" name="command" value="languageCommand">
							<input type="hidden" name="language" value="en">
							<input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
							<input type="image" src="../../img/us.png" alt="US">
						</form>
					</div>
				</li>
				
				<li class="nav-item">
					<div id="flags">
						<form action="controller" method="post">
							<input type="hidden" name="command" value="languageCommand">
							<input type="hidden" name="language" value="uk">
							<input type="hidden" name=url value="${requestScope['javax.servlet.forward.query_string']}">
							<input type="image" src="../img/ua.png" alt="UK">
						</form>
					</div>
				</li>
				</c:if>
				<p>${user.name}</p>
			</ul>
		</div>
	</nav>
	<div class="container mt-5"></div>

</body>
</html>
