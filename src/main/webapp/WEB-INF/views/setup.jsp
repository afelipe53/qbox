<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<head>
	<!-- Standard Meta -->
  	<meta charset="utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

 	<!-- Site Properities -->
	<title>Configurar ambiente - QBox</title>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/semantic-ui/semantic.css" />">
  	<link href="<c:url value="/resources/favicon.ico" />" rel="icon" type="image/x-icon" />

  	<script src="<c:url value="/resources/jquery/jquery.min.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe-content.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe.js" />"></script>
  	<script src="<c:url value="/resources/semantic-ui/semantic.js" />"></script>
  	<script src="<c:url value="/resources/css/css.css" />"></script>

  	<script>
		  $(document).ready(function() { $('.ui.form').form({ }); });
  	</script>

</head>

<body>

	<div class="ui grid container">

		<div class="row">
			<div class="column"> </div>
		</div>

		<div class="row">
			<div class="column">
				<div class="ui message">
					<img class="ui tiny left floated image" src="<c:url value="/resources/img/qbox.png" />">
					<h1 class="ui header">QBox Armazenamento de arquivos na web!</h1>
					<p>O QBox é um aplicativo web para que você possa armazenar, gerenciar e compartilhar seus arquivos na web.</p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="column">
		    	<h1 class="ui header">Configurar ambiente</h1>
		    	<div class="ui divider"></div>
		    	
		    	<form action="do-setup" method="post" class="ui large form">
					<div class="field">
				    	<div class="ui corner labeled input" style="width: 30%;">
		      				<input type="text" placeholder="Pasta raíz de arquivos" name="rootVolumeFolder">
		      				<div class="ui corner label">
		        				<i class="asterisk icon"></i>
		      				</div>
		    			</div>
		    		</div>

  					<c:if test="${!empty param.setupMessage}">
						<div class="ui message" 
							style="box-shadow: 0px 0px 0px 1px #e0b4b4 inset, 0px 0px 0px 0px rgba(0, 0, 0, 0); 
								background-color: #fff6f6; color: #9f3a38; width: 30%;">

							<c:out value="${param.setupMessage}" />
						</div>
					</c:if>

  					<div class="ui fluid teal submit button" style="width: 30%;">Configurar ambiente</div>
    			</form>
    			            			
			</div>
		</div>

	</div>
	
	<jsp:include page="template/footer.jsp"/>

</body>

</html>