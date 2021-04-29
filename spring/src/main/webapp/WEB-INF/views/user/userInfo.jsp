<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../include/header.jsp" %>
   
<%@include file="../include/banner.jsp" %>
    <div id="wrapper">		
    	<div id="main_content">
    		<div class="line"></div>
    		<div id="menu">	
    			<div class="list-group">
  					
  					<c:forEach var="i" begin="0" end="${UserCategory.size()-1}">
  					  <a href="<c:url value='${UserCategory.get(i).getValue()}'/>">
  					  	<button type="button" class="list-group-item list-group-item-action">  ${UserCategory.get(i).getKey()} </button>
  						</a>
  					</c:forEach>
			
				</div>
			</div>
			<div id="section">
				<div class="board_name"><p><span class="board_text">${b_name}</span>게시판</p></div>
				<div class="line"></div>
    			<div class="container w-100 mx-auto mt-5">
 						 <div class="user-info">
 						 	<p>활동명 : ${principal.username}</p>
 						 	<p>이메일 : ${principal.email}</p>
 						 	<p>가입날짜 : <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${principal.regdate}"/></p>
 						 	<p>정지기한 : <c:if test="not empty principal.haltDate">
 						 					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${principal.haltDate}"/>
 						 				</c:if>
 						 	</p>
 						 </div>
						<div style="float:right;">
							<button type="button" class="btn btn-primary btn-block" id="btn-delete">회원 탈퇴</button>
						</div>  	
					
    			</div>
    		</div>
    	</div>
    		
    		
    </div>
<script src="${pageContext.request.contextPath}/js/User.js"></script>      
<%@include file="../include/footer.jsp" %>
