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
    				<c:forEach var="i" begin="0" end="${recentArticle.size()-1}">
 						 <div class="col mb-4">
   							 <div class="card article">
     							 <div class="card-body">
      								  <h5 class="card-title">${boardTypes[i].name()} 최신 게시글</h5>
      								  <c:forEach items="${recentArticle.get(i)}" var="bvo">
      								  	<p class="card-text"><a class="link-article" href="${pageContext.request.contextPath}/bbs/${boardTypes[i].name()}/${bvo.bid}">${bvo.subject}</a></p>
     								  </c:forEach>
     							 </div>
  							  </div>
						  </div>
					  <div class="col mb-4">
   						 <div class="card article">
    						  <div class="card-body">
     							   <h5 class="card-title">${boardTypes[i].name()} HOT 게시글</h5>
     							   <c:forEach items="${hotArticle.get(i)}" var="bvo">
      								  	<p class="card-text"><a class="link-article" href="${pageContext.request.contextPath}/bbs/${boardTypes[i].name()}/${bvo.bid}">${bvo.subject}</a></p>
     								</c:forEach>
     							   
    						  </div>
  						  </div>
  						</div>
  					</c:forEach>
 					
 					 
					</div>
    			</div>
    		</div>
    	</div>
    		
    		
    </div>
<script>
	var result= '${err}';
	if(result!=null){
		alert(result);
	}

</script>
<%@include file="include/footer.jsp" %>