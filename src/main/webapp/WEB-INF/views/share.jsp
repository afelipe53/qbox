<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<!-- Standard Meta -->
  	<meta charset="utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

 	<!-- Site Properities -->
	<title>Compartilhamento - QBox Arquivos em nuvem</title>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/semantic-ui/semantic.css" />">
  	<link href="<c:url value="/resources/favicon.ico" />" rel="icon" type="image/x-icon" />

  	<script src="<c:url value="/resources/jquery/jquery.min.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe-content.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe.js" />"></script>
  	<script src="<c:url value="/resources/semantic-ui/semantic.js" />"></script>
  	<script src="<c:url value="/resources/css/css.css" />"></script>
  	
  	<script>

  	</script>

</head>

<body>

	<div class="ui grid container">

		<div class="row">
			<div class="column"> </div>
		</div>

		<div class="row">
			<div class="column">Compartilhado por: ${userShare.name} ${userShare.lastName}</div>
		</div>

		<div class="row">

			<div class="wide column">

				<div class="ui breadcrumb">

					<c:forEach items="${breadcrumbList}" var="qBoxFile">

						<a class="section" href="${root}/share/${linkId}/${qBoxFile.id}">${qBoxFile.fileName}</a>
				  		<i class="right angle icon divider"></i>

				  	</c:forEach>
				</div>

				<table class="ui very basic table selectable" style="width: 100%;">
					<thead>
				    	<tr style="color: #aaa !important; font-size: 13px !important;">
				      		<th style="color: #aaa !important; font-size: 13px !important;">Nome</th>
				      		<th style="color: #aaa !important; font-size: 13px !important;">Modificado em</th>
				      		<th style="color: #aaa !important; font-size: 13px !important;">Extensão</th>
				    	</tr>
				  	</thead>

				  	<tbody>
				  		<c:forEach items="${fileList}" var="qBoxFile">

						    <tr style="color: #3d464d !important; font-size: 13px !important;">
						      <td style="color: #3d464d !important; font-size: 13px !important;">

						      	<c:choose>
								    <c:when test="${qBoxFile.folder}">
								        <a href="${root}/share/${linkId}/${qBoxFile.id}">${qBoxFile.fileName}</a>
								    </c:when>
								    <c:otherwise>
								        <a href="${root}/share/file/${qBoxFile.id}" download="${qBoxFile.fileName}">${qBoxFile.fileName}</a>
								    </c:otherwise>
								</c:choose>

						      </td>
						      <td style="color: #3d464d !important; font-size: 13px !important;">${qBoxFile.dateModifiedOn}</td>
						      <td style="color: #3d464d !important; font-size: 13px !important;">${qBoxFile.fileExtension}</td>
						    </tr>
						</c:forEach>
				  	</tbody>
				</table>
				
			</div>
		</div>
		
	</div>

	<jsp:include page="template/footer.jsp"/>

</body>

</html>