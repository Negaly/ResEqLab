<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
<head>
<title>Reserves</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<meta charset="utf-8">
</head>
<body>
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
					<li><a href="/reserve"><span
							class="glyphicon glyphicon-tasks"> </span> Reserve</a></li>
					<c:choose>
						<c:when test="${userAdmin}">
							<li><a href="/create"><span
									class="glyphicon glyphicon-pencil"></span> Create</a></li>
							<li><a href="/listReserves"><span
									class="glyphicon glyphicon-tasks"></span> Reserves</a></li>
						</c:when>
					</c:choose>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="<c:url value="${url}"/>"> <c:choose>
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

	

	<table class="table table-striped" style="width: 60%;" align="center">
		<tr>
			<th>Title</th>
			<th>Description</th>
			<c:choose>
				<c:when test="${userAdmin}">
					<th>Action</th>
					<th></th>
				</c:when>
			</c:choose>
		</tr>

		<c:forEach items="${resources}" var="resource">
			<tr>
				<td><c:out value="${resource.title}" /></td>
				<td><c:out value="${resource.description}" /></td>
				<c:choose>
					<c:when test="${userAdmin}">
						<td><a class="btn btn-danger"
							href="<c:url value="/remove?id=${resource.id}" />">Remove</a></td>
					<td><a class="btn btn-warning"
							href="<c:url value="/modifyResource?resourceId=${resource.id}" />">Modify</a></td>
					</c:when>
				</c:choose>

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
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
