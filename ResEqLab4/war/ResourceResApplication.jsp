<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
<head>
<title>Resources</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<meta charset="utf-8">
</head>
<body>


	<datalist id="mishoras">
		<option label="08:00-09:00" value="8">
		<option label="09:00-09:00" value="9">
		<option label="10:00-09:00" value="10">
		<option label="11:00-09:00" value="11">
		<option label="12:00-09:00" value="12">
		<option label="13:00-09:00" value="13">
		<option label="14:00-09:00" value="14">
		<option label="15:00-09:00" value="15">
	</datalist>



	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/main"><span class="glyphicon glyphicon-home">
						</span> Home</a></li>
					<li><a href="/reserve" data-toggle="modal"
						data-target="#myModal"><span class="glyphicon glyphicon-tasks">
						</span> Reserve</a></li>
					<c:choose>
						<c:when test="${user == 'admin@example.com'}">
							<li><a href="/create"><span
									class="glyphicon glyphicon-pencil"></span> Create</a></li>
						</c:when>
					</c:choose>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="/login.jsp"> <c:choose>
								<c:when test="${user != null}">
									<span class="glyphicon glyphicon-off"> </span>
								</c:when>
								<c:otherwise>
									<span class="glyphicon glyphicon-user"> </span>
								</c:otherwise>
							</c:choose> <c:out value="${urlLinktext}" /></a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>


	<div style="width: 100%;">
		<div class="line"></div>
		<div class="topLine">
			<h1 style="text-align: center">Resources</h1>
			<div style="float: right;"></div>
		</div>
	</div>



	<table class="table" style="width: 60%;" align="center">
		<tr>
			<th>Title</th>
			<th>Description</th>
			<c:choose>
				<c:when test="${user != null}">
					<th>Action</th>
				</c:when>
			</c:choose>
		</tr>

		<c:forEach items="${resources}" var="resource">
			<tr>
				<td><c:out value="${resource.title}" /></td>
				<td><c:out value="${resource.description}" /></td>
				<c:if test="${user != null}">
					<td>
						<form action="/reserve" method="post" accept-charset="utf-8">
							<input type="date">
							<input type="text" list="mishoras">
							<input type="submit" value="Reserve">
						</form>
					</td>
				</c:if>

			</tr>
		</c:forEach>
	</table>



	<hr />






	<footer>
		<h6 small>
			You have a total number of
			<c:out value="${fn:length(resources)}" />
			Resources.
		</h6>
	</footer>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
