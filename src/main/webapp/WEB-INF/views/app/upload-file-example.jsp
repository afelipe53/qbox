<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=100" />
		<title>Home - AFJ</title>
	</head>
<body>
	<h2>Home do sistema</h2>
	
	<form action="upload-file-submit" method="post" enctype="multipart/form-data">
		<p>
			<label for="file">Arquivo para fazer upload</label> <br />
			<input type="file" name="file" /> <br />
			<input type="submit" name="submit" value="Upload" /> <br />
		</p>
	</form>

</body>
</html>