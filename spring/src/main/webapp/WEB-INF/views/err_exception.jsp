<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="include/header.jsp" %>
   
<%@include file="include/banner.jsp" %>
   <div id="wrapper">
     		
     		
  		<div id="main_content">
   		<div class="line"></div>
   		<div class="section">
   			<p class="error">${exception}</p>
   			<a href="${pageContext.request.contextPath}/bbs/main"><p class="error"> 메인으로</p></a>
   		
   		</div>
		
    </div>
    		
    		
    		
   </div>

<%@include file="include/footer.jsp" %>



