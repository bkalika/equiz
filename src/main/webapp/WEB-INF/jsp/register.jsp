<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/locale.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title><my:Locale value="page.login.title" /></title>
<link rel="stylesheet" type="text/css" href="../../styles/forget.css">
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body">
						<h5 class="card-title text-center">
							<my:Locale value="page.register.title" />
						</h5>
						<form action="controller" method="post" class="form-signin">
							<input type="hidden" name="command" value="registerCommand">
							
							<div class="form-label-group">
								<input type="text" name="login" id="inputEmail" class="form-control" placeholder="<my:Locale value="page.login.enter"/> <my:Locale value="page.register.login"/>" required autofocus>
									<label for="inputEmail">
										<my:Locale value="page.login.enter"/> <my:Locale value="page.register.login" />
									</label>
							</div>

							<div class="form-label-group">
								<input type="password" name="password" id="inputPassword" class="form-control"
									placeholder="<my:Locale value="page.login.enter"/> <my:Locale value="page.register.password"/>" required>
								<label for="inputPassword">
									<my:Locale value="page.login.enter"/> <my:Locale value="page.register.password" />
								</label>
							</div>
							
							<div class="form-label-group">
								<input type="password" name="passwordConfirm" id="inputPassword" class="form-control"
									placeholder="<my:Locale value="page.login.enter"/> <my:Locale value="page.register.password.confirm"/>" required>
								<label for="inputPassword">
									<my:Locale value="page.login.enter"/> <my:Locale value="page.register.password.confirm" />
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
								<my:Locale value="page.register.register" />
							</button>
							<hr class="my-4">
						</form>
						<div>
							<button type="submit" id="login" class="btn btn-lg btn-google btn-block text-uppercase">
								<a href="/">
									<my:Locale value="page.login.login" />
								</a>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>