<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
<%@include file="include/header.jsp" %>
    <div id="wrapper">
    		
    <%@include file="include/banner.jsp" %>
    		
    	<div class="line"></div>
    <div id="main_content">
    	<div id="menu">	
    		<div class="list-group">
  				<button type="button" class="list-group-item list-group-item-action category" disabled>
  					  Public
 				 </button>
  				<button type="button" class="list-group-item list-group-item-action">   안내         </button>
 				 <button type="button" class="list-group-item list-group-item-action">   모집        </button>
 				 <button type="button" class="list-group-item list-group-item-action">   기타        </button>
  				<button type="button" class="list-group-item list-group-item-action">    QA   </button>
			</div>
		</div>
		<div id="section">
			<div class="board_name"><p><span class="board_text">PRJ</span>게시판</p></div>
			<div class="line"></div>
    		<div class="container w-100 mx-auto mt-5">
    			
    					<p class="board_subject">테스트 제목입니다.</p>
    					<br/>
    					<div class="board_writer" >작성자 : <strong>홍길동</strong></div>
    					<div class="board_hit" >조회수 : 4</div>
    					<br/>
    					<hr/>
    					<br/>
    					<br/>
    					<div class="board_content">
    					<p>rgregeethththtrhrththynonhonnhkogngn
    					hgnghnhghnghnnhnghnghnghnghnghnghnghnghn
    					gnghnghnghnghnghnghnghgnghnghngnghn</p>
    					</div>
    				
					
					<br/>
    		</div>
    	</div>
    </div>
    		
    		
    	</div>
      
<%@include file="include/footer.jsp" %>

</body>
</html>