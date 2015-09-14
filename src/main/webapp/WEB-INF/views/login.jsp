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
	<title>QBox Arquivos em nuvem</title>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/semantic-ui/semantic.css" />">
  	<link href="<c:url value="/resources/favicon.ico" />" rel="icon" type="image/x-icon" />

  	<script src="<c:url value="/resources/jquery/jquery.min.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe-content.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe.js" />"></script>
  	<script src="<c:url value="/resources/semantic-ui/semantic.js" />"></script>
  	<script src="<c:url value="/resources/css/css.css" />"></script>
  	
  	<style type="text/css">
	    body {
	      background-color: #DADADA;
	    }
	    body > .grid {
	      height: 100%;
	    }
	    .image {
	      margin-top: -100px;
	    }
	    .column {
	      max-width: 450px;
	    }
  	</style>
  	
  	<script>
		  $(document)
		    .ready(function() {
		      $('.ui.form')
		        .form({
		          fields: {
		            email: {
		              identifier  : 'email',
		              rules: [
		                {
		                  type   : 'empty',
		                  prompt : 'Por favor informe seu e-mail'
		                },
		                {
		                  type   : 'email',
		                  prompt : 'Por favor informe um e-mail válido'
		                }
		              ]
		            },
		            password: {
		              identifier  : 'password',
		              rules: [
		                {
		                  type   : 'empty',
		                  prompt : 'Por favor informe sua senha'
		                },
		                {
		                  type   : 'length[6]',
		                  prompt : 'Sua senha precisa ter mais de 6 caracteres'
		                }
		              ]
		            }
		          }
		        })
		      ;
		    })
		  ;
  	</script>

</head>

<body style="background-color: #ffffff !important;">
	<div class="ui middle aligned center aligned grid">
		<div class="column">
	    	<h2 class="ui teal image header">
	      	<img src="<c:url value="/resources/img/qbox.png" />" class="image">
	  		<div class="content">
		  		Acessar sua conta
	  		</div>
			</h2>

			<form action="do-login" method="post" class="ui large form">
				<div class="ui stacked segment">
  					<div class="field">
    					<div class="ui left icon input">
      						<i class="user icon"></i>
      						<input type="text" name="email" placeholder="E-mail" value="${param.email}">
    					</div>
  					</div>
  					<div class="field">
    					<div class="ui left icon input">
      						<i class="lock icon"></i>
      						<input type="password" name="password" placeholder="Senha" value="${param.password}">
    					</div>
  					</div>
  					<div class="ui fluid large teal submit button">Acessar</div>
				</div>

				<div class="ui error message"> </div>

				<c:if test="${!empty param.loginMessage}">
					<div class="ui message" style="box-shadow: 0px 0px 0px 1px #e0b4b4 inset, 0px 0px 0px 0px rgba(0, 0, 0, 0); background-color: #fff6f6; color: #9f3a38;">
						<ul class="list"> <li> <c:out value="${param.loginMessage}" /> </li> </ul>
					</div>
				</c:if>

			</form>

    		<div class="ui message">
      			Novo por aqui? <a href="/user-register">Criar uma conta</a>
    		</div>
  		</div>
	</div>
</body>
</html>