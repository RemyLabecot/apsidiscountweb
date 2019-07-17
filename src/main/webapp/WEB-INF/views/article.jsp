<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<section>
		<h1>
			Article -
			<c:out value="${message}" />
		</h1>
		<div>
			<form method="post" action="${article.id}" accept-charset="iso-8859-1" >
				<input type="text" name="designation"
					value="<c:out value="${article.designation}" />"> <br>
				<textarea name="description" rows="10" cols="50">
					<c:out value="${article.description}" />
				</textarea>
				<br>
				<button type="submit">Enregistrer</button>
			</form>
		</div>
	</section>
</body>
</html>