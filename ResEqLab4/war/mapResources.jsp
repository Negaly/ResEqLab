<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
						<li><a href="/map"><span class="glyphicon glyphicon-th"> 
						</span> Map</a></li>
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
			<h1 style="text-align: center">Maps</h1>
			<div style="float: right;"></div>
		</div>
	</div>

	<div class=container>
		<c:if test="${dialogo != null}">
			<div class="alert alert-success" style="width: 100%;">
				<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>${dialogo}</strong>
			</div>
		</c:if>

		<script>
			function reserve() {

				alert("Este recurso no se puede reservar");
			}
		</script>

		<div class="izqMap">
			<table class="table" style="width: 20%;">

				<tr>

					<div class="input-group date">

						<span class="input-group-addon"> <span id="datepick"
							class="glyphicon glyphicon-calendar"></span>

						</span> <input type="date" class="form-control" id="datepick" name="date">

					</div>

				</tr>

				<tr>

					<td><label for="title">Hora:</label></td>

					<td><select>

							<c:forEach var="i" begin="0" end="23">

								<c:if test="${i <= 9 }">

									<option>0${i}:00</option>

								</c:if>

								<c:if test="${i>9}">

									<option>${i}:00</option>

								</c:if>

							</c:forEach>

					</select></td>

				</tr>

				<tr>

					<td><button type="button">Buscar</button></td>

				</tr>
			</table>
		</div>




		<div class=mapSquare>
			<c:forEach var="i" begin="0" end="4">
				<c:forEach var="j" begin="0" end="4">
					<c:if test="${mapa[i][j] == 'recurso'}">
						<a href="/reserve"><img src="/images/silla.png" class=map></img></a>
					</c:if>
					<c:if test="${mapa[i][j] == 'ocupado'}">
						<img src="/images/silla2.png" class=mapOc onclick="reserve()"></img>
					</c:if>
					<c:if test="${mapa[i][j] == null}">
						<img src="/images/ladrillo.jpg" class=map></img>
					</c:if>

					<c:if test="${i == 5}">
						<br>
					</c:if>
				</c:forEach>
			</c:forEach>
		</div>
	</div>

	<hr />

	<footer> </footer>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
