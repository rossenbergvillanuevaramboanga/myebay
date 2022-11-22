<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Visualizza Elemento</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Visualizza dettaglio</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Id:</dt>
							  <dd class="col-sm-9">${show_annuncio_attr.id}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Testo Annuncio:</dt>
							  <dd class="col-sm-9">${show_annuncio_attr.testoAnnuncio}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Prezzo:</dt>
							  <dd class="col-sm-9">${show_annuncio_attr.prezzo}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data creazione:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_annuncio_attr.data}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Categorie:</dt>
							  <c:forEach items="${categorie_annuncio_attr }" var="categoriaItem">
							  	<dd class="col-sm-9">${categoriaItem}</dd>
							  </c:forEach>
					    	</dl>
					    	
					    <!-- end card body -->
					    </div>
					    
					    <div class='card-footer'>

					        <a href="${pageContext.request.contextPath }/annuncio/list" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
					        <c:if test="${show_annuncio_attr.aperto }">
						        <sec:authorize access="isAuthenticated()">
						        	<a id="acquistoLink_#_${show_annuncio_attr.id }" class="btn btn-success link-for-modal" data-bs-toggle="modal" data-bs-target="#confirmOperationModal"  >Acquista</a>
							    </sec:authorize>
							    
							    <sec:authorize access="!isAuthenticated()">
							   		<a class="btn btn-success" href="${pageContext.request.contextPath}/annuncio/acquistaWithoutAuth?idAnnuncioWithNoAuth=${show_annuncio_attr.id }">Acquista</a> 
							    </sec:authorize>
						    </c:if>
					    </div>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
			<!-- Modal -->
	<div class="modal fade" id="confirmOperationModal" tabindex="-1"  aria-labelledby="confirmOperationModalLabel"
	    aria-hidden="true">
	    <div class="modal-dialog" >
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="confirmOperationModalLabel">Conferma Operazione</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <div class="modal-body">
	                Continuare con l'operazione?
	            </div>
	            <form method="post" action="${pageContext.request.contextPath}/annuncio/acquista" >
		            <div class="modal-footer">
		            	<input type="hidden" name="idAnnuncioForAcquisto" id="idAnnuncioForAcquisto">
		                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
		                <input type="submit" value="Continua"  class="btn btn-primary">
		            </div>
	            </form>
	        </div>
	    </div>
	</div>
	<!-- end Modal -->
	<script type="text/javascript">
		<!-- aggancio evento click al conferma del modal  -->
		$(".link-for-modal").click(function(){
			<!-- mi prendo il numero che poi sar� l'id. Il 18 � perch� 'changeStatoLink_#_' � appunto lungo 18  -->
			var callerId = $(this).attr('id').substring(15);
			<!-- imposto nell'hidden del modal l'id da postare alla servlet -->
			$('#idAnnuncioForAcquisto').val(callerId);
		});
	</script>
	  </body>
</html>