<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<%@include file="include/header.jsp" %>
   
<%@include file="include/banner.jsp" %>
    <div id="wrapper">		
    	<div id="main_content">
    		<div class="line"></div>
			<div id="section">
				<div class="line"></div>
    			<div class="container w-100 mx-auto mt-5">
    				<div class="row row-cols-1 row-cols-md-2">
    				
 						<div class="col mb-4">
   							 <div class="card article">
     							 <div class="card-body">
      								  <h5 class="card-title">Arcturus HOT 게시글</h5>
      								  <c:forEach items="${bestArcturus}" var="bvo">
      								  	<p class="card-text text-truncate"><a class="link-article" href="${pageContext.request.contextPath}/bbs/arcturus/${bvo.bid}">${bvo.subject}</a></p>
     								  </c:forEach>
     							 </div>
  							  </div>
						</div>
					 	<div class="col mb-4">
   						 <div class="card article">
    						  <div class="card-body">
     							   <h5 class="card-title">Starcraft HOT 게시글</h5>
     							   <c:forEach items="${bestStarcraft}" var="bvo">
      								  	<p class="card-text text-truncate"><a class="link-article" href="${pageContext.request.contextPath}/bbs/starcraft/${bvo.bid}">${bvo.subject}</a></p>
     								</c:forEach>
     							   
    						  </div>
  						  </div>
  						</div>
  				
 					
 					 
					</div>
    			</div>
    		</div>
    	</div>
    		
    		
    </div>
<script>
	var result= '${err}';
	console.log(${err});
	if(result!=null && result!=""){
		alert(result);
	}

</script>
<%@include file="include/footer.jsp" %>