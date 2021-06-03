<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<title><my:Locale value="page.subjects.title" /></title>
<link rel="stylesheet" type="text/css" href="../../styles/courses.css">
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
<script src="../../bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="col-6 offset-md-5">
		<table border="1" cellpadding="2" cellspacing="1">
			<tr>
				<th><my:Locale value="page.subject.yours" /></th>
			</tr>
			<c:forEach items="${subjects}" var="subject">
				<div>
					<form method="post">
						<tr>
							<td>
								<div>
									<form method="post" action="controller">
										<input type="hidden" name="subjectId" value="${subject.id}">
										<input type="hidden" name="command" value="testCommand">
										<button type="submit" class="btn btn-link">${subject.name}</button>
									</form>
								</div>
							</td>
						</tr>
					</form>
				</div>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
