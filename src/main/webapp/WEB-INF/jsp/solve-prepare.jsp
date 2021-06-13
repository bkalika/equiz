<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<html>
<head>
<title>Test</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:choose>
		<c:when test="${role == 'admin'}">
			<h1>${requestScope.test.name}</h1>
		</c:when>
		<c:otherwise>
			<%--<h1>${requestScope.test.testName}</h1> --%>
			<h1>${requestScope.test.name}</h1>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${requestScope.expired}">
		<div>
			<h3>Error!</h3>
			<p>Cannot start solving an expired test.</p>
		</div>
	</c:if>
	<c:if test="${requestScope.require_confirm}">
		<div>
			<h3><my:Locale value="page.test.solve.message" /></h3>
			<p><my:Locale value="page.test.solve.question" /></p>
		</div>
		<form method="post" action="controller">
			<input name="confirm" value="1" type="hidden">
			<c:choose>
				<c:when test="${role == 'admin'}">
					
					<input name="testId" value="${requestScope.test.id}"  type="hidden">
				</c:when>
				<c:otherwise>
					<c:if test="${tests != null}">
						<input name="testId" value="${requestScope.test.id}"  type="hidden">
					</c:if>
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="command" value="goToSolveTestCommand">
			<button type="submit" class="btn btn-success profile"><my:Locale value="page.test.solve.continue"/></button>
		</form>
	</c:if>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
