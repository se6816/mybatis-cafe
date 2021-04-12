<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    				<form method="post" action="write">
    					<div class="form-group">
    						<label for="cur_pw">현재 비밀번호 :</label>
 						 	<input type="password" class="form-control" id="cur_pw" name="cur_pw" maxlength="20"/>
  							<br>
  							<label for="change_pw">바꿀 비밀번호 : </label>
 						 	<input type="password" class="form-control" id="change_pw" name="change_pw" maxlength="20"/>
  							<br>
  							<label for="change_pw_check">바꿀 비밀번호 확인:</label>
 							<input type="password" class="form-control" id="change_pw_check" name="change_pw_check" maxlength="20"/>
  							
						</div>
						<div style="float:right;">
							<button type="button" class="btn btn-primary btn-block" id="btn-change-pwd">변경하기</button>
						</div>  	
					</form>
    			</div>
    		</div>
    	</div>
    		
    		
    </div>
<script src="${pageContext.request.contextPath}/js/User.js"></script>
<%@include file="../include/footer.jsp" %>