<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Resources</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<meta charset="utf-8">
	</head>
	<body>
	
		<div style="width: 100%;">
			<div class="line"></div>
			<div class="topLine">
				<div style="float: left;" class="headline">Resources</div>
				<div style="float: right;">
					<a
						href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
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
