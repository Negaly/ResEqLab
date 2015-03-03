<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>Todos</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<meta charset="utf-8">
</head>
<body>
	<div style="width: 100%;">
		<div class="line"></div>
		<div class="topLine">
			<div style="float: left;" class="headline">Todos</div>
			<div style="float: right;">
				<a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}" /></a>
				<c:if test="${user != null}">
					<c:out value="${user.nickname}" />
				</c:if>
			</div>
		</div>
	</div>
	<div style="clear: both;" />
	You have a total number of ${fn:length(todos)} Todos.

	
		<table>
<tr>
<th>Short description</th>
<th>Long Description</th>
<th>URL</th>
<th>Done</th>
</tr>
<c:forEach items="${todos}" var="todo">
<tr>
<td><c:out value="${todo.shortDescription}" /></td>
<td><c:out value="${todo.longDescription}" /></td>
<td><c:out value="${todo.url}" /></td>
<td><a class="done" href="<c:url value="/done?id=${todo.id}"
/>">Done</a></td>
</tr>
</c:forEach>
</table>
<hr />
<div class="main">
<div class="headline">New todo</div>
<c:choose>
<c:when test="${user != null}">
<form action="/new" method="post" accept-charset="utf-
8">
<table>
<tr>
<td><label for="summary">Summary</label></td>
<td><input type="text" name="summary" id="summary" size="65" /></td>
</tr>
<tr>
<td valign="description"><label for="description">Description</label></td>
<td><textarea rows="4" cols="50" name="description"
										id="description"></textarea></td>
</tr>
<tr>
<td valign="top"><label for="url">URL</label></td>
<td><input type="url" name="url" id="url" size="65" /></td>
</tr>
<tr>
<td colspan="2" align="right"><input type="submit" value="Create" /></td>
</tr>
</table>
</form>
</c:when>
<c:otherwise>
Please login with your Google account
</c:otherwise>
</c:choose>
</div>
</body>
</html>