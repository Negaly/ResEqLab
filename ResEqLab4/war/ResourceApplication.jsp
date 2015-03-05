<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Resources</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<meta charset="utf-8">
	</head>
	<body>
		 <nav class="navbar navbar-default" role="navigation">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.html"><img class="img-responsive" src="images/logo2.png"></img></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            
            <li><a href="/reservar" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-tasks"> </span>Reservar</a></li>
            <li><a href="/create"><span class="glyphicon glyphicon-leaf"> </span> Crear</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="<c:url value="${url}"/>"><span class="glyphicon glyphicon-user"> </span><c:out value="${urlLinktext}"/></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
		<div style="width: 100%;">
			<div class="line"></div>
			<div class="topLine">
				<div style="float: left;" class="headline">Resources</div>
				<div style="float: right;">
					
				</div>
			</div>
		</div>
	
		<div style="clear: both;" />
		You have a total number of <c:out value="${fn:length(resources)}" />
		Resources.
	
		<table>
			<tr>
				<th>Title</th>
				<th>Description</th>
				
			</tr>
	
			<c:forEach items="${resources}" var="resource">
				<tr>
					<td><c:out value="${resource.title}" /></td>
					<td><c:out value="${resource.description}" /></td>
					<td><a class="done"
						href="<c:url value="/remove?id=${resource.id}" />">Remove</a></td>
					
					
				</tr>
			</c:forEach>
		</table>
	
	
		<hr />
	
		<div class="main">
	
			<div class="headline">New resource</div>
	
					<form action="/new" method="post" accept-charset="utf-8">
						<table>
							<tr>
								<td><label for="title">Title</label></td>
								<td><input type="text" name="title" id="title" size="65" /></td>
							</tr>
							<tr>
								<td valign="description"><label for="description">Description</label></td>
								<td><textarea rows="4" cols="50" name="description"
										id="description"></textarea></td>
							</tr>
							
							<tr>
								<td colspan="2" align="right"><input type="submit"
									value="Create" /></td>
							</tr>
						</table>
					</form>

		</div>
	</body>
</html>
