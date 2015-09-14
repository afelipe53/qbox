<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ page isErrorPage="true"%>

<!DOCTYPE html>
<html>
<head>
	<!-- Standard Meta -->
  	<meta charset="utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

 	<!-- Site Properities -->
	<title>Página não encontrada - QBox</title>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/semantic-ui/semantic.css" />">
  	<link href="<c:url value="/resources/favicon.ico" />" rel="icon" type="image/x-icon" />

  	<script src="<c:url value="/resources/jquery/jquery.min.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe-content.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe.js" />"></script>
  	<script src="<c:url value="/resources/semantic-ui/semantic.js" />"></script>
  	<script src="<c:url value="/resources/css/css.css" />"></script>

</head>

<body>

	<div class="ui grid container">

		<div class="row">
			<div class="column">
				<h1 class="ui header">Página não encontrada</h1>
			</div>
		</div>
	</div>

</body>

</html>