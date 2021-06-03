<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title><my:Locale value="page.login.title" /></title>
<link rel="stylesheet" type="text/css" href="../../styles/login.css">
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<!-- This snippet uses Font Awesome 5 Free as a dependency. You can download it at fontawesome.io! -->
	<div class="container">
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body">
						<h5 class="card-title text-center">
							<my:Locale value="page.login.title" />
						</h5>
						<form action="controller" method="post" class="form-signin">
							<input type="hidden" name="command" value="loginCommand">
							<div class="form-label-group">
								<label for="inputEmail">
									<my:Locale value="page.login.username" />
								</label>
								<input type="text" name="username" id="inputEmail" class="form-control"
									placeholder="<my:Locale value="page.login.username"/>" required autofocus>
							</div>

							<div class="form-label-group">
								<input type="password" name="password" id="inputPassword" class="form-control"
									placeholder="<my:Locale value="page.login.password"/>" required>
								<label for="inputPassword">
									<my:Locale value="page.login.password" />
								</label>
							</div>

							<div class="custom-control custom-checkbox mb-3">
								
								<input type="checkbox" name="rememberMe" value="Y" class="custom-control-input" id="customCheck1">
								<label class="custom-control-label" for="customCheck1">
									<my:Locale value="page.login.remember" />
								</label>
							</div>
							
							<div>
								<c:if test="${errorMessage!=\"\"}">
									<div>
										<p>${errorMessage}</p>
									</div>
								</c:if>
							</div>
							<button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">
								<my:Locale value="page.login.login" />
							</button>
							<hr class="my-4">
						</form>
						<div>
							<form action="controller" method="post">
								<input type="hidden" name="command" value="goToUserRegisterCommand">
								<button type="submit" id="register" class="btn btn-lg btn-google btn-block text-uppercase">
									<my:Locale value="page.login.register" />
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>