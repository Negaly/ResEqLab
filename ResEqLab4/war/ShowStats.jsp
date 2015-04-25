<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
<head>
<title>Statistics</title>
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
					<li><a href="/main"> <span
							class="glyphicon glyphicon-home"></span> Home
					</a></li>
					<li><a href="/map"> <span class="glyphicon glyphicon-th"></span>
							Map
					</a></li>
					<li><a href="/reserve"> <span
							class="glyphicon glyphicon-tasks"> </span> Reserve
					</a></li>
					<c:choose>
						<c:when test="${userAdmin}">
							<li><a href="/create"><span
									class="glyphicon glyphicon-pencil"></span> Create</a></li>
							<li><a href="/listReserves"><span
									class="glyphicon glyphicon-tasks"></span> Reserves</a></li>
							<li><a href="/stats"> <span
									class="glyphicon glyphicon-tasks"></span> Statistics
							</a></li>
							<!-- Si no gusta glyphicon-object-align-bottom, poner  glyphicon-wrench o glyphicon-tasks o glyphicon-signal-->
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
			<h1 style="text-align: center">Statistics</h1>
			<div style="float: right;"></div>
		</div>
	</div>

	<div class=container>
		<c:if test="${dialogo != null}">
			<div class="alert alert-success" style="width: 100%;">
				<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>${dialogo}</strong>
			</div>
		</c:if>

		<div
			style="padding: 10px; float: left; width: 35%; text-align: justify;">
			<table class="table">
				<!-- 
				<tr>
					<td><label for="title">Número de visitas:</label></td>
					<td>${nVisitas}</td>
				</tr>
				-->
				<tr>
					<td><label for="title">Número de usuarios:</label></td>
					<td>${nUsuarios}</td>
				</tr>
				<tr>
					<td><label for="title">Número de recursos:</label></td>
					<td>${nRecursos}</td>
				</tr>
				<tr>
					<td><label for="title">Número de reservas:</label></td>
					<td>${nReservas}</td>
				</tr>
				<tr>
					<td><label for="title">Usuario actual:</label></td>
					<td>${user}</td>
				</tr>

				<tr>
					<td><label for="title">Número de Libres:</label></td>
					<td>${nRecursos - nOcupadas}</td>
				</tr>
				<tr>
					<td><label for="title">Número de Ocupadas:</label></td>
					<td>${nOcupadas}</td>
				</tr>
			</table>
			<div id="GraficoGoogleChart-4" style="width: 400px; height: 300px"></div>

		</div>
		<div
			style="padding: 10px; float: right; width: 65%; text-align: justify;">

			<div id="GraficoGoogleChart-2" style="width: 700px; height: 500px"></div>

			<div id="GraficoGoogleChart-4" style="width: 700px; height: 500px"></div>

		</div>
	</div>

	<footer> </footer>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script>
	   google.load("visualization", "1", {packages:["corechart"]});
	   google.setOnLoadCallback(dibujarGrafico);
	   function dibujarGrafico() {
	     
		 //--Grafico 2--
	     // Tabla de datos: valores y etiquetas de la gráfica
	     var data2 = google.visualization.arrayToDataTable([
	       ['Día','Nº Reservas'],
	       ['Lunes', ${nResMonday}],
	       ['Martes', ${nResThuesday}],
	       ['Miércoles', ${nResWednesday}],
	       ['Jueves', ${nResThursday}],
	       ['Viernes', ${nResFriday}]    
	     ]);
	     var options2 = {
	       title: 'Número de Reservas de esta semana por día',
           legend: 'none',
           vAxis: { gridlines: { count: 10 } }
	       //is3D: true
	     }
	          
		 //--Grafico 4-- Porcentaje de reservas hoy
	     // Tabla de datos: valores y etiquetas de la gráfica
	     var data4 = google.visualization.arrayToDataTable([
	       ['Estado','Nº Puestos'],
	       ['Libre', ${nRecursos - nOcupadas}],
	       ['Ocupado', ${nOcupadas}]  

	     ]);
	     var options4 = {
	       title: 'Porcentaje de puestos ocupados en este momento',
           legend: 'none',
           pieHole: 0.5
           //vAxis: { gridlines: { count: 10 } }
	       //is3D: true
	     }
	     // Dibujar el gráfico

	     new google.visualization.ColumnChart( 
	   	     //ColumnChart sería el tipo de gráfico a dibujar
	   	       document.getElementById('GraficoGoogleChart-2')
	   	     ).draw(data2, options2);

	     new google.visualization.PieChart( 
    	     //ColumnChart sería el tipo de gráfico a dibujar
    	       document.getElementById('GraficoGoogleChart-4')
    	     ).draw(data4, options4);
	   }
	 </script>
</body>
</html>
