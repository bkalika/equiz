<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<html>
<c:set var="title" value="Error" scope="page" />
<title><my:Locale value="page.error.page" /></title>
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
<script src="../../bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../../styles/error.css">
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container-fluid bs-const">
		<div class="col-lg-3"></div>
		<div class="col-lg-6">
			<table id="main-container">
				<tr>
					<td><img src="/img/error.png" class="message"></td>
					<td class="cntnt">
						<h2 class="error">The following error occurred</h2>
						<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}" scope="page" /> <c:set var="message"
							value="${requestScope['javax.servlet.error.message']}" />
							<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}" />
							<c:if test="${not empty code}">
							<h3>Error code: ${code}</h3>
						</c:if> <c:if test="${code == '500'}">
							<c:set var="errorMessage" value="internal server error" scope="page" />
						</c:if>
						<c:if test="${not empty message}">
							<h3>${message}</h3>
						</c:if> <c:if test="${not empty exception}">
							<%
							exception.printStackTrace(new PrintWriter(out));
							%>
						</c:if>
						<c:if test="${not empty requestScope.errorMessage}">
							<h3>${requestScope.errorMessage}</h3>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>