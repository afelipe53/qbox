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
	<title>QBox Arquivos em nuvem</title>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/semantic-ui/semantic.css" />">
  	<link href="<c:url value="/resources/favicon.ico" />" rel="icon" type="image/x-icon" />

  	<script src="<c:url value="/resources/jquery/jquery.min.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe-content.js" />"></script>
  	<script src="<c:url value="/resources/library/iframe.js" />"></script>
  	<script src="<c:url value="/resources/semantic-ui/semantic.js" />"></script>
  	<script src="<c:url value="/resources/css/css.css" />"></script>
  	
  	<script>
		  $(document).ready(function() { $('.ui.form').form({ });});

		  function uploadFile(e) {
			  $.ajax({
		      	  url: '${root}/app/upload-file',
	              type: 'POST',
		      	  data: new FormData(document.getElementById('file-form')),
		      	  processData: false,
		      	  contentType: false,
		      	  beforeSend: function() {
		      		  $('#upload-button').hide();
		      	      $('#loadingDiv').show();
                  },
		      	  success: function() {
		      		  $('#loadingDiv').hide();
		      		  window.location.reload(true);
		          }
		      });
		   }

		   function confirmDeleteFile(idFile) {

			   $('#confirm-delete-file-modal')
			   .modal({
			     closable  : false,
			     onDeny    : function(){
					return true;
			     },
			     onApprove : function() {
			    	 deleteFile(idFile);
			     }
			   })
			   .modal('show')
			 ;

		   }

		   function confirmDeleteFolder(idFolder) {

			   $('#confirm-delete-folder-modal')
			   .modal({
			     closable  : false,
			     onDeny    : function(){
					return true;
			     },
			     onApprove : function() {
			    	 deleteFile(idFolder);
			     }
			   })
			   .modal('show')
			 ;

		   }

		   function deleteFile(idFile) {

			   $.ajax({
			      	  url: '${root}/app/delete/' + idFile,
		              type: 'POST',
			      	  data: null,
			      	  processData: false,
			      	  contentType: false,
			      	  beforeSend: function() {},
			      	  success: function() {
			      		  window.location.reload(true);
			          }
			      });
		   }

		   function createFolder() {

			   $('#create-folder-modal')
			   .modal({
			     closable  : false,
			     onDeny    : function(){
					return true;
			     },
			     
			     onApprove : function() {

				     var parentId = document.getElementById('parentFolder').value.length == 0 ? -1 : document.getElementById('parentFolder').value;

			    	 $.ajax({
				      	  url: '${root}/app/create-folder/' + parentId + "/" + document.getElementById('folder-name-input').value,
			              type: 'POST',
				      	  data: null,
				      	  processData: false,
				      	  contentType: false,
				      	  beforeSend: function() {},
				      	  success: function() {
				      		  window.location.reload(true);
				          }
				      });

				      return true;
			     }
			   }).modal('show');

		   }

		   function confirmEditFolder(idFolder, name) {

			   $('#folder-name-edit-input').val(name);

			   $('#edit-name-folder-modal')
			   .modal({
			     closable  : false,
			     onDeny    : function() {
					return true;
			     },
			     onApprove : function() {
			    	 editFolderName(idFolder, $('#folder-name-edit-input').val());
			     }
			   })
			   .modal('show');

		   }

		   function editFolderName(idFolder, nameFolder) {

			   $.ajax({
			      	  url: '${root}/app/editFolderName/' + idFolder,
		              type: 'POST',
			      	  data : nameFolder, 
			      	  processData: false,
			      	  contentType: false,
			      	  beforeSend: function() {},
			      	  success: function() {
			      		  window.location.reload(true);
			          }
			      });
		   }

		   function confirmGenerateLink(fileId) {

			   $('#confirm-generate-file-link')
			   .modal({
			     closable  : false,
			     onDeny    : function() {
					return true;
			     },
			     onApprove : function() {
				     generateLinkForFile(fileId);
			     }
			   })
			   .modal('show');
		   }

		   function generateLinkForFile(fileId) {

			   $.ajax({
			      	  url: '${root}/app/generate-link/' + fileId,
		              type: 'POST',
			      	  data : null, 
			      	  processData: false,
			      	  contentType: false,
			      	  beforeSend: function() {},
			      	  success: function(data) {
			      		  $('#div-message-create-link').show();
			      		  $("#p-link-generated").empty();
			      		  $("#p-link-generated").append(data);
			          }
			      });
		   }

		   function closeMessageCreateLink() {
			   $('#div-message-create-link').hide();
		   }

		   var $loading = 
		   $(document).ajaxStart(function () { $loading.show(); }).ajaxStop(function () { $loading.hide(); });

  	</script>

</head>

<body>

	<div class="ui grid container">

		<div class="row">
			<div class="column"> </div>
		</div>

		<div class="row">
			<div class="column">
				<img class="ui middle aligned mini image spaced" src="<c:url value="/resources/img/qbox.png" />">
				&nbsp; &nbsp; &nbsp; &nbsp;
				<div class="ui transparent left icon input">
					<input placeholder="Pesquisar arquivo" type="text">
				    <i class="search icon"></i>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="three wide column">
				<jsp:include page="../template/menu.jsp"/>
			</div>

			<div class="twelve wide column">

				<form id="file-form" method="POST" enctype="multipart/form-data" class="ui large form">
					<input type="hidden" id="parentFolder" name="parentFolder" value="${parentFolder.id}" />
					<input class="ui tiny button" type="file" id="file-select" name="file" size="1000000" />
					<input class="ui primary tiny button" type="button" id="upload-button" value="Carregar arquivo" onclick="uploadFile();"/>
					<div id="loadingDiv" style="display: none; margin-top: 7px !important;" class="ui active inline loader center aligned"></div>
					<input class="green ui tiny button" type="button" id="create-folder-button" value="Nova pasta" onclick="createFolder();"/>
				</form>

				<div class="ui positive message" id="div-message-create-link" style="display: none;">
					<i class="close icon" id="button-close-div-message-create-link" onclick="closeMessageCreateLink();"></i>
				  	<div class="header">
				    	Novo link criado
				  	</div>
				  	<a><p id="p-link-generated"></p></a>
				</div>

				<div class="ui breadcrumb">
				
					<c:forEach items="${breadcrumbList}" var="qBoxFile">

						<a class="section" href="${root}/app/home/${qBoxFile.id}">${qBoxFile.fileName}</a>
				  		<i class="right angle icon divider"></i>

				  	</c:forEach>
				</div>

				<table class="ui very basic table selectable" style="width: 100%;">
					<thead>
				    	<tr style="color: #aaa !important; font-size: 13px !important;">
				      		<th style="color: #aaa !important; font-size: 13px !important;">Nome</th>
				      		<th style="color: #aaa !important; font-size: 13px !important;">Modificado em</th>
				      		<th style="color: #aaa !important; font-size: 13px !important;">Extensão</th>
				      		<th style="color: #aaa !important; font-size: 13px !important;">Opções</th>
				    	</tr>
				  	</thead>

				  	<tbody>
				  		<c:forEach items="${fileList}" var="qBoxFile">

						    <tr style="color: #3d464d !important; font-size: 13px !important;">
						      <td style="color: #3d464d !important; font-size: 13px !important;">

						      	<c:choose>
								    <c:when test="${qBoxFile.folder}">
								        <a href="${root}/app/home/${qBoxFile.id}">${qBoxFile.fileName}</a>
								    </c:when>    
								    <c:otherwise>
								        <a href="${root}/app/file/${qBoxFile.id}" download="${qBoxFile.fileName}">${qBoxFile.fileName}</a>
								    </c:otherwise>
								</c:choose>

						      </td>
						      <td style="color: #3d464d !important; font-size: 13px !important;">${qBoxFile.dateModifiedOn}</td>
						      <td style="color: #3d464d !important; font-size: 13px !important;">${qBoxFile.fileExtension}</td>
						      <td style="color: #3d464d !important; font-size: 13px !important;">

						      	<c:choose>
								    <c:when test="${qBoxFile.folder}">
  										<i class="remove icon" onclick="confirmDeleteFolder(${qBoxFile.id});" style="cursor: pointer;"></i>
  										<i class="edit icon" onclick="confirmEditFolder(${qBoxFile.id}, '${qBoxFile.fileName}');" style="cursor: pointer;"></i>
								    </c:when>    
								    <c:otherwise>
								        <i class="remove icon" onclick="confirmDeleteFile(${qBoxFile.id});" style="cursor: pointer;"></i>
								    </c:otherwise>
								</c:choose>

						        <i class="linkify icon" style="cursor: pointer;" onclick="confirmGenerateLink(${qBoxFile.id});"></i>
						      </td>
						    </tr>
						</c:forEach>
				  	</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="ui basic small modal" id="confirm-delete-file-modal">
  		<div class="header">
    		Apagar arquivo
  		</div>
  		<div class="content">
    		<p>Tem certeza que deseja apagar esse arquivo?</p>
  		</div>
  		<div class="actions">
    		<div class="ui button cancel">Cancelar</div>
    		<div class="ui inverted red basic button ok">Apagar</div>
  		</div>
	</div>
	
	<div class="ui basic small modal" id="confirm-delete-folder-modal">
  		<div class="header">
    		Apagar pasta
  		</div>
  		<div class="content">
    		<p>Tem certeza que deseja apagar essa pasta?</p>
  		</div>
  		<div class="actions">
    		<div class="ui button cancel">Cancelar</div>
    		<div class="ui inverted red basic button ok">Apagar</div>
  		</div>
	</div>

	<div class="ui tiny modal" id="create-folder-modal">
  		<div class="header">
    		Nova pasta
  		</div>
  		<div class="content">
    		<p>Informe um nome para a pasta: </p>
    		<div class="ui input focus fluid tiny">
				<input type="text" id="folder-name-input">
			</div>
  		</div>
  		<div class="actions">
    		<div class="ui button cancel">Cancelar</div>
    		<div class="ui primary button ok">Criar</div>
  		</div>
	</div>

	<div class="ui tiny modal" id="edit-name-folder-modal">
  		<div class="header">
    		Alterar Nome da pasta
  		</div>
  		<div class="content">
    		<p>Informe um novo nome para a pasta: </p>
    		<div class="ui input focus fluid tiny">
				<input type="text" id="folder-name-edit-input">
			</div>
  		</div>
  		<div class="actions">
    		<div class="ui button cancel">Cancelar</div>
    		<div class="ui primary button ok">Confirmar</div>
  		</div>
	</div>

	<div class="ui basic small modal" id="confirm-generate-file-link">
  		<div class="header">
    		Gerar Link de compartilhamento
  		</div>
  		<div class="content">
    		<p>Tem certeza que deseja gerar o link para este arquivo/pasta?</p>
  		</div>
  		<div class="actions">
    		<div class="ui button cancel">Cancelar</div>
    		<div class="ui inverted red basic button ok">Confirmar</div>
  		</div>
	</div>

	<jsp:include page="../template/footer.jsp"/>

</body>

</html>